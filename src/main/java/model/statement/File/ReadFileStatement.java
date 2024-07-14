package model.statement.File;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.statement.IStatement;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private Expression expression;
    private String varName;

    public ReadFileStatement(Expression expression, String varName){
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<String, BufferedReader> fileTable = state.getFileTable();
        MyHeap heap = state.getHeap();
        if(!symTable.containsKey(varName))
            throw new MyException("There is no entry for this key!\n");

        Value value = symTable.lookUp(varName);
        if (!value.getType().equals(new IntType()))
            throw new MyException(String.format("ERROR: %s is not of type IntType", value));

        value = expression.evaluate(symTable, heap);
        if(!value.getType().equals(new StringType()))
            throw new MyException(String.format("ERROR: %s does not evaluate to StringType", value));

        StringValue castValue = (StringValue) value;
        if (!fileTable.containsKey(castValue.getVal()))
            throw new MyException(String.format("ERROR: the fileTable does not contain %s", castValue));

        BufferedReader br = fileTable.lookUp(castValue.getVal());
        try{
            String line = br.readLine();
            if (line == null)
                line = "0";
            symTable.put(varName, new IntValue(Integer.parseInt(line)));
        }
        catch(IOException ex){
            throw new MyException(ex.getMessage());
        }

        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new MyException("ERROR: ReadFile requires a string as expression parameter");
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new MyException("ERROR: ReadFile requires an int as variable parameter");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new ReadFileStatement(expression, varName);
    }

    @Override
    public String toString(){
        return "readFile("+expression.toString() +", "+ varName +")";
    }
}
