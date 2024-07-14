package repository;

import model.ProgramState;
import model.exception.MyException;
import utils.MyIList;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void add(ProgramState programState);

    void logPrgStateExec(ProgramState prgState) throws MyException;

    MyIList<ProgramState> getPrgList();

    void setPrgList(MyIList<ProgramState> stateList);

    void clear();
}
