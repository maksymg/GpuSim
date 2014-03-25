package com.mgnyniuk.ui;

import com.gpusim2.config.GridSimOutput;
import com.mgnyniuk.core.ConfigGenerator;
import com.mgnyniuk.core.ConfigurationUtil;
import com.mgnyniuk.core.ExperimentRunner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Group;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        newExperimentBtn.setOnAction(actionEvent -> {
            try {
                ConfigGenerator.generateMatrixMutiplyConfigs();
                ExperimentRunner experimentRunner = new ExperimentRunner(256, 1, null, 0);
                experimentRunner.runExperimnet();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        newExperimentBtn.setGraphic(newBtn);

        // Show results of simulation
        Button showResultsBtn = new Button("Show Results");
        showResultsBtn.setOnAction(actionEvent -> {
            Group resultsRoot = new Group();
            Stage resultsStage = new Stage();
            resultsStage.setScene(new Scene(resultsRoot));
            try {
                resultsRoot.getChildren().add(createResultsChart());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

            resultsStage.show();
        });

        // HBox with spacing 5
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(newExperimentBtn, showResultsBtn);
        hBox.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(hBox);

    }

    protected AreaChart<Number, Number> createResultsChart() throws FileNotFoundException{
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Matrix size");
        yAxis.setLabel("Time");

        // output list
        List<GridSimOutput> outputList = ConfigurationUtil.loadOutputs(0, 255);

        // matrix size list
        List<Integer> matrixSizeList = new ArrayList<Integer>();
        for (int i = 1; i <= 4096 / 16; i++) {
            matrixSizeList.add(16 * i);
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Time/Matrix Size");

        for (int i=0; i < matrixSizeList.size()-1; i++) {
            series.getData().add(new XYChart.Data<Number,Number>(matrixSizeList.get(i), outputList.get(i).getTotalSimulationTime()));
        }

        ac.getData().add(series);

        return ac;
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
