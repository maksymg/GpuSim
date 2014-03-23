package com.mgnyniuk.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Group;

/**
 * Created by maksym on 3/23/14.
 */
public class MainWindow extends Application {

    private static final Image NEW_BTN = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_new.png"));

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root, 700, 500));

        // New Experiment Button
        ImageView newBtn = new ImageView(NEW_BTN);
        Button newExperimentBtn = new Button();
        newExperimentBtn.setGraphic(newBtn);

        // HBox with spacing 5
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(newExperimentBtn);
        hBox.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(hBox);
    }

    @Override
    public void start(Stage stage) throws Exception {
        init(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
