package model.expression;

import model.exception.*;
import model.type.Type;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;
import model.exception.MyException;


public interface Expression {
    public Value evaluate(MyIDict<String, Value> symbolTable, MyHeap heap) throws MyException;

    Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException;
    String toString();
}