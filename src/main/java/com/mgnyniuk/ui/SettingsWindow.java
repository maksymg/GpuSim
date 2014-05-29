package com.mgnyniuk.ui;

import com.hazelcast.core.Hazelcast;
import com.mgnyniuk.experiment.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by maksym on 5/12/14.
 */
public class SettingsWindow {

    private static Text settingsParallelLblText;
    private static Text settingsDistributedLblText;

    private static Label quantityOfParallelSimulationLbl;
    public static TextField quantityOfParallelSimulationTextField;

    private static Boolean isDistributedSimulation = false;

    public static Scene getSettingsWindowScene(Settings settings) {

        Group settingsGroup = new Group();
        GridPane gridPane = new GridPane();
        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Скасувати");
        HBox controlButtonsHbox = new HBox();

        CheckBox distributedSimulationCheckBox = new CheckBox("Розподілена симуляція");
        distributedSimulationCheckBox.setIndeterminate(false);
        distributedSimulationCheckBox.setSelected(isDistributedSimulation);

        settingsParallelLblText = new Text("Налаштування паралельної поведінки:");
        settingsParallelLblText.setStyle("-fx-font-weight: bold");

        quantityOfParallelSimulationLbl = new Label("Кількість паралельних симуляцій:");
        quantityOfParallelSimulationTextField = new TextField(settings.getQuantityOfParallelSimulation().toString());

        settingsDistributedLblText = new Text("Налашутування розподіленої поведінки:");
        settingsDistributedLblText.setStyle("-fx-font-weight: bold");

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(settingsParallelLblText, 1, 1);
        gridPane.setColumnSpan(settingsParallelLblText, 2);
        GridPane.setMargin(settingsParallelLblText, new Insets(0, 0, 15, 0));

        gridPane.add(quantityOfParallelSimulationLbl, 1, 2);
        gridPane.add(quantityOfParallelSimulationTextField, 2, 2);

        gridPane.add(settingsDistributedLblText, 1, 3);
        gridPane.setColumnSpan(settingsDistributedLblText, 2);
        GridPane.setMargin(settingsDistributedLblText, new Insets(10, 0, 15, 0));

        gridPane.add(distributedSimulationCheckBox, 1, 4);
        gridPane.setColumnSpan(distributedSimulationCheckBox, 2);
        GridPane.setMargin(distributedSimulationCheckBox, new Insets(0, 0, 15, 0));

        // Prepare Hbox for buttons
        controlButtonsHbox.setSpacing(20);

        // checkBox event
        distributedSimulationCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean old_val, Boolean new_val) {
                isDistributedSimulation = new_val;
            }
        });

        // Ok Button save event
        okButton.setOnAction(actionEvent -> {
            settings.setQuantityOfParallelSimulation(Integer.parseInt(quantityOfParallelSimulationTextField.getText()));
            settings.setIsDistributedSimulation(isDistributedSimulation);

            if (isDistributedSimulation) {
                MainWindow.hzInstance = Hazelcast.newHazelcastInstance();
            } else {
                Hazelcast.shutdownAll();
            }
            Stage parentStage = (Stage)okButton.getScene().getWindow();
            parentStage.close();
        });

        // Cancel Button event
        cancelButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)cancelButton.getScene().getWindow();
            parentStage.close();
        });

        controlButtonsHbox.getChildren().addAll(okButton, cancelButton);

        gridPane.add(controlButtonsHbox, 1, 5);

        settingsGroup.getChildren().add(gridPane);

        return new Scene(settingsGroup, 400, 300);

    }

}
