package com.gui.interpreter;

import controller.Controller;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.exception.MyException;
import model.statement.IStatement;
import model.ProgramState;
import model.value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import utils.*;

import java.io.BufferedReader;
import java.util.*;

public class runProgram {
    IController controller;
    MyHeap heap;
    MyIList<Value> out;
    MyIDict<String, BufferedReader> file;

    MyIStack<IStatement> exe;

    MyISemaphore semaphores;

    public runProgram(IController controller) {
        this.controller = controller;
    }

    @FXML
    private ListView<IStatement> exeStack;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    @FXML
    private Button nextStepButton;

    @FXML
    private ListView<String> outputTable;

    @FXML
    private ListView<Integer> prgState;

    @FXML
    private TextField prgStateNumber;

    @FXML
    private TableView<Pair<String, Value>> symbolTable;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, List<Integer>>>> semaphoreTable;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddress;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapValue;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolName;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolValue;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreIndex;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreValue;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, List<Integer>> semaphoreList;

    @FXML
    public void update() throws MyException{
        Integer selectedProgramId = this.prgState.getSelectionModel().getSelectedItem();
        this.prgState.getItems().clear();
        this.heapTable.getItems().clear();
        this.outputTable.getItems().clear();
        this.fileTable.getItems().clear();
        this.symbolTable.getItems().clear();
        this.exeStack.getItems().clear();
        this.semaphoreTable.getItems().clear();

        this.prgStateNumber.setText(this.controller.getPrgStates().size().toString());
        this.controller.getPrgStates().forEach(prgState -> this.prgState.getItems().add(prgState.getId()));

        if (this.controller.getPrgStates().size() > 0) {
            this.heap = this.controller.getPrgStates().get(0).getHeap();
            this.out = this.controller.getPrgStates().get(0).getOut();
            this.file = this.controller.getPrgStates().get(0).getFileTable();
            this.exe = this.controller.getPrgStates().get(0).getExeStack();
            this.semaphores = this.controller.getPrgStates().get(0).getSemaphoreTable();
        }

        if (this.heap != null) {
            for(Integer i : heap.getContent().keySet()){
                heapTable.getItems().add(new Pair<>(i, heap.get(i)));
            }
        }

        if (this.out != null) {
            this.out.forEach(output
                    -> this.outputTable.getItems().add(output.toString()));
        }

        if (this.file != null) {
            this.file.getContent().forEach((key, value)
                    -> this.fileTable.getItems().add(key));
        }

        if(this.semaphores != null){
            for(Integer i : semaphores.getSemaphoreTable().keySet()){
                semaphoreTable.getItems().add(new Pair<>(i, semaphores.getSemaphoreTable().get(i)));
            }
        }

        ProgramState current;
        try{
            if(this.controller.getPrgStates().size() == 1) {
                current = this.controller.getPrgStates().get(0);
            }
            else{
                current = this.controller.getPrgStates().stream().filter(x -> Integer.valueOf(x.getId()).equals(selectedProgramId)).findAny().get();
                //if(this.controller.getPrgStates().size() == 1);
            }
            current.getSymTable().getContent().forEach((x, y) -> this.symbolTable.getItems().add(new Pair<>(x, y)));
            List<IStatement> statementList = current.getExeStack().toList();
            for (int i = statementList.size() - 1; i >= 0; i--) {
                this.exeStack.getItems().add(statementList.get(i));
            }

            this.prgState.getSelectionModel().select(selectedProgramId);
        } catch (NoSuchElementException e) {
            return ;
        } finally {
            this.prgState.refresh();
            this.heapTable.refresh();
            this.outputTable.refresh();
            this.fileTable.refresh();
            this.symbolTable.refresh();
            this.exeStack.refresh();
            this.semaphoreTable.refresh();
        }
    }

    @FXML
    public void initialize() throws MyException{
        this.heapAddress.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        this.symbolName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.symbolValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        this.semaphoreIndex.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.semaphoreValue.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.semaphoreList.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getValue()));
        this.update();

        this.nextStepButton.setOnAction(actionEvent -> {
            try {
                if((this.controller.getPrgStates().size() == 1 && !this.controller.getPrgStates().get(0).getExeStack().isEmpty()) || this.controller.getPrgStates().size() != 1)
                    this.controller.oneStepForAllPrg(controller.getPrgStates());
                this.update();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        this.prgState.setOnMouseClicked(x -> {
            try {
                this.update();
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
