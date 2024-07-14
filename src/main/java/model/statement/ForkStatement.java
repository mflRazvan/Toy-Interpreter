package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.type.Type;
import utils.MyIDict;
import utils.MyIStack;
import utils.MyStack;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state){
        MyIStack<IStatement> newExeStack = new MyStack<>();
        newExeStack.push(statement);
        return new ProgramState(newExeStack, state.getSymTable().copy(),
                state.getOut(), state.getFileTable(), state.getHeap(), state.getSemaphoreTable());
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new ForkStatement(statement);
    }

    @Override
    public String toString() {
        return String.format("Fork{\n%s\n}", statement);
    }
}
