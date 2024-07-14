package hardcoded;

import controller.Controller;
import model.ProgramState;
import model.expression.VariableExpression;
import model.statement.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import model.expression.*;
import model.statement.File.*;
import repository.IRepository;
import repository.Repository;
import utils.MyIList;
import utils.MyList;

import java.util.ArrayList;
import java.util.List;

public class HardcodedPrograms {
    private static IStatement buildStatements(IStatement... statements){
        if(statements.length == 0){
            return new NopStatement();
        }
        if(statements.length == 1){
            return statements[0];
        }
        IStatement finalStatement = new CompStatement(statements[0], statements[1]);
        for(int i = 2; i < statements.length; i ++)
            finalStatement = new CompStatement(finalStatement, statements[i]);
        return finalStatement;
    }

    public static final List<IStatement> hardcodedPrograms = new ArrayList<IStatement>(List.of(
            new CompStatement(new VarDeclStatement("v", new IntType()), new AssignStatement("v", new ValueExpression(new IntValue(2)))),

            buildStatements(new VarDeclStatement("a", new IntType()),
                new VarDeclStatement("b", new IntType()),
                new AssignStatement("a", new ArithmeticExpr(new ValueExpression(new IntValue(2)), new
                        ArithmeticExpr(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), Operation.MULTIPLY), Operation.PLUS)),
                new AssignStatement("b", new ArithmeticExpr(new VariableExpression("a"), new
                        ValueExpression(new IntValue(1)), Operation.PLUS)), new PrintStatement(new VariableExpression("b"))),

            buildStatements(new VarDeclStatement("a", new BoolType()),
                new AssignStatement("a", new ValueExpression(new BoolValue(false))),
                new VarDeclStatement("v", new IntType()),
                new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))),

            buildStatements( new VarDeclStatement("varf", new StringType()),
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                new OpenFileStatement(new VariableExpression("varf")),
                new VarDeclStatement("vare", new IntType()),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new CloseFileStatement(new VariableExpression("varf"))),

            buildStatements(new VarDeclStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a"))),

            buildStatements(new VarDeclStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new PrintStatement(new HeapRead(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpr(
                        new HeapRead(new HeapRead(new VariableExpression("a"))),
                        new ValueExpression(new IntValue(5)),
                        Operation.PLUS))),

            buildStatements(new VarDeclStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new PrintStatement(new HeapRead(new VariableExpression("v"))),
                new HeapWrite("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpr(
                        new HeapRead(new VariableExpression("v")),
                        new ValueExpression(new IntValue(5)),
                        Operation.PLUS))),

            buildStatements(new VarDeclStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new HeapRead(new HeapRead(new VariableExpression("a"))))),

            buildStatements(new VarDeclStatement("v", new IntType()),
                new AssignStatement("v", new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(
                        new VariableExpression("v"),
                        new ValueExpression(new IntValue(0)),
                        ">"),
                new CompStatement(
                        new PrintStatement(new VariableExpression ("v")),
                        new AssignStatement("v",
                                new ArithmeticExpr(
                                        new VariableExpression("v"),
                                        new ValueExpression(new IntValue(1)),
                                        Operation.MINUS)))),
                new PrintStatement(new VariableExpression("v"))),

            buildStatements(new VarDeclStatement("v", new IntType()),
                new VarDeclStatement("a", new RefType(new IntType())),
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new NewStatement("a", new ValueExpression(new IntValue(22))),
                new ForkStatement(buildStatements(new HeapWrite("a", new ValueExpression(new IntValue(30))), new AssignStatement("v", new ValueExpression(new IntValue(32))), new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapRead(new VariableExpression("a"))))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new HeapRead(new VariableExpression("a")))),

            buildStatements(new VarDeclStatement("v1", new RefType(new IntType())),
                    new VarDeclStatement("cnt", new IntType()),
                    new NewStatement("v1", new ValueExpression(new IntValue(2))),
                    new CreateSemaphoreStatement("cnt", new HeapRead(new VariableExpression("v1"))),
                    new ForkStatement(
                            buildStatements(
                                    new AcquireStatement("cnt"),
                                    new HeapWrite("v1",
                                            new ArithmeticExpr(
                                                    new HeapRead(new VariableExpression("v1")),
                                                    new ValueExpression(new IntValue(10)),
                                                    Operation.MULTIPLY)),
                                    new PrintStatement(new HeapRead(new VariableExpression("v1"))),
                                    new ReleaseStatement("cnt"))),
                    new ForkStatement(
                            buildStatements(
                                    new AcquireStatement("cnt"),
                                    new HeapWrite("v1",
                                            new ArithmeticExpr(
                                                    new HeapRead(new VariableExpression("v1")),
                                                    new ValueExpression(new IntValue(10)),
                                                    Operation.MULTIPLY)),
                                    new HeapWrite("v1",
                                            new ArithmeticExpr(
                                                    new HeapRead(new VariableExpression("v1")),
                                                    new ValueExpression(new IntValue(2)),
                                                    Operation.MULTIPLY)),
                                    new PrintStatement(new HeapRead(new VariableExpression("v1"))),
                                    new ReleaseStatement("cnt"))),
                    new AcquireStatement("cnt"),
                    new PrintStatement(new ArithmeticExpr(new HeapRead(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), Operation.MINUS)),
                    new ReleaseStatement("cnt")
            ),

            new CompStatement(new VarDeclStatement("v1", new RefType(new IntType())),
                    new CompStatement(new VarDeclStatement("cnt", new IntType()),
                            new CompStatement(
                            new NewStatement("v1", new ValueExpression(new IntValue(1))),
                            new CompStatement(new CreateSemaphoreStatement("cnt", new HeapRead(new VariableExpression("v1"))),
                                    new CompStatement(
                                        new ForkStatement(new CompStatement(
                                                new AcquireStatement("cnt"),
                                                new CompStatement(new HeapWrite("v1", new ArithmeticExpr(new HeapRead(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), Operation.MULTIPLY)),
                                                        new CompStatement(new PrintStatement(new HeapRead(new VariableExpression("v1"))),
                                                                new ReleaseStatement("cnt")))
                                    )),
                                            new CompStatement(new ForkStatement(new CompStatement(new AcquireStatement("cnt"),
                                                    new CompStatement(new HeapWrite("v1", new ArithmeticExpr(new HeapRead(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), Operation.MULTIPLY)),
                                                            new CompStatement(new HeapWrite("v1", new ArithmeticExpr(new HeapRead(new VariableExpression("v1")), new ValueExpression(new IntValue(2)), Operation.MULTIPLY)),
                                                                    new CompStatement(new PrintStatement(new HeapRead(new VariableExpression("v1"))), new ReleaseStatement("cnt")))))),
                                                    new CompStatement(new AcquireStatement("cnt"),
                                                            new CompStatement(new PrintStatement(new ArithmeticExpr(new HeapRead(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), Operation.MINUS)),
                                                                    new ReleaseStatement("cnt"))))))))),

            new CompStatement(new VarDeclStatement("a", new IntType()),
                    new CompStatement(new VarDeclStatement("b", new IntType()),
                            new CompStatement(new VarDeclStatement("c", new IntType()),
                                    new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                            new CompStatement(new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                    new CompStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                            new CompStatement(new SwitchStatement(new ArithmeticExpr(new VariableExpression("a"), new ValueExpression(new IntValue(10)), Operation.MULTIPLY),
                                                                    new ArithmeticExpr(new VariableExpression("b"), new VariableExpression("c"), Operation.MULTIPLY),
                                                                    new ValueExpression(new IntValue(10)),
                                                                    new CompStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                    new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                    new PrintStatement(new ValueExpression(new IntValue(300)))),
                                                                    new PrintStatement(new ValueExpression(new IntValue(300))))))))))
            ));
}