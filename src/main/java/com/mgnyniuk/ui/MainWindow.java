package com.mgnyniuk.ui;

import com.gpusim2.config.GridSimOutput;
import com.mgnyniuk.core.ConfigGenerator;
import com.mgnyniuk.core.ConfigurationUtil;
import com.mgnyniuk.core.ExperimentRunner;
import com.mgnyniuk.experiment.Experiment;
import com.mgnyniuk.experiment.MatrixMultiplyExperiment;
import com.mgnyniuk.experiment.NBodyExperiment;
import javafx.application.Application;
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
    private static final Image RUN_MODELING = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_runModeling.png"));
    public static Experiment runningExperiment;

    // Inputs and labels for settings MatrixMultiplyExperiment
    private static Label mainParametersForMatrixMultiplyLbl;

    private static Label experimentNameForMatrixMultiplyLbl;
    private static TextField experimentNameForMatrixMultiplyTextField;

    private static Label experimentDescriptionForMatrixMultiplyLbl;
    private static TextField experimentDescriptionForMatrixMultiplyTextField;

    private static Label experimentConditionsForMatrixMultiplyLbl;

    private static Label minMatrixSizeLbl;
    private static TextField minMatrixSizeTextField;

    private static Label maxMatrixSizeLbl;
    private static TextField maxMatrixSizeTextField;

    private static Label matrixSizeIncrementLbl;
    private static TextField matrixSizeIncrementTextField;

    private static Label blockSizeLbl;
    private static TextField blockSizeTextField;

    private static CheckBox modelingParametersForMatrixMultiplyChkBox;

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

    // Inputs and labels for settings NBodyExperiment

    private static Label mainParametersForNBodyLbl;

    private static Label experimentNameForNBodyLbl;
    private static TextField experimentNameForNBodyTextField;

    private static Label experimentDescriptionForNBodyLbl;
    private static TextField experimentDescriptionForNBodyTextField;

    private static Label experimentConditionsForNBodyLbl;

    private static Label minNLbl;
    private static TextField minNTextField;

    private static Label maxNLbl;
    private static TextField maxNTextField;

    private static Label minTPBLbl;
    private static TextField minTPBTextField;

    private static Label maxTPBLbl;
    private static TextField maxTPBTextField;

    private static CheckBox modelingParametersForNBodyChkBox;

    private static Label gpuCoreRatingLbl;
    private static TextField gpuCoreRatingTextField;

    private static Label limitationsDividerLbl;
    private static TextField limitationsDividerTextField;

    private static Label smallTPBPenaltyWeightLbl;
    private static TextField smallTPBPenaltyWeightTextField;

    private static Label largeTPBPenaltyWeightLbl;
    private static TextField largeTPBPenaltyWeightTextField;

    private static Label multiplicativeLengthScaleFactorLbl;
    private static TextField multiplicativeLengthScaleFactorTextField;

    private static Label additiveLengthScaleFactorLbl;
    private static TextField additiveLengthScaleFactorTextField;

    public static void prepareFieldsForNBodyExperiment(NBodyExperiment nBodyExperiment) {

        inputsGridPane.getChildren().clear();

        // Inputs and labels for settings
        mainParametersForNBodyLbl = new Label("Основні параметри експеременту:");

        experimentNameForNBodyLbl = new Label("Ім'я");
        experimentNameForNBodyTextField = new TextField("<No Experiment Name>");

        experimentDescriptionForNBodyLbl = new Label("Опис:");
        experimentDescriptionForNBodyTextField = new TextField("<No Experiment Description>");

        experimentConditionsForNBodyLbl = new Label("Граничні умови експерименту:");

        minNLbl = new Label("Мінімальне N:");
        minNTextField = new TextField(nBodyExperiment.getMinN().toString());

        maxNLbl = new Label("Максимальне N:");
        maxNTextField = new TextField(nBodyExperiment.getMaxN().toString());

        minTPBLbl = new Label("Мінімальна кількість потоків на блок:");
        minTPBTextField = new TextField(nBodyExperiment.getMinTPB().toString());

        maxTPBLbl = new Label("Максимальна кількість потоків на блок:");
        maxTPBTextField = new TextField(nBodyExperiment.getMaxTPB().toString());

        modelingParametersForNBodyChkBox = new CheckBox("Параметри моделювання:");
        // setting modelingParametersForNBodyChkBox checkBox
        modelingParametersForNBodyChkBox.setIndeterminate(false);
        modelingParametersForNBodyChkBox.selectedProperty().addListener((ov, old_val, new_val) -> setVisibleModelingParameterBlockForNBody(new_val ? true : false));

        gpuCoreRatingLbl = new Label("Рейтинг кожного з ядер GPU:");
        gpuCoreRatingTextField = new TextField(nBodyExperiment.getGpuCoreRating().toString());

        limitationsDividerLbl = new Label("Параметр зменшення кількості завдань:");
        limitationsDividerTextField = new TextField(nBodyExperiment.getLimitationDivider().toString());

        smallTPBPenaltyWeightLbl = new Label("Ваговий коефіцієнт складової переключення контексту:");
        smallTPBPenaltyWeightTextField = new TextField(nBodyExperiment.getSmallTPBPenaltyWeight().toString());

        largeTPBPenaltyWeightLbl = new Label("Ваговий коефіцієнт складової синхронізації:");
        largeTPBPenaltyWeightTextField = new TextField(nBodyExperiment.getLargeTPBPenaltyWeight().toString());

        multiplicativeLengthScaleFactorLbl = new Label("Мультиплікативний коефіцієнт масштабування обсягу завдань:");
        multiplicativeLengthScaleFactorTextField = new TextField(nBodyExperiment.getMultiplicativeLengthScaleFactor().toString());

        additiveLengthScaleFactorLbl = new Label("Адитивний коефіцієнт масштабування обсягу завдань:");
        additiveLengthScaleFactorTextField = new TextField(nBodyExperiment.getAdditiveLengthScaleFactor().toString());

        inputsGridPane.add(mainParametersForNBodyLbl, 1, 1);
        inputsGridPane.setColumnSpan(mainParametersForNBodyLbl, 2);

        inputsGridPane.add(experimentNameForNBodyLbl, 1, 2);
        inputsGridPane.add(experimentNameForNBodyTextField, 2, 2);

        inputsGridPane.add(experimentDescriptionForNBodyLbl, 1, 3);
        inputsGridPane.add(experimentDescriptionForNBodyTextField, 2, 3);

        inputsGridPane.add(experimentConditionsForNBodyLbl, 1, 4);
        inputsGridPane.setColumnSpan(experimentConditionsForNBodyLbl, 2);

        inputsGridPane.add(minNLbl, 1, 5);
        inputsGridPane.add(minNTextField, 2, 5);

        inputsGridPane.add(maxNLbl, 1, 6);
        inputsGridPane.add(maxNTextField, 2, 6);

        inputsGridPane.add(minTPBLbl, 1, 7);
        inputsGridPane.add(minTPBTextField, 2, 7);

        inputsGridPane.add(maxTPBLbl, 1, 8);
        inputsGridPane.add(maxTPBTextField, 2, 8);

        inputsGridPane.add(modelingParametersForNBodyChkBox, 1, 9);
        inputsGridPane.setColumnSpan(modelingParametersForNBodyChkBox, 2);

        inputsGridPane.add(gpuCoreRatingLbl, 1, 10);
        inputsGridPane.add(gpuCoreRatingTextField, 2, 10);

        inputsGridPane.add(limitationsDividerLbl, 1, 11);
        inputsGridPane.add(limitationsDividerTextField, 2, 11);

        inputsGridPane.add(smallTPBPenaltyWeightLbl, 1, 12);
        inputsGridPane.add(smallTPBPenaltyWeightTextField, 2, 12);

        inputsGridPane.add(largeTPBPenaltyWeightLbl, 1, 13);
        inputsGridPane.add(largeTPBPenaltyWeightTextField, 2, 13);

        inputsGridPane.add(multiplicativeLengthScaleFactorLbl, 1, 14);
        inputsGridPane.add(multiplicativeLengthScaleFactorTextField, 2, 14);

        inputsGridPane.add(additiveLengthScaleFactorLbl, 1, 15);
        inputsGridPane.add(additiveLengthScaleFactorTextField, 2, 15);
    }

    public static void prepareFieldsForMatrixMultiplyExperiment(MatrixMultiplyExperiment matrixMultiplyExperiment) {

        inputsGridPane.getChildren().clear();

        // Inputs and labels for settings
        mainParametersForMatrixMultiplyLbl = new Label("Основні параметри експеременту:");

        experimentNameForMatrixMultiplyLbl = new Label("Ім'я:");
        experimentNameForMatrixMultiplyTextField = new TextField("<No Experiment Name>");

        experimentDescriptionForMatrixMultiplyLbl = new Label("Опис:");
        experimentDescriptionForMatrixMultiplyTextField = new TextField("<No Experiment Description>");

        experimentConditionsForMatrixMultiplyLbl = new Label("Граничні умови експерименту:");

        minMatrixSizeLbl = new Label("Мінімальний розмір матриць:");
        minMatrixSizeTextField = new TextField(matrixMultiplyExperiment.getMinMatrixSize().toString());

        maxMatrixSizeLbl = new Label("Максимальний розмір матриць:");
        maxMatrixSizeTextField = new TextField(matrixMultiplyExperiment.getMaxMatrixSize().toString());

        matrixSizeIncrementLbl = new Label("Інкремент розміру матриць:");
        matrixSizeIncrementTextField = new TextField(matrixMultiplyExperiment.getMatrixSizeIncrement().toString());

        blockSizeLbl = new Label("Розмір блоку:");
        blockSizeTextField = new TextField(matrixMultiplyExperiment.getBlockSize().toString());

        modelingParametersForMatrixMultiplyChkBox = new CheckBox("Параметри моделювання:");
        // setting modelingParametersForMatrixMultiplyChkBox checkBox
        modelingParametersForMatrixMultiplyChkBox.setIndeterminate(false);
        modelingParametersForMatrixMultiplyChkBox.selectedProperty().addListener((ov, old_val, new_val) -> setVisibleModelingParameterBlockForMatrixMultiply(new_val ? true : false));

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

        inputsGridPane.add(mainParametersForMatrixMultiplyLbl, 1, 1);
        inputsGridPane.setColumnSpan(mainParametersForMatrixMultiplyLbl, 2);

        inputsGridPane.add(experimentNameForMatrixMultiplyLbl, 1, 2);
        inputsGridPane.add(experimentNameForMatrixMultiplyTextField, 2, 2);

        inputsGridPane.add(experimentDescriptionForMatrixMultiplyLbl, 1, 3);
        inputsGridPane.add(experimentDescriptionForMatrixMultiplyTextField, 2, 3);

        inputsGridPane.add(experimentConditionsForMatrixMultiplyLbl, 1, 4);
        inputsGridPane.setColumnSpan(experimentConditionsForMatrixMultiplyLbl, 2);

        inputsGridPane.add(minMatrixSizeLbl, 1, 5);
        inputsGridPane.add(minMatrixSizeTextField, 2, 5);

        inputsGridPane.add(maxMatrixSizeLbl, 1, 6);
        inputsGridPane.add(maxMatrixSizeTextField, 2, 6);

        inputsGridPane.add(matrixSizeIncrementLbl, 1, 7);
        inputsGridPane.add(matrixSizeIncrementTextField, 2, 7);

        inputsGridPane.add(blockSizeLbl, 1, 8);
        inputsGridPane.add(blockSizeTextField, 2, 8);

        inputsGridPane.add(modelingParametersForMatrixMultiplyChkBox, 1, 9);
        inputsGridPane.setColumnSpan(modelingParametersForMatrixMultiplyChkBox, 2);

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

        // set modelingParameters block invisible for Matrix Multiply Experiment
        //setVisibleModelingParameterBlockForMatrixMultiply(false);

        // set modelingParameters block invisible for NBody Experiment
        //setVisibleModelingParameterBlockForNBody(false);
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
            if (runningExperiment == Experiment.MATRIXMULTIPLY) {
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
            }

            if (runningExperiment == Experiment.NBODY) {

                int minN = Integer.parseInt(minNTextField.getText());
                int maxN = Integer.parseInt(maxNTextField.getText());
                int minTPB = Integer.parseInt(minTPBTextField.getText());
                int maxTPB = Integer.parseInt(maxTPBTextField.getText());

                int gpuCoreRating = Integer.parseInt(gpuCoreRatingTextField.getText());
                int limitationDivider = Integer.parseInt(limitationsDividerTextField.getText());
                double smallTPBPenaltyWeight = Double.parseDouble(smallTPBPenaltyWeightTextField.getText());
                double largeTPBPenaltyWeight = Double.parseDouble(largeTPBPenaltyWeightTextField.getText());
                double multiplicativeLengthScaleFactor = Double.parseDouble(multiplicativeLengthScaleFactorTextField.getText());
                double additiveLengthScaleFactor = Double.parseDouble(additiveLengthScaleFactorTextField.getText());

                NBodyExperiment nBodyExperiment = new NBodyExperiment(minN, maxN, minTPB, maxTPB, gpuCoreRating,
                        limitationDivider, smallTPBPenaltyWeight, largeTPBPenaltyWeight, multiplicativeLengthScaleFactor,
                        additiveLengthScaleFactor);

                try {
                    nBodyExperiment.serializeSimulationConfigs(nBodyExperiment.createInputs());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

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

    public static void setVisibleModelingParameterBlockForMatrixMultiply(boolean isVisible) {
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

    public static void setVisibleModelingParameterBlockForNBody(boolean isVisible) {

        gpuCoreRatingLbl.setVisible(isVisible);
        gpuCoreRatingTextField.setVisible(isVisible);

        limitationsDividerLbl.setVisible(isVisible);
        limitationsDividerTextField.setVisible(isVisible);

        smallTPBPenaltyWeightLbl.setVisible(isVisible);
        smallTPBPenaltyWeightTextField.setVisible(isVisible);

        largeTPBPenaltyWeightLbl.setVisible(isVisible);
        largeTPBPenaltyWeightTextField.setVisible(isVisible);

        multiplicativeLengthScaleFactorLbl.setVisible(isVisible);
        multiplicativeLengthScaleFactorTextField.setVisible(isVisible);

        additiveLengthScaleFactorLbl.setVisible(isVisible);
        additiveLengthScaleFactorTextField.setVisible(isVisible);
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
