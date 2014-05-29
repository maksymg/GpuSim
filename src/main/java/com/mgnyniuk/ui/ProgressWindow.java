package com.mgnyniuk.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

/**
 * Created by maksym on 5/29/14.
 */
public class ProgressWindow {

    public static ProgressBar pb;

    public static Scene getProgressWindowScene() {

        Group progressBarGroup = new Group();
        GridPane gridPane = new GridPane();

        Button cancelButton = new Button();

        pb = new ProgressBar();
        pb.setProgress(20);

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(pb, 1,1);
        gridPane.setColumnSpan(pb, 3);

        progressBarGroup.getChildren().add(gridPane);

        return new Scene(progressBarGroup, 400, 200);
    }
}
