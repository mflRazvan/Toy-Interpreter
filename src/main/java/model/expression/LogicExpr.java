package model.expression;

import model.type.IntType;
import model.type.Type;
import model.value.*;
import utils.MyHeap;
import utils.MyIDict;
import model.exception.*;
import model.type.BoolType;

public class LogicExpr implements Expression{
    Expression expr1;
    Expression expr2;
    String operation;

    public LogicExpr(Expression expr1, Expression expr2, String operation){
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDict<String,Value> symbolTable, MyHeap heap) throws MyException{
        Value eval1 = expr1.evaluate(symbolTable, heap);
        if (eval1.getType().equals(new BoolType())){
            Value eval2 = expr2.evaluate(symbolTable, heap);
            if (eval2.getType().equals(new BoolType())){
                BoolValue value1 = (BoolValue) eval1;
                BoolValue value2 = (BoolValue) eval2;
                boolean n1, n2;
                n1 = value1.getVal();
                n2 = value2.getVal();
                if(n1 && n2){
                    return new BoolValue((true));
                }
                else if((n1 && n2 == false) || (n1 == false && n2)){
                    if(operation == "and") {
                        return new BoolValue(false);
                    } else return new BoolValue(true);
                }
                else return new BoolValue(false);
            } else throw new WrongTypeException("The second operand is not an integer!");
        } else throw new WrongTypeException("The first operand is not an integer!");
    }

    @Override
    public Type typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type type1, type2;
        type1=expr1.typeCheck(typeEnv);
        type2=expr2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }
}
