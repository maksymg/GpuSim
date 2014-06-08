package com.mgnyniuk.experiment;

import javafx.scene.control.Label;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maksym on 6/5/14.
 */
public class NBodyExperimentCalibration implements Serializable {

    private List<Integer> nList;
    private List<Integer> tpbList;
    private List<Double> simulationTimeList;

    public NBodyExperimentCalibration() { }

    public NBodyExperimentCalibration(List<Integer> nList, List<Integer> tpbList, List<Double> simulationTimeList) {
        this.nList = nList;
        this.tpbList = tpbList;
        this.simulationTimeList = simulationTimeList;
    }

    public List<Integer> getNList() {
        return nList;
    }

    public void setNList(List<Integer> nList) {
        this.nList = nList;
    }

    public List<Integer> getTpbList() {
        return tpbList;
    }

    public void setTpbList(List<Integer> tpbList) {
        this.tpbList = tpbList;
    }

    public List<Double> getSimulationTimeList() {
        return simulationTimeList;
    }

    public void setSimulationTimeList(List<Double> simulationTimeList) {
        this.simulationTimeList = simulationTimeList;
    }
}
