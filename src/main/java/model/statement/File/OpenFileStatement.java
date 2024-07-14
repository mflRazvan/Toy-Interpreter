package model.statement.File;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.statement.IStatement;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileStatement implements IStatement{
    private Expression expression;

    public OpenFileStatement(Expression expression){
        this.expression = expression;
    }

    public void setFilename(Expression expression) {
        this.expression = expression;
    }

    public String getFilename() {
        return expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<String, BufferedReader> fileTable = state.getFileTable();
        MyHeap heap = state.getHeap();
        Value value;
        value = expression.evaluate(symTable, heap);
        if(!value.equals(new StringValue("")))
            throw new MyException("The expression is not of string type!\n");

        StringValue filename = (StringValue) value;

        if(fileTable.containsKey(expression.toString()))
            throw new MyException("The file is already open!\n");

        BufferedReader b;
        try{
            b = new BufferedReader(new FileReader(filename.getVal()));
        }
        catch(IOException ex){
            throw new MyException("error while trying to open the file\n");
        }

        fileTable.put(filename.getVal(), b);

        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new MyException("ERROR: OpenReadFile requires a string expression");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new OpenFileStatement(expression);
    }

    @Override
    public String toString() {
        return "openFile(\"" + expression.toString() + "\")";
    }
}
