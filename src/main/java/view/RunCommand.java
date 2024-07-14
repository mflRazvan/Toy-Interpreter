package view;

import controller.Controller;
import model.exception.MyException;

public class RunCommand extends Command {
    private Controller ctr;
    public RunCommand(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute(){
        try{
            ctr.allStep();
        }
        catch(MyException ex){
            System.err.println(ex.getMessage());
        }
    }
}