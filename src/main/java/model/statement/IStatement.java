package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.type.Type;
import utils.MyIDict;

public interface IStatement {
    public ProgramState execute(ProgramState state) throws MyException;

    MyIDict<String,Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException;

    public IStatement deepCopy();
}
