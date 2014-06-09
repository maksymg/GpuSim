package com.mgnyniuk.ui;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimOutput;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.Member;
import com.mgnyniuk.core.ConfigurationUtil;
import com.mgnyniuk.core.ExperimentRunner;
import com.mgnyniuk.core.distributed.SimulationRunner;
import com.mgnyniuk.experiment.*;
import com.mgnyniuk.util.FileManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by maksym on 3/23/14.
 */
public class MainWindow extends Application {

    final static Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public static HazelcastInstance hzInstance;

    private static final Image NEW_BTN = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_new.png"));
    private static final Image RUN_MODELING = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_runModeling.png"));
    private static final Image VIEW_PLOTS = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_viewPlots.png"));
    private static final Image SETTINGS = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_settings.png"));
    private static final Image OPEN = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_open.png"));
    private static final Image SAVE_AS = new Image(MainWindow.class.getResourceAsStream("/pictures/btn_saveAs.png"));

    private static GridPane inputsGridPane = new GridPane();
    private static Button runSimulationBtn;
    private static Button showResultsBtn;
    private static Button newExperimentBtn;
    private static Button settingsBtn;
    private static Button saveAsBtn;
    private static Button openExperimentBtn;
    private static Button openCalibrationFileBtn;

    // Hazelcast distributed maps
    public static Map<Integer, GridSimConfig> configMap;
    public static Map<Integer, GridSimOutput> outputMap;

    public static Experiment runningExperiment;
    public static boolean isCalibrationFileUsing;
    public static Settings currentSettings;
    private static MatrixMultiplyExperiment matrixMultiplyExperiment;
    public static NBodyExperiment nBodyExperiment;
    private static MatrixMultiplyExperimentCalibration matrixMultiplyExperimentCalibration;
    private static NBodyExperimentCalibration nBodyExperimentCalibration;

    // Inputs and labels for settings MatrixMultiplyExperiment
    private static Label mainParametersForMatrixMultiplyLbl;

    private static Label experimentNameForMatrixMultiplyLbl;
    private static TextField experimentNameForMatrixMultiplyTextField;

    private static Label experimentDescriptionForMatrixMultiplyLbl;
    private static TextField experimentDescriptionForMatrixMultiplyTextField;

    private static CheckBox calibrationFileChkBox;

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

