package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.type.Type;
import utils.MyIDict;

public class PrintStatement implements IStatement{
    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.addOut(expression.evaluate(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new PrintStatement(expression);
    }

    @Override
    public String toString(){
        return "print(" +expression.toString()+")";
    }

}
