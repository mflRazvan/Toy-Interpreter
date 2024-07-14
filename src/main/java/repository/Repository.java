package repository;

import model.ProgramState;
import model.exception.MyException;
import utils.MyIList;
import utils.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Repository implements IRepository{

    private MyIList<ProgramState> programStateList;

    private String logFilePath;

    public Repository() {
        programStateList = new MyList<>();
        this.logFilePath = "";
    }

    public Repository(String logFile) {
        programStateList = new MyList<>();
        this.logFilePath = logFile;
    }

    public Repository(MyIList<ProgramState> programStateList){
        this.programStateList = programStateList;
        this.logFilePath = "";
    }

    public Repository(MyIList<ProgramState> programStateList, String logFilePath){
        this.programStateList = programStateList;
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCurrentProgramState() {
        try {
            return programStateList.get(0);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
    }

    @Override
    public void logPrgStateExec(ProgramState prgState) throws MyException {
        try{
            PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(prgState.toString());
            logFile.close();
        }
        catch(IOException ex)
        {
            throw new MyException(ex.getMessage());
        }
    }

    @Override
    public MyIList<ProgramState> getPrgList(){
        return programStateList;
    }

    @Override
    public void setPrgList(MyIList<ProgramState> stateList){
        programStateList = stateList;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "programStateList=" + programStateList +
                '}';
    }

    @Override
    public void clear(){
        this.programStateList.clear();
    }

    @Override
    public void add(ProgramState programState) {
        programStateList.add(programState);
    }
}
