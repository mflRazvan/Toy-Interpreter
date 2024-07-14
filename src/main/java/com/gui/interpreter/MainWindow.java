package com.gui.interpreter;

import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;

import java.util.concurrent.Executors;

public class MainWindow extends Application {
    private static IController controller;

    public static void setController(IController controller) {
        MainWindow.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            controller = new Controller(new Repository("log.txt"), Executors.newFixedThreadPool(2), true);
            FXMLLoader mainWindowLoader = new FXMLLoader(MainWindow.class.getResource("runProgram.fxml"));
            mainWindowLoader.setControllerFactory(c -> new runProgram(controller));

            Parent mainWindowRoot = mainWindowLoader.load();
            runProgram mainController = mainWindowLoader.getController();

            primaryStage.setTitle("Example");
            primaryStage.setScene(new Scene(mainWindowRoot));
            primaryStage.show();

            Stage secondaryStage = new Stage();

            FXMLLoader secondWindowLoader = new FXMLLoader(MainWindow.class.getResource("list.fxml"));

            secondWindowLoader.setControllerFactory(c -> new list(controller, mainController));

            Parent secondWindowRoot = secondWindowLoader.load();
            list listCont = secondWindowLoader.getController();

            secondaryStage.setTitle("Interpreter");
            secondaryStage.setScene(new Scene(secondWindowRoot));
            secondaryStage.show();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
