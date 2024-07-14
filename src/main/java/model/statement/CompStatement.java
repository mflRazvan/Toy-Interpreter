package model.statement;

import model.ProgramState;
import model.exception.InterpreterException;
import model.exception.MyException;
import model.type.Type;
import utils.MyIDict;
import utils.MyIStack;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second){
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDict<String,Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStatement deepCopy(){
        return new CompStatement(first, second);
    }

    @Override
    public String toString(){
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}