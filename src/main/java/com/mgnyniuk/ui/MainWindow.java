package com.mgnyniuk.ui;

import com.gpusim2.config.GridSimOutput;
import com.mgnyniuk.core.ConfigGenerator;
import com.mgnyniuk.core.ConfigurationUtil;
import com.mgnyniuk.core.ExperimentRunner;
import com.mgnyniuk.experiment.MatrixMultiplyExperiment;
import com.sun.javafx.css.converters.StringConverter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Group;


import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maksym on 3/23/14.
 */
public class MainWindow extends Application {

    private static final Image NEW_BTN = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_new.png"));
    private static final Image RUN_MODELING = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_runModeling.png"));

    // Inputs and labels for settings
    private static Label mainParametersLbl;

    private static Label experimentNameLbl;
    private static TextField experimentNameTextField;

    private static Label experimentDescriptionLbl;
    private static TextField experimentDescriptionTextField;

    private static Label experimentConditionsLbl;

    private static Label minMatrixSizeLbl;
    private static TextField minMatrixSizeTextField;

    private static Label maxMatrixSizeLbl;
    private static TextField maxMatrixSizeTextField;

    private static Label matrixSizeIncrementLbl;
    private static TextField matrixSizeIncrementTextField;

    private static Label blockSizeLbl;
    private static TextField blockSizeTextField;

    private static CheckBox modelingParametersChkBox;

    private static Label numberOfCpuLbl;
    private static TextField numberOfCpuTextField;

    private static Label rankOfCpuLbl;
    private static TextField rankOfCpuTextField;

    private static Label numberOfGpuLbl;
    private static TextField numberOfGpuTextField;

    private static Label rankOfGpuLbl;
    private static TextField rankOfGpuTextField;

    private static Label resourceCapacityLbl;
    private static TextField resourceCapacityTextField;

    private static Label linkCapacityLbl;
    private static TextField linkCapacityTextField;

    private static Label loadOperationCostLbl;
    private static TextField loadOperationCostTextField;

    private static Label saveOperationCostLbl;
    private static TextField saveOperationCostTextField;

