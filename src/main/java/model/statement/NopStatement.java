package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.type.Type;
import utils.MyIDict;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state){ return null; }

    @Override
    public IStatement deepCopy(){
        return new NopStatement();
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        return typeEnv;
    }

    @Override
    public String toString(){ return ""; }
}
