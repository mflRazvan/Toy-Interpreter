package model.statement;

import javafx.util.Pair;
import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;
import utils.MyIHeap;
import utils.MyISemaphore;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStatement implements IStatement{
    private String var;
    private Expression expr1;
    private static final Lock lock = new ReentrantLock();

    public CreateSemaphoreStatement(String var, Expression expr1){
        this.expr1 = expr1;
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        lock.lock();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyHeap heapTable = state.getHeap();
        MyISemaphore semaphoreTable = state.getSemaphoreTable();
        Value number1 = expr1.evaluate(symTable, heapTable);
        if(number1.getType().equals(new IntType())){
            int freeAddress = semaphoreTable.getFreeAddress();
            IntValue number = (IntValue)number1;
            semaphoreTable.put(freeAddress, new Pair<>(number.getVal(), new ArrayList<>()));
            if (symTable.isDefined(var) && symTable.lookUp(var).getType().equals(new IntType()))
                symTable.update(var, new IntValue(freeAddress));
            else
                throw new MyException(String.format("Error for variable %s: not defined/does not have int type!", var));
        }
        else{
            throw new MyException("The evaluated expression does not have the int type\n");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        if (typeEnv.lookUp(var).equals(new IntType())) {
            if (expr1.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Expression is not of int type!");
        } else {
            throw new MyException(String.format("%s is not of type int!", var));
        }
    }

    @Override
    public IStatement deepCopy(){
        return new CreateSemaphoreStatement(var, expr1);
    }

    @Override
    public String toString() {
        return String.format("createSemaphore(%s, %s)", var, expr1);
    }
}
