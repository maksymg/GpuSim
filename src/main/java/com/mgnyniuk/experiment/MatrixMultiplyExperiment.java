package com.mgnyniuk.experiment;

/**
 * Created by maksym on 4/28/14.
 */
public class MatrixMultiplyExperiment {

    private Integer minMatrixSize;
    private Integer maxMatrixSize;
    private Integer matrixSizeIncrement;
    private Integer blockSize;

    private Integer numberOfCpu;
    private Integer rankOfCpu;
    private Integer numberOfGpu;
    private Integer rankOfGpu;
    private Double resourceCapacity;
    private Double linkCapacity;
    private Double loadOperationCost;
    private Double saveOperationCost;

    public MatrixMultiplyExperiment() {
        minMatrixSize = 16;
        maxMatrixSize = 4096;
        matrixSizeIncrement = 16;
        blockSize = 16;

        numberOfCpu = 8;
        rankOfCpu = 1000;
        numberOfGpu = 384;
        rankOfGpu = 10000;
        resourceCapacity = 10000000000.0;
        linkCapacity = 10000000000.0;
        loadOperationCost = 0.00018;
        saveOperationCost = 0.000936;
    }

    public MatrixMultiplyExperiment(Integer minMatrixSize, Integer maxMatrixSize, Integer matrixSizeIncrement, Integer blockSize,
                                    Integer numberOfCpu, Integer rankOfCpu, Integer numberOfGpu, Integer rankOfGpu,
                                    Double resourceCapacity, Double linkCapacity, Double loadOperationCost, Double saveOperationCost) {
        this.minMatrixSize = minMatrixSize;
        this.maxMatrixSize = maxMatrixSize;
        this.matrixSizeIncrement = matrixSizeIncrement;
        this.blockSize = blockSize;
        this.numberOfCpu = numberOfCpu;
        this.rankOfCpu = rankOfCpu;
        this.numberOfGpu = numberOfGpu;
        this.rankOfGpu = rankOfGpu;
        this.resourceCapacity = resourceCapacity;
        this.linkCapacity = linkCapacity;
        this.loadOperationCost = loadOperationCost;
        this.saveOperationCost = saveOperationCost;
    }

    public Integer getMinMatrixSize() {
        return minMatrixSize;
    }

    public void setMinMatrixSize(Integer minMatrixSize) {
        this.minMatrixSize = minMatrixSize;
    }

    public Integer getMaxMatrixSize() {
        return maxMatrixSize;
    }

    public void setMaxMatrixSize(Integer maxMatrixSize) {
        this.maxMatrixSize = maxMatrixSize;
    }

    public Integer getMatrixSizeIncrement() {
        return matrixSizeIncrement;
    }

    public void setMatrixSizeIncrement(Integer matrixSizeIncrement) {
        this.matrixSizeIncrement = matrixSizeIncrement;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getNumberOfCpu() {
        return numberOfCpu;
    }

    public void setNumberOfCpu(Integer numberOfCpu) {
        this.numberOfCpu = numberOfCpu;
    }

    public Integer getRankOfCpu() {
        return rankOfCpu;
    }

    public void setRankOfCpu(Integer rankOfCpu) {
        this.rankOfCpu = rankOfCpu;
    }

    public Integer getNumberOfGpu() {
        return numberOfGpu;
    }

    public void setNumberOfGpu(Integer numberOfGpu) {
        this.numberOfGpu = numberOfGpu;
    }

    public Integer getRankOfGpu() {
        return rankOfGpu;
    }

    public void setRankOfGpu(Integer rankOfGpu) {
        this.rankOfGpu = rankOfGpu;
    }

    public Double getResourceCapacity() {
        return resourceCapacity;
    }

    public void setResourceCapacity(Double resourceCapacity) {
        this.resourceCapacity = resourceCapacity;
    }

    public Double getLinkCapacity() {
        return linkCapacity;
    }

    public void setLinkCapacity(Double linkCapacity) {
        this.linkCapacity = linkCapacity;
    }

    public Double getLoadOperationCost() {
        return loadOperationCost;
    }

    public void setLoadOperationCost(Double loadOperationCost) {
        this.loadOperationCost = loadOperationCost;
    }

    public Double getSaveOperationCost() {
        return saveOperationCost;
    }

    public void setSaveOperationCost(Double saveOperationCost) {
        this.saveOperationCost = saveOperationCost;
    }
}
