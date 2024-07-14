package controller;

import model.exception.*;
import model.statement.IStatement;
import model.ProgramState;
import model.exception.MyException;
import utils.MyIList;

import java.util.List;

public interface IController {
    public void oneStepForAllPrg(MyIList<ProgramState> stateList) throws MyException;
    public void allStep() throws MyException;
    public MyIList<ProgramState> removeCompletedPrg(MyIList<ProgramState> inPrgList);
    //public void setDisplayFlag(boolean displayFlag);
    void setProgram(IStatement statement) throws MyException;
    //public boolean getDisplayFlag();
    public MyIList<ProgramState> getPrgStates();
}
