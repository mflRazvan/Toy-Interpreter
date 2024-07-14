package model.statement;

import model.ProgramState;
import model.exception.MyException;
import model.exception.VarNotDeclException;
import model.exception.WrongTypeException;
import model.expression.Expression;
import model.type.Type;
import model.value.Value;
import utils.MyHeap;
import utils.MyIDict;

public class AssignStatement implements IStatement{
    String id;

    Expression expression;

    public AssignStatement(String id, Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString(){
        return id+" = "+ expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDict<String, Value> symTbl= state.getSymTable();
        MyHeap heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            Value val = expression.evaluate(symTbl, heap);
            Type typeId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typeId))
                symTbl.update(id, val);
            else {
                System.out.println("Type Id: " + typeId + " Var Id: " + val.getType());
                throw new WrongTypeException("declared type of variable " + id + " and type of the assigned expression do not match");
            }
        }
        else throw new VarNotDeclException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDict<String,Type> typeCheck(MyIDict<String, Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookUp(id);
        Type typexp = expression.typeCheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStatement deepCopy(){
        return new AssignStatement(id, expression);
    }
}
