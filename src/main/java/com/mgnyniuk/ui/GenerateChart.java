package com.mgnyniuk.ui;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimGridletConfig;
import com.gpusim2.config.GridSimOutput;
import com.gpusim2.config.GridSimResourceConfig;
import com.mgnyniuk.core.ConfigurationUtil;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maksym on 5/11/14.
 */
public class GenerateChart {

    public static LineChart<Number, Number> getResultChartForMatrixMultiplyExperiment(int outputStartIndex,
                                                                                      int outputEndIndex,
                                                                                      List<Integer> matrixSizeList) throws FileNotFoundException {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> ac = new LineChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Matrix size");
        yAxis.setLabel("Time");

        // output list
        List<GridSimOutput> outputList = ConfigurationUtil.loadOutputs(outputStartIndex, outputEndIndex);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Time/Matrix Size");

        for (int i = 0; i < matrixSizeList.size(); i++) {
            series.getData().add(new XYChart.Data<Number, Number>(matrixSizeList.get(i), outputList.get(i).getTotalSimulationTime()));
        }

        ac.getData().add(series);
        ac.setCreateSymbols(false);

        return ac;
    }

    public static LineChart<Number, Number> getResultChartForMatrixMultiplyExperimentAndCalibrationExperiment(int outputStartIndex,
                                                                                                              int outputEndIndex,
                                                                                                              List<Integer> mmExperimentMatrixSizeList, List<Integer> mmCalibrationExperimentMatrixSizeList,
                                                                                                              List<Double> mmCalibrationExperimentSimulationTimeList) throws FileNotFoundException {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> ac = new LineChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Matrix size");
        yAxis.setLabel("Time");

        // output list
        List<GridSimOutput> outputList = ConfigurationUtil.loadOutputs(outputStartIndex, outputEndIndex);

        LineChart.Series<Number, Number> modelSeries = new LineChart.Series<>();
        modelSeries.setName("Model");


        LineChart.Series<Number, Number> realParallelSystemSeries = new LineChart.Series<>();
        realParallelSystemSeries.setName("Real Parallel System");

        for (int i = 0; i < mmExperimentMatrixSizeList.size(); i++) {
            modelSeries.getData().add(new LineChart.Data<>(mmExperimentMatrixSizeList.get(i), outputList.get(i).getTotalSimulationTime()));
        }

        for (int i = 0; i < mmCalibrationExperimentMatrixSizeList.size(); i++) {
            realParallelSystemSeries.getData().add(new LineChart.Data<>(mmCalibrationExperimentMatrixSizeList.get(i), mmCalibrationExperimentSimulationTimeList.get(i)));
        }

        ac.getData().addAll(modelSeries, realParallelSystemSeries);
        ac.setCreateSymbols(false);

        return ac;
    }

    public static LineChart<Number, Number> getRelativeErrorChartForMatrixMultiplyExperiment(int outputStartIndex,
                                                                                      int outputEndIndex, List<Integer> matrixSizeList, List<Double> mmCalibrationExperimentSimulationTimeList) throws FileNotFoundException {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> ac = new LineChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Matrix size");
        yAxis.setLabel("Relative Error [%]");

        // output list
        List<GridSimOutput> outputList = ConfigurationUtil.loadOutputs(outputStartIndex, outputEndIndex);

        LineChart.Series<Number, Number> relativeErrorSeries = new LineChart.Series<>();
        relativeErrorSeries.setName("Relative Error");


        for (int i = 0; i < mmCalibrationExperimentSimulationTimeList.size(); i++) {
            double relativeError = ((Math.abs(mmCalibrationExperimentSimulationTimeList.get(i) - outputList.get(i).getTotalSimulationTime()) /mmCalibrationExperimentSimulationTimeList.get(i))/mmCalibrationExperimentSimulationTimeList.size()) * 100;
            relativeErrorSeries.getData().add(new LineChart.Data<>(matrixSizeList.get(i), relativeError));
        }

        ac.getData().addAll(relativeErrorSeries);
        ac.setCreateSymbols(false);

        return ac;
    }

    public static AreaChart<Number, Number> getResultChartForMatrixMultiplyExperiment(Map<Integer, GridSimOutput> outputMap, List<Integer> matrixSizeList) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Matrix size");
        yAxis.setLabel("Time");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Time/Matrix Size");

        for (int i = 0; i < matrixSizeList.size() - 1; i++) {
            series.getData().add(new XYChart.Data<Number, Number>(matrixSizeList.get(i), outputMap.get(i).getTotalSimulationTime()));
        }

        ac.getData().add(series);

        return ac;
    }

    public static AreaChart<Number, Number> getResultChartForNBodyExperimentTPBToTime(Map<Integer, GridSimConfig> configMap,
                                                                                      Map<Integer, GridSimOutput> outputMap, int N) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("Threads Per Block");
        yAxis.setLabel("Time[ms]");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Time[ms]/Threads Per Block");

        List<Integer> configNumberWithFoundNParameterList = new ArrayList<>();
        List<Integer> TPBForFoundNList = new ArrayList<>();
        List<Double> timeList = new ArrayList<>();

        for (int i = 0; i < configMap.size(); i++) {

            // GridSimGridletConfig and GridSimGridletResource are in a single copy
            GridSimGridletConfig gridSimGridletConfig = configMap.get(i).getGridlets().get(0);
            GridSimResourceConfig gridSimResourceConfig = configMap.get(i).getResources().get(0);

            if (gridSimGridletConfig.getCount() == N) {
                configNumberWithFoundNParameterList.add(i);
                TPBForFoundNList.add(gridSimResourceConfig.getCount());

            }
        }

        for (Integer index : configNumberWithFoundNParameterList) {
            timeList.add(outputMap.get(index).getTotalSimulationTime());
        }

        for (int i = 0; i < configNumberWithFoundNParameterList.size(); i++) {
            series.getData().add(new XYChart.Data<>(TPBForFoundNList.get(i), timeList.get(i)));
        }

        ac.getData().add(series);

        return ac;
    }

    public static AreaChart<Number, Number> getResultChartForNBodyExperimentNToTime(Map<Integer, GridSimConfig> configMap,
                                                                                    Map<Integer, GridSimOutput> outputMap, int TPB) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> ac = new AreaChart<Number, Number>(xAxis, yAxis);

        // setup chart
        ac.setTitle("Simulation Result");
        xAxis.setLabel("N");
        yAxis.setLabel("Time[ms]");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Time[ms]/N");

        List<Integer> configNumberWithFoundTPBParameterList = new ArrayList<>();
        List<Integer> NForFoundTPBList = new ArrayList<>();
        List<Double> timeList = new ArrayList<>();

        for (int i = 0; i < configMap.size(); i++) {

            // GridSimGridletConfig and GridSimGridletResource are in a single copy
            GridSimGridletConfig gridSimGridletConfig = configMap.get(i).getGridlets().get(0);
            GridSimResourceConfig gridSimResourceConfig = configMap.get(i).getResources().get(0);

            if (gridSimResourceConfig.getCount() == TPB) {
                configNumberWithFoundTPBParameterList.add(i);
                NForFoundTPBList.add(gridSimGridletConfig.getCount());
            }
        }

        for (Integer index : configNumberWithFoundTPBParameterList) {
            timeList.add(outputMap.get(index).getTotalSimulationTime());
        }

        for (int i = 0; i < configNumberWithFoundTPBParameterList.size(); i++) {
            series.getData().add(new XYChart.Data<>(NForFoundTPBList.get(i), timeList.get(i)));
        }

        ac.getData().add(series);

        return ac;
    }
}