        calibrationFileChkBox = new CheckBox("Файл Калібрування");
        calibrationFileChkBox.setIndeterminate(false);
        calibrationFileChkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            setOpenCalibrationFileBtnDisable(new_val ? false : true);
            isCalibrationFileUsing = new_val;
        });

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

        inputsGridPane.add(calibrationFileChkBox, 1, 4);
        inputsGridPane.add(openCalibrationFileBtn, 2, 4);

        inputsGridPane.add(experimentConditionsForNBodyLbl, 1, 5);
        inputsGridPane.setColumnSpan(experimentConditionsForNBodyLbl, 2);

        inputsGridPane.add(minNLbl, 1, 6);
        inputsGridPane.add(minNTextField, 2, 6);

        inputsGridPane.add(maxNLbl, 1, 7);
        inputsGridPane.add(maxNTextField, 2, 7);

        inputsGridPane.add(minTPBLbl, 1, 8);
        inputsGridPane.add(minTPBTextField, 2, 8);

        inputsGridPane.add(maxTPBLbl, 1, 9);
        inputsGridPane.add(maxTPBTextField, 2, 9);

        inputsGridPane.add(modelingParametersForNBodyChkBox, 1, 10);
        inputsGridPane.setColumnSpan(modelingParametersForNBodyChkBox, 2);

        inputsGridPane.add(gpuCoreRatingLbl, 1, 11);
        inputsGridPane.add(gpuCoreRatingTextField, 2, 11);

        inputsGridPane.add(limitationsDividerLbl, 1, 12);
        inputsGridPane.add(limitationsDividerTextField, 2, 12);

        inputsGridPane.add(smallTPBPenaltyWeightLbl, 1, 13);
        inputsGridPane.add(smallTPBPenaltyWeightTextField, 2, 13);

        inputsGridPane.add(largeTPBPenaltyWeightLbl, 1, 14);
        inputsGridPane.add(largeTPBPenaltyWeightTextField, 2, 14);

        inputsGridPane.add(multiplicativeLengthScaleFactorLbl, 1, 15);
        inputsGridPane.add(multiplicativeLengthScaleFactorTextField, 2, 15);

        inputsGridPane.add(additiveLengthScaleFactorLbl, 1, 16);
        inputsGridPane.add(additiveLengthScaleFactorTextField, 2, 16);
    }

    public static void prepareFieldsForMatrixMultiplyExperiment(MatrixMultiplyExperiment matrixMultiplyExperiment) {

        inputsGridPane.getChildren().clear();

        // Inputs and labels for settings
        mainParametersForMatrixMultiplyLbl = new Label("Основні параметри експеременту:");

        experimentNameForMatrixMultiplyLbl = new Label("Ім'я:");
        experimentNameForMatrixMultiplyTextField = new TextField("<No Experiment Name>");

        experimentDescriptionForMatrixMultiplyLbl = new Label("Опис:");
        experimentDescriptionForMatrixMultiplyTextField = new TextField("<No Experiment Description>");

        calibrationFileChkBox = new CheckBox("Файл Калібрування");
        calibrationFileChkBox.setIndeterminate(false);
        calibrationFileChkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            setOpenCalibrationFileBtnDisable(new_val ? false : true);
            isCalibrationFileUsing = new_val;
        });

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

        inputsGridPane.add(calibrationFileChkBox, 1, 4);
        inputsGridPane.add(openCalibrationFileBtn, 2, 4);

        inputsGridPane.add(experimentConditionsForMatrixMultiplyLbl, 1, 5);
        inputsGridPane.setColumnSpan(experimentConditionsForMatrixMultiplyLbl, 2);

        inputsGridPane.add(minMatrixSizeLbl, 1, 6);
        inputsGridPane.add(minMatrixSizeTextField, 2, 6);

        inputsGridPane.add(maxMatrixSizeLbl, 1, 7);
        inputsGridPane.add(maxMatrixSizeTextField, 2, 7);

        inputsGridPane.add(matrixSizeIncrementLbl, 1, 8);
        inputsGridPane.add(matrixSizeIncrementTextField, 2, 8);

        inputsGridPane.add(blockSizeLbl, 1, 9);
        inputsGridPane.add(blockSizeTextField, 2, 9);

        inputsGridPane.add(modelingParametersForMatrixMultiplyChkBox, 1, 10);
        inputsGridPane.setColumnSpan(modelingParametersForMatrixMultiplyChkBox, 2);

        inputsGridPane.add(numberOfCpuLbl, 1, 11);
        inputsGridPane.add(numberOfCpuTextField, 2, 11);

        inputsGridPane.add(rankOfCpuLbl, 1, 12);
        inputsGridPane.add(rankOfCpuTextField, 2, 12);

        inputsGridPane.add(numberOfGpuLbl, 1, 13);
        inputsGridPane.add(numberOfGpuTextField, 2, 13);

        inputsGridPane.add(rankOfGpuLbl, 1, 14);
        inputsGridPane.add(rankOfGpuTextField, 2, 14);

        inputsGridPane.add(resourceCapacityLbl, 1, 15);
        inputsGridPane.add(resourceCapacityTextField, 2, 15);

        inputsGridPane.add(linkCapacityLbl, 1, 16);
        inputsGridPane.add(linkCapacityTextField, 2, 16);

        inputsGridPane.add(loadOperationCostLbl, 1, 17);
        inputsGridPane.add(loadOperationCostTextField, 2, 17);

        inputsGridPane.add(saveOperationCostLbl, 1, 18);
        inputsGridPane.add(saveOperationCostTextField, 2, 18);
    }

    public static void setInputsGridPaneVisiblity(boolean isVisible) {
        inputsGridPane.setVisible(isVisible);
    }

    public static void setRunSimulationBtnDisable(boolean isDisabled) {
        runSimulationBtn.setDisable(isDisabled);
    }

    public static void setShowResultsBtnDisable(boolean isDisabled) {
        showResultsBtn.setDisable(isDisabled);
    }

    public static void setSaveAsBtnDisable(boolean isDisabled) {
        saveAsBtn.setDisable(isDisabled);
    }

    public static void setOpenExperimentBtnDisable(boolean isDisabled) {
        openExperimentBtn.setDisable(isDisabled);
    }

    public static void setOpenCalibrationFileBtnDisable(boolean isDisabled) {
        openCalibrationFileBtn.setDisable(isDisabled);
    }

    public static void runSimulation() {

        if (runningExperiment == Experiment.MATRIXMULTIPLY) {

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

            matrixMultiplyExperiment = new MatrixMultiplyExperiment(minMatrixSize,
                    maxMatrixSize, matrixSizeIncrement, blockSize, numberOfCpu, rankOfCpu, numberOfGpu,
                    rankOfGpu, resourceCapacity, linkCapacity, loadOperationCost, saveOperationCost);

            logger.info("Matrix Multiply Experiment is Created!");

            try {

                if (currentSettings.getIsDistributedSimulation()) {

                    List<Future<Boolean>> futuresList = new ArrayList<>();

                    configMap = hzInstance.getMap("configMap");
                    outputMap = hzInstance.getMap("outputMap");

                    IExecutorService executorService = hzInstance.getExecutorService("default");
                    Set<HazelcastInstance> hazelcastInstanceSet = Hazelcast.getAllHazelcastInstances();

                    // get hazelcast members
                    Set<Member> memberSet = new HashSet<>();
                    for (HazelcastInstance hazelcastInstance : hazelcastInstanceSet) {
                        memberSet = hazelcastInstance.getCluster().getMembers();
                    }

                    matrixMultiplyExperiment.populateConfigMap(matrixMultiplyExperiment.getMatrixSizeList(),
                            matrixMultiplyExperiment.getBlockSizeList(), configMap);

                    int quantityPerClusterNode = matrixMultiplyExperiment.getMatrixSizeList().size() / memberSet.size();
                    int startIndexPerNode = 0;

                    for (Member member : memberSet) {
                        System.out.println(startIndexPerNode);
                        Future<Boolean> future = executorService.submitToMember(new SimulationRunner(quantityPerClusterNode,
                                currentSettings.getQuantityOfParallelSimulation(), startIndexPerNode), member);
                        futuresList.add(future);
                        startIndexPerNode += quantityPerClusterNode;
                    }

                    for (Future<Boolean> future : futuresList) {
                        System.out.println(future.get());
                    }

                    System.out.println("OutputMap Size: " + outputMap.size());

                } else {
                    matrixMultiplyExperiment.serializeSimulationConfigs(matrixMultiplyExperiment.getMatrixSizeList(),
                            matrixMultiplyExperiment.getBlockSizeList());

                    ExperimentRunner matrixMultiplyExperimentRunner = new ExperimentRunner(matrixMultiplyExperiment.getMatrixSizeList().size(), currentSettings.getQuantityOfParallelSimulation(), null, 0);
                    matrixMultiplyExperimentRunner.runExperimnet();
                }

            } catch (FileNotFoundException e) {
                logger.error(e.getStackTrace().toString());
            } catch (IOException e) {
                logger.error(e.getStackTrace().toString());
            } catch (InterruptedException e) {
                logger.error(e.getStackTrace().toString());
            } catch (ExecutionException e) {
                logger.error(e.getStackTrace().toString());
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

            nBodyExperiment = new NBodyExperiment(minN, maxN, minTPB, maxTPB, gpuCoreRating,
                    limitationDivider, smallTPBPenaltyWeight, largeTPBPenaltyWeight, multiplicativeLengthScaleFactor,
                    additiveLengthScaleFactor);

            logger.info("NBody experiment is created!");

            try {

                if (currentSettings.getIsDistributedSimulation()) {
                    List<Future<Boolean>> futuresList = new ArrayList<>();

                    configMap = hzInstance.getMap("configMap");
                    outputMap = hzInstance.getMap("outputMap");

                    IExecutorService executorService = hzInstance.getExecutorService("default");
                    Set<HazelcastInstance> hazelcastInstanceSet = Hazelcast.getAllHazelcastInstances();

                    // get hazelcast members
                    Set<Member> memberSet = new HashSet<>();
                    for (HazelcastInstance hazelcastInstance : hazelcastInstanceSet) {
                        memberSet = hazelcastInstance.getCluster().getMembers();
                    }

                    List<NBodyExperiment.Input> inputs = nBodyExperiment.createInputs();
                    nBodyExperiment.populateConfigMap(inputs, configMap);

                    logger.info("Hazelcast configMap is populated with nBody configs.");

                    int quantityPerClusterNode = inputs.size() / memberSet.size();
                    int startIndexPerNode = 0;

                    for (Member member : memberSet) {
                        System.out.println(startIndexPerNode);
                        Future<Boolean> future = executorService.submitToMember(new SimulationRunner(quantityPerClusterNode,
                                currentSettings.getQuantityOfParallelSimulation(), startIndexPerNode), member);
                        futuresList.add(future);
                        startIndexPerNode += quantityPerClusterNode;
                    }

                    for (Future<Boolean> future : futuresList) {
                        System.out.println(future.get());
                    }

                    System.out.println("OutputMap Size: " + outputMap.size());

                } else {
                    List<NBodyExperiment.Input> inputs = nBodyExperiment.createInputs();
                    nBodyExperiment.serializeSimulationConfigs(inputs);
                    ExperimentRunner nBodyExperimentRunner = new ExperimentRunner(inputs.size(), currentSettings.getQuantityOfParallelSimulation(), null, 0);
                    nBodyExperimentRunner.runExperimnet();
                }
            } catch (FileNotFoundException e) {
                logger.error(e.getStackTrace().toString());
            } catch (IOException e) {
                logger.error(e.getStackTrace().toString());
            } catch (InterruptedException e) {
                logger.error(e.getStackTrace().toString());
            } catch (ExecutionException e) {
                logger.error(e.getStackTrace().toString());
            }

        }
    }

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root, 800, 800));

        // create default settings for experimenting
        currentSettings = new Settings();

        // GridPane for inputs
        inputsGridPane.setVisible(false);
        inputsGridPane.setHgap(5);
        inputsGridPane.setVgap(5);

        // run simulation Btn
        runSimulationBtn = new Button();
        ImageView runSimulationImage = new ImageView(RUN_MODELING);
        runSimulationBtn.setGraphic(runSimulationImage);
        runSimulationBtn.setDisable(true);

        // show results Btn
        showResultsBtn = new Button();
        ImageView showResultsBtnImg = new ImageView(VIEW_PLOTS);
        showResultsBtn.setGraphic(showResultsBtnImg);
        showResultsBtn.setDisable(true);

        // new experiment Btn
        ImageView newBtnImage = new ImageView(NEW_BTN);
        newExperimentBtn = new Button();
        newExperimentBtn.setGraphic(newBtnImage);

        // show results button
        settingsBtn = new Button();
        ImageView settingsBtnImg = new ImageView(SETTINGS);
        settingsBtn.setGraphic(settingsBtnImg);

        // saveAs experiment Button
        saveAsBtn = new Button();
        ImageView saveAsBtnImg = new ImageView(SAVE_AS);
        saveAsBtn.setGraphic(saveAsBtnImg);
        saveAsBtn.setDisable(true);

        // open experiment Button
        openExperimentBtn = new Button();
        ImageView openExperimentBtnImg = new ImageView(OPEN);
        openExperimentBtn.setGraphic(openExperimentBtnImg);
        openExperimentBtn.setDisable(true);

        // open calibration file Button
        openCalibrationFileBtn = new Button("Відкрити");
        openCalibrationFileBtn.setDisable(true);

        newExperimentBtn.setOnAction(actionEvent -> {
            try {
                // delete configs files from previous experiment
                FileManager.deleteFilesFromCurrentDir("config.*\\.xml");
                // delete outputs files from previous experiment
                FileManager.deleteFilesFromCurrentDir("output.*\\.xml");

                // Calibration file reset
                isCalibrationFileUsing = false;
                openCalibrationFileBtn.setDisable(true);

                // Disable show results btn
                showResultsBtn.setDisable(true);

                Stage choosingExperimentStage = new Stage();

                choosingExperimentStage.setScene(ChoosingExperimentWindow.getChoosingExperimentScene());
                choosingExperimentStage.setResizable(false);
                choosingExperimentStage.show();

                //inputsGridPane.setVisible(true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        saveAsBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GSE files (*.gse)", "*.gse");
            fileChooser.getExtensionFilters().add(extFilter);

            fileChooser.setTitle("Зберегти експеремент: ");
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                if (runningExperiment == Experiment.MATRIXMULTIPLY) {
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

                    matrixMultiplyExperiment = new MatrixMultiplyExperiment(minMatrixSize,
                            maxMatrixSize, matrixSizeIncrement, blockSize, numberOfCpu, rankOfCpu, numberOfGpu,
                            rankOfGpu, resourceCapacity, linkCapacity, loadOperationCost, saveOperationCost);

                    MatrixMultiplyExperiment.saveExperiment(file, matrixMultiplyExperiment);
                } else if (runningExperiment == Experiment.NBODY) {

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

                    nBodyExperiment = new NBodyExperiment(minN, maxN, minTPB, maxTPB, gpuCoreRating,
                            limitationDivider, smallTPBPenaltyWeight, largeTPBPenaltyWeight, multiplicativeLengthScaleFactor,
                            additiveLengthScaleFactor);

                    NBodyExperiment.saveExperiment(file, nBodyExperiment);

                }

            }

        });

        openExperimentBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GSE files (*.gse)", "*.gse");
            fileChooser.getExtensionFilters().addAll(extFilter);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                if (runningExperiment == Experiment.MATRIXMULTIPLY) {
                    matrixMultiplyExperiment = MatrixMultiplyExperiment.loadExperiment(file);
                    prepareFieldsForMatrixMultiplyExperiment(matrixMultiplyExperiment);
                    setInputsGridPaneVisiblity(true);
                    setVisibleModelingParameterBlockForMatrixMultiply(false);
                } else if (runningExperiment == Experiment.NBODY) {
                    nBodyExperiment = NBodyExperiment.loadExperiment(file);
                    prepareFieldsForNBodyExperiment(nBodyExperiment);
                    setInputsGridPaneVisiblity(true);
                    setVisibleModelingParameterBlockForNBody(false);
                }
            }
        });

        openCalibrationFileBtn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GSC files (*.gsc)", "*.gsc");
            fileChooser.getExtensionFilters().addAll(extFilter);

            // Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                if (runningExperiment == Experiment.MATRIXMULTIPLY) {
                    matrixMultiplyExperimentCalibration = MatrixMultiplyExperiment.loadExperimentCalibration(file);
                } else if (runningExperiment == Experiment.NBODY) {
                    nBodyExperimentCalibration = NBodyExperiment.loadExperimentCalibration(file);
                }
            }

        });

        runSimulationBtn.setOnAction(actionEvent -> {

            Stage simulationProgressStage = new Stage();

            simulationProgressStage.setScene(ProgressWindow.getProgressWindowScene());
            simulationProgressStage.setResizable(false);
            simulationProgressStage.show();
        });

        showResultsBtn.setOnAction(actionEvent -> {
            showResults();
        });

        settingsBtn.setOnAction(actionEvent -> {

            Stage settingsStage = new Stage();

            settingsStage.setScene(SettingsWindow.getSettingsWindowScene(currentSettings));
            settingsStage.setResizable(false);
            settingsStage.show();
        });

        // HBox with spacing 5
        HBox buttonsHBox = new HBox(5);
        buttonsHBox.getChildren().addAll(newExperimentBtn, openExperimentBtn, saveAsBtn, runSimulationBtn, showResultsBtn, settingsBtn);
        buttonsHBox.setAlignment(Pos.TOP_LEFT);

        GridPane masterGridPane = new GridPane();
        masterGridPane.add(buttonsHBox, 1, 1);


        masterGridPane.add(inputsGridPane, 1, 2);
        masterGridPane.setPadding(new Insets(5, 5, 5, 5));
        root.getChildren().add(masterGridPane);
    }

    private static LineChart<Number, Number> matrixMultiplyExperimentChart;

    public static void showResults() {
        Group resultsRoot = new Group();
        Stage resultsStage = new Stage();
        resultsStage.setScene(new Scene(resultsRoot));
        try {
            if (runningExperiment == Experiment.MATRIXMULTIPLY) {
                if (currentSettings.getIsDistributedSimulation()) {
                    resultsRoot.getChildren().add(GenerateChart.getResultChartForMatrixMultiplyExperiment(outputMap, matrixMultiplyExperiment.getMatrixSizeList()));

                } else {
                    if (isCalibrationFileUsing) {

                        GridPane gridPane = new GridPane();
                        // ChoiceBox for type of graphic
                        ChoiceBox cb = new ChoiceBox();
                        cb.getItems().addAll("Relative Error (Matrix Size)", "Time (Matrix Size)");
                        cb.getSelectionModel().selectFirst();

                        GridPane.setConstraints(cb, 0, 0);
                        GridPane.setMargin(cb, new Insets(10, 10, 10, 10));
                        GridPane.setHalignment(cb, HPos.LEFT);

                        if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().equals("Relative Error (Matrix Size)".toLowerCase())) {
                            matrixMultiplyExperimentChart = GenerateChart.getRelativeErrorChartForMatrixMultiplyExperiment(0, matrixMultiplyExperiment.getMatrixSizeList().size(), matrixMultiplyExperiment.getMatrixSizeList(),
                                    matrixMultiplyExperimentCalibration.getSimulationTimeList());
                        } else if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().equals("Time (Matrix Size)".toLowerCase())) {
                            matrixMultiplyExperimentChart = GenerateChart.getResultChartForMatrixMultiplyExperimentAndCalibrationExperiment(0, matrixMultiplyExperiment.getMatrixSizeList().size(), matrixMultiplyExperiment.getMatrixSizeList(),
                                    matrixMultiplyExperimentCalibration.getMatrixSizeList(), matrixMultiplyExperimentCalibration.getSimulationTimeList());
                        }

                        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                            @Override
                            public void changed(ObservableValue observableValue, Object o, Object new_value) {
                                gridPane.getChildren().clear();

                                if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().equals("Relative Error (Matrix Size)".toLowerCase())) {

                                    try {
                                        matrixMultiplyExperimentChart = GenerateChart.getRelativeErrorChartForMatrixMultiplyExperiment(0, matrixMultiplyExperiment.getMatrixSizeList().size(), matrixMultiplyExperiment.getMatrixSizeList(),
                                                matrixMultiplyExperimentCalibration.getSimulationTimeList());
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                } else if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().equals("Time (Matrix Size)".toLowerCase())) {

                                    try {
                                        matrixMultiplyExperimentChart = GenerateChart.getResultChartForMatrixMultiplyExperimentAndCalibrationExperiment(0, matrixMultiplyExperiment.getMatrixSizeList().size(), matrixMultiplyExperiment.getMatrixSizeList(),
                                                matrixMultiplyExperimentCalibration.getMatrixSizeList(), matrixMultiplyExperimentCalibration.getSimulationTimeList());
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                                GridPane.setConstraints(matrixMultiplyExperimentChart, 0, 1);
                                GridPane.setMargin(matrixMultiplyExperimentChart, new Insets(10, 10, 10, 10));
                                GridPane.setHalignment(matrixMultiplyExperimentChart, HPos.LEFT);

                                gridPane.getChildren().addAll(cb, matrixMultiplyExperimentChart);
                            }
                        });

                        GridPane.setConstraints(matrixMultiplyExperimentChart, 0, 1);
                        GridPane.setMargin(matrixMultiplyExperimentChart, new Insets(10, 10, 10, 10));
                        GridPane.setHalignment(matrixMultiplyExperimentChart, HPos.LEFT);

                        gridPane.getChildren().addAll(cb, matrixMultiplyExperimentChart);

                        resultsRoot.getChildren().add(gridPane);

                    } else {
                        resultsRoot.getChildren().add(GenerateChart.getResultChartForMatrixMultiplyExperiment(0, matrixMultiplyExperiment.getMatrixSizeList().size(), matrixMultiplyExperiment.getMatrixSizeList()));
                    }
                }
            } else if (runningExperiment == Experiment.NBODY) {

                Map<String, Integer> timeTPBGraphicMap = new TreeMap<>();
                Map<String, Integer> timeNGraphicMap = new TreeMap<>();

                // counter

               /* for (int i = nBodyExperiment.getMinN(); i <= nBodyExperiment.getMaxN(); i *= 2) {
                    timeNGraphicMap.put(i, "Time(TPB); N = " + i);
                }

                for (int i = nBodyExperiment.getMinTPB(); i <= nBodyExperiment.getMaxTPB(); i *= 2) {
                    timeTPBGraphicMap.put(i, "Time(N); TPB = " + i);
                }*/

                for (int i = nBodyExperiment.getMinN(); i <= nBodyExperiment.getMaxN(); i *= 2) {
                    timeNGraphicMap.put("Time(TPB); N = " + i, i);
                    if (isCalibrationFileUsing) {
                        timeNGraphicMap.put("RelativeError(TPB); N = " + i, i);
                    }
                }

                for (int i = nBodyExperiment.getMinTPB(); i <= nBodyExperiment.getMaxTPB(); i *= 2) {
                    timeTPBGraphicMap.put("Time(N); TPB = " + i, i);
                    if (isCalibrationFileUsing) {
                        timeTPBGraphicMap.put("RelativeError(N); TPB = " + i, i);
                    }
                }

                GridPane gridPane = new GridPane();

                Map<Integer, GridSimConfig> gridSimConfigMap;
                Map<Integer, GridSimOutput> gridSimOutputMap;

                gridSimConfigMap = ConfigurationUtil.deserializeConfigsToMap();
                gridSimOutputMap = ConfigurationUtil.deserializeOutputsToMap();


                // ChoiceBox for type of graphic
                ChoiceBox cb = new ChoiceBox();
                for (Map.Entry<String, Integer> entry : timeNGraphicMap.entrySet()) {
                    cb.getItems().add(entry.getKey());
                }

                for (Map.Entry<String, Integer> entry : timeTPBGraphicMap.entrySet()) {
                    cb.getItems().add(entry.getKey());
                }

                //cb.getItems().addAll("Dog", "Cat", "Horse");
                cb.getSelectionModel().selectFirst();

                GridPane.setConstraints(cb, 0, 0);
                GridPane.setMargin(cb, new Insets(10, 10, 10, 10));
                GridPane.setHalignment(cb, HPos.LEFT);

                // patterns for finding graphic
                String experimentPatternTPB = "Time(TPB);";
                String experimentPatternN = "Time(N);";
                String relativeErrorPatternTPB = "RelativeError(TPB);";
                String relativeErrorPatternN = "RelativeError(N);";

                if (currentSettings.getIsDistributedSimulation()) {


                } else {
                    if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().contains(experimentPatternTPB.toLowerCase())) {
                        if (isCalibrationFileUsing) {
                            nBodyChart = GenerateChart.getResultChartForNBodyExperimentAndCalibrationExperimentTPBToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinN() / nBodyExperiment.getLimitationDivider(),
                                    nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                        } else {
                            nBodyChart = GenerateChart.getResultChartForNBodyExperimentTPBToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinN() / nBodyExperiment.getLimitationDivider());
                        }
                    } else if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().contains(experimentPatternN.toLowerCase())) {

                        if (isCalibrationFileUsing) {
                            nBodyChart = GenerateChart.getResultChartForNBodyExperimentAndCalibrationExperimentNToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinTPB(),
                                    nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                        } else {
                            nBodyChart = GenerateChart.getResultChartForNBodyExperimentNToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinTPB());
                        }
                    } else if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().contains(relativeErrorPatternTPB.toLowerCase())) {
                        if (isCalibrationFileUsing)
                            nBodyChart = GenerateChart.getRelativeErrorChartForNBodyExperimentForTPBToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinN() / nBodyExperiment.getLimitationDivider(),
                                    nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                    } else if (((String) cb.getSelectionModel().getSelectedItem()).toLowerCase().contains(relativeErrorPatternN.toLowerCase())) {
                        if (isCalibrationFileUsing)
                            nBodyChart = GenerateChart.getRelativeErrorChartForNBodyExperimentForNToTime(gridSimConfigMap, gridSimOutputMap, nBodyExperiment.getMinN() / nBodyExperiment.getLimitationDivider(),
                                    nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                    }

                    cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observableValue, Object o, Object new_value) {
                            gridPane.getChildren().clear();
                            Integer key = null;


                            if (((String) new_value).toLowerCase().contains(experimentPatternTPB.toLowerCase())) {
                                for (Map.Entry entry : timeNGraphicMap.entrySet()) {
                                    if (new_value.equals(entry.getKey())) {
                                        key = (Integer) entry.getValue();
                                        break; //breaking because its one to one map
                                    }
                                }
                                if (isCalibrationFileUsing) {
                                    nBodyChart = GenerateChart.getResultChartForNBodyExperimentAndCalibrationExperimentTPBToTime(gridSimConfigMap, gridSimOutputMap, key / nBodyExperiment.getLimitationDivider(),
                                            nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                                } else {
                                    nBodyChart = GenerateChart.getResultChartForNBodyExperimentTPBToTime(gridSimConfigMap, gridSimOutputMap, key / nBodyExperiment.getLimitationDivider());
                                }
                            } else if (((String) new_value).toLowerCase().contains(experimentPatternN.toLowerCase())) {
                                for (Map.Entry entry : timeTPBGraphicMap.entrySet()) {
                                    if (new_value.equals(entry.getKey())) {
                                        key = (Integer) entry.getValue();
                                        break; //breaking because its one to one map
                                    }
                                }
                                if (isCalibrationFileUsing) {
                                    nBodyChart = GenerateChart.getResultChartForNBodyExperimentAndCalibrationExperimentNToTime(gridSimConfigMap, gridSimOutputMap, key,
                                            nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());

                                } else {
                                    nBodyChart = GenerateChart.getResultChartForNBodyExperimentNToTime(gridSimConfigMap, gridSimOutputMap, key);
                                }
                            } else if (((String) new_value).toLowerCase().contains(relativeErrorPatternN.toLowerCase())) {
                                for (Map.Entry entry : timeTPBGraphicMap.entrySet()) {
                                    if (new_value.equals(entry.getKey())) {
                                        key = (Integer) entry.getValue();
                                        break; //breaking because its one to one map
                                    }
                                }
                                if (isCalibrationFileUsing)
                                    nBodyChart = GenerateChart.getRelativeErrorChartForNBodyExperimentForNToTime(gridSimConfigMap, gridSimOutputMap, key,
                                            nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());
                            } else if (((String) new_value).toLowerCase().contains(relativeErrorPatternTPB.toLowerCase())) {
                                for (Map.Entry entry : timeNGraphicMap.entrySet()) {
                                    if (new_value.equals(entry.getKey())) {
                                        key = (Integer) entry.getValue();
                                        break; //breaking because its one to one map
                                    }
                                }
                                if (isCalibrationFileUsing)
                                    nBodyChart = GenerateChart.getRelativeErrorChartForNBodyExperimentForTPBToTime(gridSimConfigMap, gridSimOutputMap, key / nBodyExperiment.getLimitationDivider(),
                                            nBodyExperimentCalibration.getNList(), nBodyExperimentCalibration.getTpbList(), nBodyExperimentCalibration.getSimulationTimeList());

                            }


                            GridPane.setConstraints(nBodyChart, 0, 1);
                            GridPane.setMargin(nBodyChart, new Insets(10, 10, 10, 10));
                            GridPane.setHalignment(nBodyChart, HPos.LEFT);

                            gridPane.getChildren().addAll(cb, nBodyChart);
                        }
                    });

                    GridPane.setConstraints(nBodyChart, 0, 1);
                    GridPane.setMargin(nBodyChart, new Insets(10, 10, 10, 10));
                    GridPane.setHalignment(nBodyChart, HPos.LEFT);

                    gridPane.getChildren().addAll(cb, nBodyChart);

                    resultsRoot.getChildren().add(gridPane);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        resultsStage.show();
    }

    private static LineChart<Number, Number> nBodyChart;

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

    @Override
    public void stop() {
        hzInstance.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
