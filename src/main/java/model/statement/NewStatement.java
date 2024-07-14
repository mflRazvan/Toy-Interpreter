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

public class NewStatement implements IStatement{
    private String varName;
    private Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIDict<String, Value> symTable = state.getSymTable();
        MyHeap heap = state.getHeap();
        if(!symTable.containsKey(varName))
            throw new MyException("A variable is not in the symbol table\n");
        Value varValue = symTable.lookUp(varName);
        if(!(varValue.getType() instanceof RefType))
            throw new MyException("A value is not a reference type\n");

        Value evaluated = expression.evaluate(symTable, heap);
        RefType locationType = (RefType) varValue.getType();
        Type innerType = locationType.getInner();

        if(!evaluated.getType().equals(innerType)) {
            System.out.println("Evaluated: " + evaluated.getType() + '\n' + "Inner: " + innerType);
            throw new MyException("The heap type and the one from symtable are not the same\n");
        }

        Integer newPosition = heap.add(evaluated);
        symTable.put(varName, new RefValue(newPosition, innerType));

        state.setSymTable(symTable);
        state.setHeap(heap);

        return null;
    }

    @Override
    public MyIDict<String,Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookUp(varName);
        Type typexp = expression.typeCheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStatement deepCopy(){
        return new NewStatement(varName, expression);
    }

    @Override
    public String toString() {
        return String.format("New{%s, %s}", varName, expression);
    }
}
