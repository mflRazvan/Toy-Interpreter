package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

public class HeapWrite implements IStatement{
    String varName;

    Expression expr;

    public HeapWrite(String varName, Expression expr){
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIDict<String, Value> symTable = state.getSymTable();
        MyHeap heap = state.getHeap();
        if(!symTable.containsKey(varName))
            throw new MyException("The variable is not declared in the heap\n");

        Value val = symTable.lookUp(varName);
        if(!(val.getType() instanceof RefType))
            throw new MyException("The saved value is not a reference value\n");

        RefValue refVal = (RefValue) val;
        Integer address = refVal.getAddress();
        if(!(heap.containsKey(refVal.getAddress())))
            throw new MyException("The heap does not contain the wanted address\n");

        Value evaluated = expr.evaluate(symTable, heap);
        RefType locationType = (RefType) refVal.getType();
        if (!locationType.getInner().equals(evaluated.getType())) {
            System.out.println("Evaluated: " + evaluated.getType() + '\n' + "Val: " + refVal.getType());
            throw new MyException(String.format("ERROR: %s not of %s", evaluated, refVal.getType()));
        }

        heap.update(address, evaluated);

        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookUp(varName).equals(new RefType(expr.typeCheck(typeEnv))))
            return typeEnv;
        throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return String.format("WriteHeap{%s, %s}", varName, expr);
    }

    @Override
    public IStatement deepCopy(){
        return new HeapWrite(varName, expr);
    }
}
