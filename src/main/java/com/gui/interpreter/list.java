package com.gui.interpreter;

import controller.IController;
import hardcoded.HardcodedPrograms;
import model.exception.MyException;
import model.statement.IStatement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class list {
    IController controller;
    runProgram runProgram;

    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private Button selectProgramButton;

    public list(IController controller, runProgram runProgram){
        this.controller = controller;
        this.runProgram = runProgram;
    }

    @FXML
    public void initialize() throws MyException {
        programsListView.setItems(FXCollections.observableList(HardcodedPrograms.hardcodedPrograms));
        selectProgramButton.setOnAction(actionEvent -> {
            try{
                int index = programsListView.getSelectionModel().getSelectedIndex();
                if (index < 0) {
                    System.out.println("No index selected");
                } else if (index >= HardcodedPrograms.hardcodedPrograms.size()) {
                    System.out.println("No program at selected index");
                } else {
                    System.out.println("Selected program " + index);
                }
                this.controller.setProgram(HardcodedPrograms.hardcodedPrograms.get(index));
                this.runProgram.update();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        this.runProgram.update();
    }
}
