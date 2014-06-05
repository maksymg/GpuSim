package com.mgnyniuk.experiment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maksym on 6/4/14.
 */
public class MatrixMultiplyExperimentCalibration implements Serializable {

    private List<Integer> matrixSizeList;
    private List<Integer> blockSizeList;
    private List<Double> simulationTimeList;

    public MatrixMultiplyExperimentCalibration() {
    }

    public MatrixMultiplyExperimentCalibration(List<Integer> matrixSizeList, List<Integer> blockSizeList,
                                               List<Double> simulationTimeList) {
        this.matrixSizeList = matrixSizeList;
        this.blockSizeList = blockSizeList;
        this.simulationTimeList = simulationTimeList;
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
