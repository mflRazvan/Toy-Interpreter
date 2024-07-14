package view;/*
package view;

import controller.Controller;
import model.exception.*;
import model.expression.*;
import model.statement.*;
import model.ProgramState;
import model.value.Value;
import repository.*;
import utils.*;
import model.type.*;
import model.value.*;

public class View {
    private IStatement ex1= new CompStatement(new VarDeclStatement("v",new IntType()),
            new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
                    VariableExpression("v"))));

    private void printMenu(){
        System.out.println("0 - exit");
        System.out.println("1 "+ ex1);
    }

    private void executeEx1() throws MyException{
        MyIStack<IStatement> stk = new MyStack<IStatement>();
        MyIDict<String, Value> symtbl = new MyDict<String,Value>();
        MyIList<Value> out = new MyList<Value>();
        ProgramState crtPrgState= new ProgramState(stk, symtbl, out, ex1);

        IRepository repo = new Repository();
        repo.add(crtPrgState);
        Controller controller = new Controller(repo);
        controller.allStep();
    }
}
*/
