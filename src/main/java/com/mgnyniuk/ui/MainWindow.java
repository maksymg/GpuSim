package com.mgnyniuk.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;

/**
 * Created by maksym on 3/23/14.
 */
public class MainWindow extends Application {

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
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
