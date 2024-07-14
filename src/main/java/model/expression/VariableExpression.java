package model.expression;

import model.exception.MyException;
import model.type.Type;
import utils.MyHeap;
import utils.MyIDict;
import model.value.*;
import model.exception.ADTEmptyException;

public class VariableExpression implements Expression{
    String id;

    public VariableExpression(String id){
        this.id = id;
    }

    public String getName() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDict<String, Value> symbolTable, MyHeap heap){
        return symbolTable.lookUp(id);
    }

    @Override
    public Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }
}
