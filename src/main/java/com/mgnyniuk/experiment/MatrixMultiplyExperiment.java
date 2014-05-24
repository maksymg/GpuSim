package com.mgnyniuk.experiment;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimGridletConfig;
import com.gpusim2.config.GridSimMachineConfig;
import com.gpusim2.config.GridSimResourceConfig;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private List<Integer> blockSizeList;
    private List<Integer> matrixSizeList;

    public MatrixMultiplyExperiment() {
        this.minMatrixSize = 16;
        this.maxMatrixSize = 4096;
        this.matrixSizeIncrement = 16;
        this.blockSize = 16;

        this.numberOfCpu = 8;
        this.rankOfCpu = 1000;
        this.numberOfGpu = 384;
        this.rankOfGpu = 10000;
        this.resourceCapacity = 10000000000.0;
        this.linkCapacity = 10000000000.0;
        this.loadOperationCost = 0.00018;
        this.saveOperationCost = 0.000936;

        this.blockSizeList = new ArrayList<>();
        this.matrixSizeList = new ArrayList<>();

        initBlockAndMatrixSizeLists(maxMatrixSize, matrixSizeIncrement, blockSize);
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

        this.blockSizeList = new ArrayList<>();
        this.matrixSizeList = new ArrayList<>();

        initBlockAndMatrixSizeLists(maxMatrixSize, matrixSizeIncrement, blockSize);
    }

    private void initBlockAndMatrixSizeLists(int maxMatrixSize, int matrixSizeIncrement, int blockSize) {
        for (int i=1; i <= maxMatrixSize / matrixSizeIncrement; i++) {
            blockSizeList.add(blockSize);
            matrixSizeList.add(matrixSizeIncrement * i);

        }
    }

    private GridSimConfig createSimulationConfig(int blockSize, int matrixSize) {

        GridSimResourceConfig gridSimResourceConfig = new GridSimResourceConfig();
        gridSimResourceConfig.setArch("gpusim.MatrixMultiply-ExperimentPlugin.Arch");
        gridSimResourceConfig.setOs("gpusim.MatrixMultiply-ExperimentPlugin.OS");
        gridSimResourceConfig.setCostPerSec(1.0);
        gridSimResourceConfig.setTimeZone(0);
        gridSimResourceConfig.setAllocPolicy(0);
        gridSimResourceConfig.setBaudRate(resourceCapacity);
        gridSimResourceConfig.setCount(1);
        gridSimResourceConfig.setMachines(new LinkedList<>());

        // First Machine
        GridSimMachineConfig gridSimMachineConfig1 = new GridSimMachineConfig();
        gridSimMachineConfig1.setPeCount(numberOfGpu);
        gridSimMachineConfig1.setPeRating(rankOfGpu);
        gridSimMachineConfig1.setCount(1);

        // Second Machine
        GridSimMachineConfig gridSimMachineConfig2 = new GridSimMachineConfig();
        gridSimMachineConfig2.setPeCount(numberOfCpu);
        gridSimMachineConfig2.setPeRating(rankOfCpu);
        gridSimMachineConfig2.setCount(1);

        gridSimResourceConfig.getMachines().add(gridSimMachineConfig1);
        gridSimResourceConfig.getMachines().add(gridSimMachineConfig2);

        // Create gridlet config
        GridSimGridletConfig gridSimGridletConfig = createGridletConfig(blockSize, matrixSize, saveOperationCost, loadOperationCost);

        GridSimConfig gridSimConfig = new GridSimConfig();
        gridSimConfig.setVersion(1);
        gridSimConfig.setLinkBaudRate(linkCapacity);
        gridSimConfig.setResources(new LinkedList<>());
        gridSimConfig.setGridlets(new LinkedList<>());

        gridSimConfig.getResources().add(gridSimResourceConfig);
        gridSimConfig.getGridlets().add(gridSimGridletConfig);

        return gridSimConfig;
    }

    private GridSimGridletConfig createGridletConfig(int blockSize, int matrixSize, double saveOperationCost, double loadOperationCost) {
        GridSimGridletConfig gridSimGridletConfig = new GridSimGridletConfig();
        double length = blockSize * Math.pow(matrixSize, 2) * saveOperationCost +
                2 * Math.pow(matrixSize, 3) * loadOperationCost;
        long inputSize = 3 * blockSize;
        long outputSize = blockSize;
        int count = matrixSize / blockSize;

        gridSimGridletConfig.setLength(length);
        gridSimGridletConfig.setInputSize(inputSize);
        gridSimGridletConfig.setOutputSize(outputSize);
        gridSimGridletConfig.setCount(count);

        return gridSimGridletConfig;
    }

    public void serializeSimulationConfigs(List<Integer> matrixSizeList, List<Integer> blockSizeList) throws FileNotFoundException {
        for (int i = 0; i < matrixSizeList.size(); i++) {
            GridSimConfig gridSimConfig = createSimulationConfig(blockSizeList.get(i), matrixSizeList.get(i));

            FileOutputStream out = new FileOutputStream("config" + i + ".xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(gridSimConfig);
            xmlEncoder.flush();
            xmlEncoder.close();
        }
    }

    public void populateConfigMap(List<Integer> matrixSizeList, List<Integer> blockSizeList, Map<Integer, GridSimConfig> configMap) throws FileNotFoundException {
        for (int i = 0; i < matrixSizeList.size(); i++) {
            GridSimConfig gridSimConfig = createSimulationConfig(blockSizeList.get(i), matrixSizeList.get(i));
            configMap.put(i, gridSimConfig);
        }
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
