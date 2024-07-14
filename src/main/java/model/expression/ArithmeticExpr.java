package model.expression;

import model.exception.*;
import utils.MyHeap;
import utils.MyIDict;
import model.value.*;
import model.type.*;

public class ArithmeticExpr implements Expression {
    private Expression expr1;
    private Expression expr2;
    private Operation operation;

    public ArithmeticExpr(Expression expr1, Expression expr2, Operation operation){
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDict<String, Value> symbolTable, MyHeap heap) throws MyException {
        Value eval1 = expr1.evaluate(symbolTable, heap);
        if (eval1.getType().equals(new IntType())){
            Value eval2 = expr2.evaluate(symbolTable, heap);
            if (eval2.getType().equals(new IntType())){
                IntValue value1 = (IntValue) eval1;
                IntValue value2 = (IntValue) eval2;
                int n1, n2;
                n1 = value1.getVal();
                n2 = value2.getVal();
                if(operation == Operation.PLUS) return new IntValue(n1 + n2);
                else if(operation == Operation.MINUS) return new IntValue(n1 - n2);
                else if(operation == Operation.MULTIPLY) return new IntValue(n1 * n2);
                else if(operation == Operation.DIVIDE){
                    if(n2 == 0) throw new DivisionByZero("Error! Impossible to divide by zero.");
                    return new IntValue(n1/n2);
                }
            } else throw new WrongTypeException("The second operand is not an integer!");
        } else throw new WrongTypeException("The first operand is not an integer!");
        return null;
    }

    @Override
    public Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException{
        Type type1, type2;
        type1=expr1.typeCheck(typeEnv);
        type2=expr2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public String toString() {
        String result = "( " + expr1.toString();
        if(operation == Operation.PLUS) result += " + ";
        else if(operation == Operation.MINUS) result += " - ";
        else if(operation == Operation.MULTIPLY) result += " * ";
        else if(operation == Operation.DIVIDE) result += " / ";
        result += expr2.toString() + " )";
        return result;
    }
}
