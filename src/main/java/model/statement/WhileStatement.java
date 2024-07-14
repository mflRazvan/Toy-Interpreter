package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.expression.Expression;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;
import utils.MyIDict;
import utils.MyIStack;

public class WhileStatement implements IStatement {
    private final Expression expression;
    IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    /*public WhileStatement(Expression expression) {
        this.expression = expression;
    }*/

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression, this.statement);
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!(value.getType() instanceof BoolType)){
            throw new MyException("The while condition should evaluate to a Boolean Type");
        }
        if(((BoolValue)value).getVal()){
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    /*@Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> executionStack = state.getExeStack();

        Value evaluate = expression.evaluate(state.getSymTable(), state.getHeap());

        if (!evaluate.getType().equals(new BoolType())) {
            System.out.println(evaluate.getType());
            throw new MyException("While expression not a boolean! ");
        }

        BoolValue expValue = (BoolValue) evaluate;

        if (expValue.getVal())
        {
            IStatement toExecute = executionStack.pop();
            IStatement copyToExecute = toExecute.deepCopy();

            executionStack.push(copyToExecute);
            executionStack.push(this.deepCopy());
            executionStack.push(toExecute);
        }
        else{
            executionStack.pop();
        }

        state.setExeStack(executionStack);
        return null;
    }*/

    @Override
    public MyIDict<String, Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type type = expression.typeCheck(typeEnv);
        if (type.equals(new BoolType())) {
            expression.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        throw new MyException("The condition of While has not the type bool");
    }

    @Override
    public String toString() {
        return "While(" + expression + ")";
    }
}
