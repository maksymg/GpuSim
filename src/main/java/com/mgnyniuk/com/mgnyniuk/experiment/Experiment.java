package com.mgnyniuk.com.mgnyniuk.experiment;

/**
 * Created by maksym on 4/23/14.
 */
public enum Experiment {
    MATRIXMULTIPLY("Matrix Multiply Experiment"),
    NBODY("N Body Experiment");

    private String value;

    private Experiment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