    public static void prepareFieldsForMatrixMultiplyExperiment(MatrixMultiplyExperiment matrixMultiplyExperiment) {
        // Inputs and labels for settings
        mainParametersLbl = new Label("Основні параметри експеременту:");

        experimentNameLbl = new Label("Ім'я:");
        experimentNameTextField = new TextField("<No Experiment Name>");

        experimentDescriptionLbl = new Label("Опис:");
        experimentDescriptionTextField = new TextField("<No Experiment Description>");

        experimentConditionsLbl = new Label("Граничні умови експерименту:");

        minMatrixSizeLbl = new Label("Мінімальний розмір матриць:");
        minMatrixSizeTextField = new TextField(matrixMultiplyExperiment.getMinMatrixSize().toString());

        maxMatrixSizeLbl = new Label("Максимальний розмір матриць:");
        maxMatrixSizeTextField = new TextField(matrixMultiplyExperiment.getMaxMatrixSize().toString());

        matrixSizeIncrementLbl = new Label("Інкремент розміру матриць:");
        matrixSizeIncrementTextField = new TextField(matrixMultiplyExperiment.getMatrixSizeIncrement().toString());

        blockSizeLbl = new Label("Розмір блоку:");
        blockSizeTextField = new TextField(matrixMultiplyExperiment.getBlockSize().toString());

        modelingParametersChkBox = new CheckBox("Параметри моделювання:");
        // setting modelingParametersChkBox checkBox
        modelingParametersChkBox.setIndeterminate(false);
        modelingParametersChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                setVisibleModelingParameterBlock(new_val ? true : false);
            }
        });

        numberOfCpuLbl = new Label("Кількість обчислювальних елементів CPU:");
        numberOfCpuTextField = new TextField(matrixMultiplyExperiment.getNumberOfCpu().toString());

        rankOfCpuLbl = new Label("Рейтинг обчислювальних елементів CPU:");
        rankOfCpuTextField = new TextField(matrixMultiplyExperiment.getRankOfCpu().toString());

        numberOfGpuLbl = new Label("Кількість обчислювальних елементів GPU:");
        numberOfGpuTextField = new TextField(matrixMultiplyExperiment.getNumberOfGpu().toString());

        rankOfGpuLbl = new Label("Рейтинг обчислювальних елементів в GPU:");
        rankOfGpuTextField = new TextField(matrixMultiplyExperiment.getRankOfGpu().toString());

        resourceCapacityLbl = new Label("Пропускна здатність ресурсу (Мб/с):");
        resourceCapacityTextField = new TextField(matrixMultiplyExperiment.getResourceCapacity().toString());

        linkCapacityLbl = new Label("Пропускна здатність каналу зв'язку (Мб/с):");
        linkCapacityTextField = new TextField(matrixMultiplyExperiment.getLinkCapacity().toString());

        loadOperationCostLbl = new Label("Вартість операції завантаження данних:");
        loadOperationCostTextField = new TextField(matrixMultiplyExperiment.getLoadOperationCost().toString());

        saveOperationCostLbl = new Label("Вартість операції збереження данних:");
        saveOperationCostTextField = new TextField(matrixMultiplyExperiment.getSaveOperationCost().toString());

        inputsGridPane.add(mainParametersLbl, 1, 1);
        inputsGridPane.setColumnSpan(mainParametersLbl, 2);

        inputsGridPane.add(experimentNameLbl, 1, 2);
        inputsGridPane.add(experimentNameTextField, 2, 2);

        inputsGridPane.add(experimentDescriptionLbl, 1, 3);
        inputsGridPane.add(experimentDescriptionTextField, 2, 3);

        inputsGridPane.add(experimentConditionsLbl, 1, 4);
        inputsGridPane.setColumnSpan(experimentConditionsLbl, 2);

        inputsGridPane.add(minMatrixSizeLbl, 1, 5);
        inputsGridPane.add(minMatrixSizeTextField, 2, 5);

        inputsGridPane.add(maxMatrixSizeLbl, 1, 6);
        inputsGridPane.add(maxMatrixSizeTextField, 2, 6);

        inputsGridPane.add(matrixSizeIncrementLbl, 1, 7);
        inputsGridPane.add(matrixSizeIncrementTextField, 2, 7);

        inputsGridPane.add(blockSizeLbl, 1, 8);
        inputsGridPane.add(blockSizeTextField, 2, 8);

        inputsGridPane.add(modelingParametersChkBox, 1, 9);
        inputsGridPane.setColumnSpan(modelingParametersChkBox, 2);

        inputsGridPane.add(numberOfCpuLbl, 1, 10);
        inputsGridPane.add(numberOfCpuTextField, 2, 10);

        inputsGridPane.add(rankOfCpuLbl, 1, 11);
        inputsGridPane.add(rankOfCpuTextField, 2, 11);

        inputsGridPane.add(numberOfGpuLbl, 1, 12);
        inputsGridPane.add(numberOfGpuTextField, 2, 12);

        inputsGridPane.add(rankOfGpuLbl, 1, 13);
        inputsGridPane.add(rankOfGpuTextField, 2, 13);

        inputsGridPane.add(resourceCapacityLbl, 1, 14);
        inputsGridPane.add(resourceCapacityTextField, 2, 14);

        inputsGridPane.add(linkCapacityLbl, 1, 15);
        inputsGridPane.add(linkCapacityTextField, 2, 15);

        inputsGridPane.add(loadOperationCostLbl, 1, 16);
        inputsGridPane.add(loadOperationCostTextField, 2, 16);

        inputsGridPane.add(saveOperationCostLbl, 1, 17);
        inputsGridPane.add(saveOperationCostTextField, 2, 17);
    }

    private static GridPane inputsGridPane = new GridPane();

    public static void setInputsGridPaneVisiblity(boolean isVisible) {
        inputsGridPane.setVisible(isVisible);
        // set modelingParameters block invisible
        setVisibleModelingParameterBlock(false);
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root, 800, 800));

        // GridPane for inputs

        inputsGridPane.setVisible(false);
        inputsGridPane.setHgap(5);
        inputsGridPane.setVgap(5);



        // New Experiment Button
        ImageView newBtnImage = new ImageView(NEW_BTN);
        Button newExperimentBtn = new Button();
        newExperimentBtn.setOnAction(actionEvent -> {
            try {
                Stage choosingExperimentStage = new Stage();

                choosingExperimentStage.setScene(ChoosingExperimentWindow.getChoosingExperimentScene());
                choosingExperimentStage.setResizable(false);
                choosingExperimentStage.show();

                //inputsGridPane.setVisible(true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        newExperimentBtn.setGraphic(newBtnImage);

        // Run Simulation Button
        ImageView runSimulationImage = new ImageView(RUN_MODELING);
        Button runSumulationBtn = new Button();
        runSumulationBtn.setOnAction(actionEvent -> {
            try {
                int minMatrixSize = Integer.parseInt(minMatrixSizeTextField.getText());
                int maxMatrixSize = Integer.parseInt(maxMatrixSizeTextField.getText());
                int matrixSizeIncrement = Integer.parseInt(matrixSizeIncrementTextField.getText());
                int blockSize = Integer.parseInt(blockSizeTextField.getText());
                int numberOfCpu = Integer.parseInt(numberOfCpuTextField.getText());
                int numberOfGpu = Integer.parseInt(numberOfGpuTextField.getText());
                int rankOfCpu = Integer.parseInt(numberOfCpuTextField.getText());
                int rankOfGpu = Integer.parseInt(rankOfGpuTextField.getText());
                double resourceCapacity = Double.parseDouble(resourceCapacityTextField.getText());
                double linkCapacity = Double.parseDouble(linkCapacityTextField.getText());
                double loadOperationCost = Double.parseDouble(loadOperationCostTextField.getText());
                double saveOperationCost = Double.parseDouble(saveOperationCostTextField.getText());

                ConfigGenerator.generateMatrixMutiplyConfigs(minMatrixSize, maxMatrixSize, matrixSizeIncrement,
                        blockSize, numberOfCpu, rankOfCpu, numberOfGpu, rankOfGpu, resourceCapacity, linkCapacity, loadOperationCost, saveOperationCost);

                ExperimentRunner experimentRunner = new ExperimentRunner(16, 2, null, 0);
                experimentRunner.runExperimnet();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        runSumulationBtn.setGraphic(runSimulationImage);

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
        HBox buttonsHBox = new HBox(5);
        buttonsHBox.getChildren().addAll(newExperimentBtn, runSumulationBtn, showResultsBtn);
        buttonsHBox.setAlignment(Pos.TOP_LEFT);

        GridPane masterGridPane = new GridPane();
        masterGridPane.add(buttonsHBox, 1, 1);


        masterGridPane.add(inputsGridPane, 1, 2);
        root.getChildren().add(masterGridPane);
    }

    protected AreaChart<Number, Number> createResultsChart() throws FileNotFoundException {
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

        for (int i = 0; i < matrixSizeList.size() - 1; i++) {
            series.getData().add(new XYChart.Data<Number, Number>(matrixSizeList.get(i), outputList.get(i).getTotalSimulationTime()));
        }

        ac.getData().add(series);

        return ac;
    }

    private static void setVisibleModelingParameterBlock(boolean isVisible) {
        numberOfCpuLbl.setVisible(isVisible);
        numberOfCpuTextField.setVisible(isVisible);

        rankOfCpuLbl.setVisible(isVisible);
        rankOfCpuTextField.setVisible(isVisible);

        numberOfGpuLbl.setVisible(isVisible);
        numberOfGpuTextField.setVisible(isVisible);

        rankOfGpuLbl.setVisible(isVisible);
        rankOfGpuTextField.setVisible(isVisible);

        resourceCapacityLbl.setVisible(isVisible);
        resourceCapacityTextField.setVisible(isVisible);

        linkCapacityLbl.setVisible(isVisible);
        linkCapacityTextField.setVisible(isVisible);

        loadOperationCostLbl.setVisible(isVisible);
        loadOperationCostTextField.setVisible(isVisible);

        saveOperationCostLbl.setVisible(isVisible);
        saveOperationCostTextField.setVisible(isVisible);
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
