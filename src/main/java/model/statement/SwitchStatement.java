package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.expression.*;
import model.type.Type;
import utils.MyIDict;
import utils.MyIStack;

public class SwitchStatement implements IStatement{
    Expression mainExpr;
    Expression expr1;
    Expression expr2;
    IStatement statement1;
    IStatement statement2;
    IStatement defaultStatement;

    public SwitchStatement(Expression expr, Expression expr1, Expression expr2, IStatement statement1, IStatement statement2, IStatement defaultStatement){
        this.mainExpr = expr;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIStack<IStatement> exeStack = state.getExeStack();
        IStatement converted = new IfStatement(new RelationalExpression(mainExpr, expr1, "=="),
                statement1, new IfStatement(new RelationalExpression(mainExpr, expr2, "=="), statement2, defaultStatement));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type main = mainExpr.typeCheck(typeEnv);
        Type first = expr1.typeCheck(typeEnv);
        Type second = expr2.typeCheck(typeEnv);
        if (main.equals(first) && main.equals(second)) {
            statement1.typeCheck(typeEnv);
            statement2.typeCheck(typeEnv);
            defaultStatement.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new MyException("The expression types don't match in the switch statement!");
        }
    }

    @Override
    public IStatement deepCopy(){
        return new SwitchStatement(mainExpr, expr1, expr2, statement1, statement2, defaultStatement);
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainExpr, expr1, statement1, expr2, statement2, defaultStatement);
    }
}
