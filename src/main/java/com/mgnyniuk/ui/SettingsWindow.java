package com.mgnyniuk.ui;

import com.mgnyniuk.experiment.Settings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private static Text settingLblText;

    private static Label quantityOfParallelSimulationLbl;
    public static TextField quantityOfParallelSimulationTextField;

    public static Scene getSettingsWindowScene(Settings settings) {

        Group settingsGroup = new Group();
        GridPane gridPane = new GridPane();
        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Скасувати");
        HBox controlButtonsHbox = new HBox();

        settingLblText = new Text("Налаштування паралельної поведінки:");
        settingLblText.setStyle("-fx-font-weight: bold");

        quantityOfParallelSimulationLbl = new Label("Кількість паралельних симуляцій:");
        quantityOfParallelSimulationTextField = new TextField(settings.getQuantityOfParallelSimulation().toString());

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(settingLblText, 1, 1);
        gridPane.setColumnSpan(settingLblText, 2);
        GridPane.setMargin(settingLblText, new Insets(0, 0, 15, 0));

        gridPane.add(quantityOfParallelSimulationLbl, 1, 2);
        gridPane.add(quantityOfParallelSimulationTextField, 2, 2);

        // Prepare Hbox for buttons
        controlButtonsHbox.setSpacing(20);
        // Ok Button save event
        okButton.setOnAction(actionEvent -> {
            settings.setQuantityOfParallelSimulation(Integer.parseInt(quantityOfParallelSimulationTextField.getText()));
            Stage parentStage = (Stage)okButton.getScene().getWindow();
            parentStage.close();
        });

        // Cancel Button event
        cancelButton.setOnAction(actionEvent -> {
            Stage parentStage = (Stage)cancelButton.getScene().getWindow();
            parentStage.close();
        });

        controlButtonsHbox.getChildren().addAll(okButton, cancelButton);

        gridPane.add(controlButtonsHbox, 1, 3);

        settingsGroup.getChildren().add(gridPane);

        return new Scene(settingsGroup, 400, 300);

    }

}
