package model.statement;

import javafx.util.Pair;
import model.ProgramState;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;
import utils.MyIDict;
import utils.MyISemaphore;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement{
    private String var;
    private static final Lock lock = new ReentrantLock();

    public AcquireStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        lock.lock();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyISemaphore semaphoreTable = state.getSemaphoreTable();
        if(symTable.isDefined(var)){
            if(symTable.lookUp(var).getType().equals(new IntType())){
                IntValue fi =(IntValue) symTable.lookUp(var);
                int foundIndex = fi.getVal();
                if(semaphoreTable.getSemaphoreTable().containsKey(foundIndex)){
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
                    int NL = foundSemaphore.getValue().size();
                    int N1 = foundSemaphore.getKey();
                    if (N1 > NL) {
                        if (!foundSemaphore.getValue().contains(state.getId())) {
                            foundSemaphore.getValue().add(state.getId());
                            semaphoreTable.update(foundIndex, new Pair<>(N1, foundSemaphore.getValue()));
                        }
                    } else {
                        state.getExeStack().push(this);
                    }
                } else {
                    throw new MyException("Index not a key in the semaphore table!");
                }
            } else {
                throw new MyException("Index must be of int type!");
            }
        } else {
            throw new MyException("Index not in symbol table!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        if (typeEnv.lookUp(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException(String.format("%s is not int!", var));
        }
    }

    @Override
    public IStatement deepCopy(){
        return new AcquireStatement(var);
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", var);
    }
}
