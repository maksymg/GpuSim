package com.mgnyniuk.experiment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maksym on 6/3/14.
 */
public class MatrixMultiplyExperimentPOJO implements Serializable {

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

    private List<Integer> blockSizeList;
    private List<Integer> matrixSizeList;

    public MatrixMultiplyExperimentPOJO() { }

    public MatrixMultiplyExperimentPOJO(Integer minMatrixSize, Integer maxMatrixSize, Integer matrixSizeIncrement, Integer blockSize,
                                        Integer numberOfCpu, Integer rankOfCpu, Integer numberOfGpu, Integer rankOfGpu,
                                        Double resourceCapacity, Double linkCapacity, Double loadOperationCost, Double saveOperationCost,
                                        List<Integer> blockSizeList, List<Integer> matrixSizeList) {
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

        this.blockSizeList = blockSizeList;
        this.matrixSizeList = matrixSizeList;
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

    public List<Integer> getBlockSizeList() {
        return blockSizeList;
    }

    public void setBlockSizeList(List<Integer> blockSizeList) {
        this.blockSizeList = blockSizeList;
    }

    public List<Integer> getMatrixSizeList() {
        return matrixSizeList;
    }

    public void setMatrixSizeList(List<Integer> matrixSizeList) {
        this.matrixSizeList = matrixSizeList;
    }
}
