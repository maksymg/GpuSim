package com.mgnyniuk.experiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maksym on 6/4/14.
 */
public class MatrixMultiplyExperimentCalibration {

    private List<Integer> matrixSizeList;
    private List<Integer> blockSizeList;
    private List<Double> simulationTimeList;

    public MatrixMultiplyExperimentCalibration() {
        this.matrixSizeList = new ArrayList<>();
        this.blockSizeList = new ArrayList<>();
        this.simulationTimeList = new ArrayList<>();
    }

    public MatrixMultiplyExperimentCalibration(List<Integer> matrixSizeList, List<Integer> blockSizeList,
                                               List<Double> simulationTimeList) {
        this.matrixSizeList = matrixSizeList;
        this.blockSizeList = blockSizeList;
        this.simulationTimeList = simulationTimeList;
    }

    public void prepare() {
        for (int i = 16; i <= 3760; i +=16) {
            matrixSizeList.add(i);
            blockSizeList.add(16);
        }

    }
    public List<Integer> getMatrixSizeList() {
        return matrixSizeList;
    }

    public void setMatrixSizeList(List<Integer> matrixSizeList) {
        this.matrixSizeList = matrixSizeList;
    }

    public List<Integer> getBlockSizeList() {
        return blockSizeList;
    }

    public void setBlockSizeList(List<Integer> blockSizeList) {
        this.blockSizeList = blockSizeList;
    }

    public List<Double> getSimulationTimeList() {
        return simulationTimeList;
    }

    public void setSimulationTimeList(List<Double> simulationTimeList) {
        this.simulationTimeList = simulationTimeList;
    }
}
