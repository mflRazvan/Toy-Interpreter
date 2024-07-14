package model.statement;

import model.ProgramState;
import model.exception.InterpreterException;
import model.exception.MyException;
import model.type.Type;
import model.value.Value;
import utils.MyIDict;

public class VarDeclStatement implements IStatement{
    String name;

    Type type;

    public VarDeclStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException{
        MyIDict<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new InterpreterException("There is already a value declared with this name");
        symTable.put(name, type.getDefaultValue());
        return null;
    }

    @Override
    public MyIDict<String,Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        typeEnv.put(name,type);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new VarDeclStatement(name, type);
    }

    @Override
    public String toString(){
        return "Declared " + type.toString() + name;
    }
}
