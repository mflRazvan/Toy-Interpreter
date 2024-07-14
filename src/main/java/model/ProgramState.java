package model;

import javafx.util.Pair;
import model.exception.*;
import model.statement.IStatement;
import model.value.Value;
import utils.*;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDict<String, Value> symTable;
    MyIList<Value> out;
    IStatement originalProgram;

    MyIDict<String, BufferedReader> fileTable;

    MyHeap Heap;

    MyISemaphore semaphoreTable;

    private int id;

    private static int nextId = 1;

    public ProgramState(MyIStack<IStatement> exeStack, MyIDict<String,Value> symTable, MyIList<Value> out, MyIDict<String, BufferedReader> fileTable, MyHeap heap, MyISemaphore semaphoreTable){
        this.exeStack=exeStack;
        this.symTable=symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.Heap = heap;
        this.id = generateNextId();
        this.semaphoreTable = semaphoreTable;
    }

    public ProgramState(IStatement origProgram){
        exeStack = new MyStack<>();
        symTable = new MyDict<>();
        out = new MyList<>();
        fileTable = new MyDict<>();
        Heap = new MyHeap();
        semaphoreTable = new MySemaphore();

        originalProgram = origProgram;
        exeStack.push(origProgram);
        this.id = generateNextId();
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDict<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDict<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void addOut(Value val){
        this.out.add(val);
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyIDict<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDict<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyHeap getHeap(){
        return Heap;
    }

    public void setHeap(MyHeap heap){
        this.Heap = heap;
    }

    public MyISemaphore getSemaphoreTable(){
        return semaphoreTable;
    }

    public void setSemaphoreTable(MyISemaphore semaphoreTable){
        this.semaphoreTable = semaphoreTable;
    }

    public ProgramState oneStep() throws MyException {
        if (exeStack.isEmpty())
            throw new ADTEmptyException("Stack empty");
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public int getId() {
        return id;
    }

    // Static synchronized method to manage the id
    private static synchronized int generateNextId() {
        return nextId++;
    }

    @Override
    public String toString() {
        return "------------------------------------------------------\n" +
                "Program ID: " + id + '\n' +
                "PrgState{\n" +
                "ExeStack = " + exeStack + '\n' +
                "------------------------------------------------------\n" +
                "SymTable = " + symTable + '\n' +
                "------------------------------------------------------\n" +
                "Out = " + out + '\n' +
                "------------------------------------------------------\n" +
                "FileTable = " + fileTable + '\n' +
                "------------------------------------------------------\n" +
                "Heap = " + Heap + '\n' +
                "------------------------------------------------------\n" +
                "SemaphoreTable = " + semaphoreTable + '\n' +
                "}\n" +
                "------------------------------------------------------\n\n\n";
    }
}