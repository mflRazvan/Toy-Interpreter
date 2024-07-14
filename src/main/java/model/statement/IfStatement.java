package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.*;
import utils.MyIDict;
import utils.MyIStack;

public class IfStatement implements IStatement{
    Expression expression;

    IStatement thenS;

    IStatement elseS;

    public IfStatement(Expression expression, IStatement thenS, IStatement elseS){
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExeStack();
        Value value = new BoolValue(false);
        value = expression.evaluate(state.getSymTable(), state.getHeap());
        if(value.equals(new BoolValue(true))) stack.push(thenS);
        else stack.push(elseS);
        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type typexp=expression.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.copy());
            elseS.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStatement deepCopy(){
        return new IfStatement(expression, thenS, elseS);
    }

    @Override
    public String toString(){
        return "(if("+ expression.toString()+") then(" +thenS.toString() +")else("+elseS.toString()+"))";
    }
}
