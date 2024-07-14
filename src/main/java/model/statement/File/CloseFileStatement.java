package model.statement.File;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.statement.IStatement;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;
import utils.MyIDict;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement {

    private Expression expression;

    public CloseFileStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        Value value = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new MyException(String.format("ERROR: %s does not evaluate to StringValue", expression));
        StringValue fileName = (StringValue) value;
        MyIDict<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.containsKey(fileName.getVal()))
            throw new MyException(String.format("ERROR: %s is not present in the symTable", value));
        BufferedReader br = fileTable.lookUp(fileName.getVal());
        try {
            br.close();
        }
        catch (IOException e) {
            throw new MyException(String.format("ERROR: Unexpected error in closing %s", value));
        }
        fileTable.remove(fileName.getVal());

        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException {
        if (!expression.typeCheck(typeEnv).equals(new StringType()))
            throw new MyException("ERROR: CloseReadFile requires a string expression");
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new CloseFileStatement(expression);
    }
}
