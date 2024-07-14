package model.expression;

import model.exception.MyException;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

public class HeapRead implements Expression{
    private Expression expr;

    public HeapRead(Expression expr){
        this.expr = expr;
    }

    @Override
    public Value evaluate(MyIDict<String, Value> symTable, MyHeap heap) throws MyException {
        Value val = expr.evaluate(symTable, heap);
        Type valType = val.getType();
        System.out.println("Value: " + valType + "  " + val);
        if(!(valType instanceof RefType)) {
            System.out.println(val.getType());
            throw new MyException("The evaluated expression is not a reference value");
        }

        RefValue referenceValue = (RefValue)val;
        System.out.println(heap.get(referenceValue.getAddress()));
        return heap.get(referenceValue.getAddress());
    }

    @Override
    public Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException{
        Type typ=expr.typeCheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return String.format("ReadHeap{%s}", expr);
    }
}
