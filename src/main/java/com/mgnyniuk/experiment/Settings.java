package com.mgnyniuk.experiment;

/**
 * Created by maksym on 5/12/14.
 */
public class Settings {

    private Integer quantityOfParallelSimulation;

    public Settings() {
        this.quantityOfParallelSimulation = 1;
    }

    public Settings(Integer quantityOfParallelSimulation) {
        this.quantityOfParallelSimulation = quantityOfParallelSimulation;
    }

    public Integer getQuantityOfParallelSimulation() {
        return quantityOfParallelSimulation;
    }

    public void setQuantityOfParallelSimulation(Integer quantityOfParallelSimulation) {
        this.quantityOfParallelSimulation = quantityOfParallelSimulation;
    }
}
