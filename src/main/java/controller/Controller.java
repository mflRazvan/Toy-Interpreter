package controller;

import model.ProgramState;
import model.exception.*;
import model.statement.IStatement;
import model.statement.NopStatement;
import model.type.Type;
import model.value.*;
import repository.IRepository;
import utils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController{
    private IRepository repository;
    boolean displayExecution;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.executor = Executors.newSingleThreadExecutor();
        displayExecution=true;
    }

    public Controller(IRepository repository, ExecutorService executor, boolean displayExecution) {
        this.repository = repository;
        this.executor = executor;
        this.displayExecution = displayExecution;
    }

    private void printThings(){
        ProgramState programState = repository.getCurrentProgramState();
        System.out.print("------------------------------------------------------\n");
        System.out.print("-----ExecutionStack-----\n");
        System.out.print(programState.getExeStack().toString() + "\n");
        System.out.print("-----OutputList-----\n");
        System.out.print(programState.getOut().toString() + "\n");
        System.out.print("-----SymbolTable-----\n");
        System.out.print(programState.getSymTable().toString() + "\n");
        System.out.print("-----FileTable-----\n");
        System.out.print(programState.getFileTable().toString() + "\n");
        System.out.print("------------------------------------------------------\n");
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heap.containsKey(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(List<Collection<Value>> symTableValues, Map<Integer, Value> heapTable){
        List<Integer> allAddresses = new ArrayList<>();

        symTableValues.forEach(symbolTable -> symbolTable.stream()
                .filter(value -> value instanceof RefValue)
                .forEach( value -> {
                    while (value instanceof RefValue) {
                        allAddresses.add(((RefValue) value).getAddress());
                        value = heapTable.get(((RefValue) value).getAddress());
                    }
                }));
        return allAddresses;
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    @Override
    public MyIList<ProgramState> removeCompletedPrg(MyIList<ProgramState> inPrgList){
        MyIList newList = new MyList();
        ProgramState state;
        for(int i = 0; i < inPrgList.size(); i++){
            state = inPrgList.get(i);
            if(state.isNotCompleted())
                newList.add(state);
        }
        return newList;
    }

    @Override
    public void oneStepForAllPrg(MyIList<ProgramState> stateList) throws MyException {
        stateList = this.removeCompletedPrg(repository.getPrgList());
        this.updateHeap();
        stateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });

        List<Callable<ProgramState>> callList = stateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            System.out.println(e);
                            try {
                                this.setProgram(new NopStatement());
                            } catch (MyException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
        }
        catch(InterruptedException e){
            throw new RuntimeException(e);
            //System.out.println(e.getMessage());
        }
        stateList.addAll(newPrgList);
        stateList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
        repository.setPrgList(stateList);
    }

    public void runTypeChecker() throws MyException {
        MyIList<ProgramState> list = repository.getPrgList();
        for(int i = 0; i < list.size(); i++){
            MyIDict<String, Type> typeTable = new MyDict<>();
            list.get(i).getExeStack().peek().typeCheck(typeTable);
        }
    }

    private void updateHeap()
    {
        ProgramState firstProgram = this.repository.getPrgList().get(0);

        firstProgram.getHeap().setContent(
                this.garbageCollector(
                        this.getAddrFromSymTable(
                                this.repository.getPrgList().stream()
                                        .map(program -> program.getSymTable().getContent().values())
                                        .collect(Collectors.toList()),
                                firstProgram.getHeap().getContent()
                        ),
                        firstProgram.getHeap().getContent()
                ));
    }

    @Override
    public void allStep()throws MyException{
        runTypeChecker();
        executor = Executors.newFixedThreadPool(2);
        MyIList temp = repository.getPrgList();
        MyIList prg = removeCompletedPrg(temp);
        while(prg.size() > 0){
            try{
                oneStepForAllPrg(prg);
            }
            catch(MyException e){
                System.out.println(e.getMessage());
            }
            temp = repository.getPrgList();
            prg = removeCompletedPrg(temp);
        }
        executor.shutdownNow();

        repository.setPrgList(prg);
    }

    @Override
    public void setProgram(IStatement statement) throws MyException{
        statement.typeCheck(new MyDict<>());

        this.repository.clear();
        this.repository.add(new ProgramState(statement));

        this.repository.logPrgStateExec(this.repository.getPrgList().get(0));

    }

    @Override
    public MyIList<ProgramState> getPrgStates() {
        return this.repository.getPrgList();
    }
}