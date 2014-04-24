package com.mgnyniuk.ui;

import com.mgnyniuk.com.mgnyniuk.experiment.Experiment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by maksym on 4/23/14.
 */
public class ChoosingExperimentWindow {

    public static Scene getChoosingExperimentScene() {
        Group choosingExperimentGroup = new Group();

        GridPane gridPane = new GridPane();

        Label chooseLbl = new Label("Оберіть один з експерементів:");

        ToggleGroup experimentsTg = new ToggleGroup();

        VBox expRadioButtonVbox = new VBox();
        expRadioButtonVbox.setSpacing(5);

        // Matrix Multiply Experiment
        RadioButton matrixMultiplyRb = new RadioButton(Experiment.MATRIXMULTIPLY.getValue());
        matrixMultiplyRb.setUserData(Experiment.MATRIXMULTIPLY);
        matrixMultiplyRb.setToggleGroup(experimentsTg);
        matrixMultiplyRb.setSelected(true);

        // N Body Experiment
        RadioButton nBodyRb = new RadioButton(Experiment.NBODY.getValue());
        nBodyRb.setUserData(Experiment.NBODY);
        nBodyRb.setToggleGroup(experimentsTg);

        expRadioButtonVbox.getChildren().add(matrixMultiplyRb);
        expRadioButtonVbox.getChildren().add(nBodyRb);

        HBox controlButtonsHbox = new HBox();
        controlButtonsHbox.setSpacing(20);

        // toggle event processing

        experimentsTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle2) {
                    if (experimentsTg.getSelectedToggle() != null) {
                            if (experimentsTg.getSelectedToggle().getUserData() == Experiment.MATRIXMULTIPLY) {
                                System.out.println(Experiment.MATRIXMULTIPLY.getValue());
                            } else if (experimentsTg.getSelectedToggle().getUserData() == Experiment.NBODY) {
                                System.out.println(Experiment.NBODY.getValue());
                            }
                    }
            }
        });

        Button okButton = new Button("Ок");
        okButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)okButton.getScene().getWindow();
            MainWindow.setInputsGridPaneVisiblity(true);
            parentStage.close();
        });
        Button cancelButton = new Button("Скасувати");
        cancelButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)cancelButton.getScene().getWindow();
            parentStage.close();
        });

        controlButtonsHbox.getChildren().add(okButton);
        controlButtonsHbox.getChildren().add(cancelButton);

        GridPane.setConstraints(chooseLbl, 0, 0);
        GridPane.setMargin(chooseLbl, new Insets(10, 10, 10, 10));
        GridPane.setHalignment(chooseLbl, HPos.LEFT);

        GridPane.setConstraints(expRadioButtonVbox, 0, 1);
        GridPane.setMargin(expRadioButtonVbox, new Insets(10, 10, 10, 10));
        GridPane.setHalignment(expRadioButtonVbox, HPos.LEFT);

        GridPane.setConstraints(controlButtonsHbox, 0, 2);
        GridPane.setMargin(controlButtonsHbox, new Insets(10, 10, 10, 90));
        GridPane.setHalignment(controlButtonsHbox, HPos.CENTER);

        gridPane.getChildren().addAll(chooseLbl, expRadioButtonVbox, controlButtonsHbox);

        choosingExperimentGroup.getChildren().add(gridPane);

        return new Scene(choosingExperimentGroup, 250, 150);
    }
}
