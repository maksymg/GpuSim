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

        simulationTimeList.add(0.074112);
        simulationTimeList.add(0.072224);
        simulationTimeList.add(0.078528);
        simulationTimeList.add(0.090944);
        simulationTimeList.add(0.108768);
        simulationTimeList.add(0.130656);
        simulationTimeList.add(0.152224);
        simulationTimeList.add(0.198592);
        simulationTimeList.add(0.255616);
        simulationTimeList.add(0.318208);
        simulationTimeList.add(0.391072);
        simulationTimeList.add(0.470688);
        simulationTimeList.add(0.575552);
        simulationTimeList.add(0.680288);
        simulationTimeList.add(0.81984);
        simulationTimeList.add(0.9608);
        simulationTimeList.add(1.12835);
        simulationTimeList.add(1.23984);
        simulationTimeList.add(1.42771);
        simulationTimeList.add(1.61379);
        simulationTimeList.add(1.78941);
        simulationTimeList.add(2.02234);
        simulationTimeList.add(2.27462);
        simulationTimeList.add(2.53459);
        simulationTimeList.add(2.84826);
        simulationTimeList.add(3.16883);
        simulationTimeList.add(3.52534);
        simulationTimeList.add(3.88509);
        simulationTimeList.add(4.31974);
        simulationTimeList.add(4.7345);
        simulationTimeList.add(5.21331);
        simulationTimeList.add(5.70186);
        simulationTimeList.add(6.23421);
        simulationTimeList.add(6.80016);
        simulationTimeList.add(7.36186);
        simulationTimeList.add(7.99942);
        simulationTimeList.add(8.6449);
        simulationTimeList.add(9.34406);
        simulationTimeList.add(10.0531);
        simulationTimeList.add(10.8215);
        simulationTimeList.add(11.6672);
        simulationTimeList.add(12.5465);
        simulationTimeList.add(13.397);
        simulationTimeList.add(14.3088);
        simulationTimeList.add(15.2752);
        simulationTimeList.add(16.2565);
        simulationTimeList.add(17.3057);
        simulationTimeList.add(18.4255);
        simulationTimeList.add(19.5256);
        simulationTimeList.add(20.7225);
        simulationTimeList.add(21.9708);
        simulationTimeList.add(23.2515);
        simulationTimeList.add(24.5641);
        simulationTimeList.add(25.9657);
        simulationTimeList.add(27.4076);
        simulationTimeList.add(28.8931);
        simulationTimeList.add(30.4159);
        simulationTimeList.add(32.028);
        simulationTimeList.add(33.7037);
        simulationTimeList.add(35.3572);
        simulationTimeList.add(37.1834);
        simulationTimeList.add(38.9598);
        simulationTimeList.add(40.7851);
        simulationTimeList.add(42.6977);
        simulationTimeList.add(44.7035);
        simulationTimeList.add(46.7584);
        simulationTimeList.add(48.9213);
        simulationTimeList.add(51.1481);
        simulationTimeList.add(53.3099);
        simulationTimeList.add(55.6982);
        simulationTimeList.add(57.9876);
        simulationTimeList.add(60.4981);
        simulationTimeList.add(62.9904);
        simulationTimeList.add(65.5003);
        simulationTimeList.add(68.2847);
        simulationTimeList.add(70.958);
        simulationTimeList.add(73.7035);
        simulationTimeList.add(76.5709);
        simulationTimeList.add(79.4501);
        simulationTimeList.add(82.4369);
        simulationTimeList.add(85.5756);
        simulationTimeList.add(88.6349);


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
