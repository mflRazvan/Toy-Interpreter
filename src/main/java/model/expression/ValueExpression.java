package model.expression;

import model.exception.MyException;
import model.type.Type;
import model.value.*;
import utils.MyHeap;
import utils.MyIDict;

public class ValueExpression implements Expression{
    Value val;

    public ValueExpression(Value val){
        this.val = val;
    }

    @Override
    public Value evaluate(MyIDict<String, Value> symbolTable, MyHeap heap) {
        return val;
    }

    @Override
    public Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException {
        return val.getType();
    }

    @Override
    public String toString(){
        return val.toString();
    }
}
