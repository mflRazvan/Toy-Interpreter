package model.expression;

import model.exception.MyException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

public class RelationalExpression implements Expression {
    private Expression expression_left;
    private Expression expression_right;
    private String comparisonOperator;

    public RelationalExpression(Expression expression_left, Expression expression_right, String comparisonOperator){
        this.comparisonOperator = comparisonOperator;
        this.expression_left = expression_left;
        this.expression_right = expression_right;
    }

    public Expression getExpression_left() {
        return expression_left;
    }

    public Expression getExpression_right() {
        return expression_right;
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public void setExpression_left(Expression expression_left) {
        this.expression_left = expression_left;
    }

    public void setExpression_right(Expression expression_right) {
        this.expression_right = expression_right;
    }

    @Override
    public String toString() {
        return "( " + expression_left.toString() + " " + comparisonOperator + " " + expression_right.toString() + " )";
    }

    @Override
    public BoolValue evaluate(MyIDict<String, Value> symTable, MyHeap heap) throws MyException{
        Value expr1 = expression_left.evaluate(symTable, heap);
        Value expr2 = expression_right.evaluate(symTable, heap);
        boolean evalResult;
        if(!expr1.getType().equals(new IntType()) || !expr2.getType().equals(new IntType()))
            throw new MyException("The required type for the comparison in int.\n");
        IntValue expr1Int = (IntValue) expr1;
        IntValue expr2Int = (IntValue) expr2;

        evalResult = switch (comparisonOperator) {
            case "<" -> expr1Int.getVal() < expr2Int.getVal();
            case "<=" -> expr1Int.getVal() <= expr2Int.getVal();
            case ">" -> expr1Int.getVal() > expr2Int.getVal();
            case ">=" -> expr1Int.getVal() >= expr2Int.getVal();
            case "==" -> expr1.equals(expr2);
            case "!=" -> !expr1.equals(expr2);
            default -> throw new MyException(comparisonOperator + " is not a valid comparison operator.");
        };

        return new BoolValue(evalResult);
    }

    @Override
    public Type typeCheck(MyIDict<String,Type> typeEnv) throws MyException{
        Type type1, type2;
        type1=expression_left.typeCheck(typeEnv);
        type2=expression_right.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }
}