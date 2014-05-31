package com.mgnyniuk.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by maksym on 5/29/14.
 */
public class ProgressWindow {

    public static ProgressBar pb;

    public static Scene getProgressWindowScene() {

        GridPane gridPane = new GridPane();

        Button startButton = new Button("Старт");
        Button cancelButton = new Button("Закрити");

        startButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)startButton.getScene().getWindow();
            pb.setProgress(0);
            MainWindow.runSimulation();
            pb.setProgress(1);
            MainWindow.showResultsBtn.setDisable(false);
            MainWindow.showResults();
            parentStage.close();
        });

        cancelButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)cancelButton.getScene().getWindow();
            parentStage.close();
        });

        HBox controlButtonsHbox = new HBox();
        controlButtonsHbox.setSpacing(20);
        controlButtonsHbox.getChildren().add(startButton);
        controlButtonsHbox.getChildren().add(cancelButton);

        pb = new ProgressBar();

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);

        GridPane.setConstraints(pb, 0, 0);
        GridPane.setHalignment(pb, HPos.CENTER);

        GridPane.setConstraints(controlButtonsHbox, 0, 1);
        GridPane.setMargin(controlButtonsHbox, new Insets(10, 0, 10, 60));
        GridPane.setHalignment(controlButtonsHbox, HPos.CENTER);

        gridPane.getColumnConstraints().add(col1);

        gridPane.getChildren().addAll(pb, controlButtonsHbox);

        return new Scene(gridPane, 300, 100);
    }
}
