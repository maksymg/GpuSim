package com.mgnyniuk.core;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimGridletConfig;
import com.gpusim2.config.GridSimMachineConfig;
import com.gpusim2.config.GridSimResourceConfig;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maksym on 3/23/14.
 */
public class ConfigGenerator {

    static List<Integer> blockSizeList = new ArrayList<Integer>();
    static List<Integer> matrixSizeList = new ArrayList<Integer>();

    static GridSimResourceConfig gridSimResourceConfig;

    private static void init(int minMatrixSize, int maxMatrixSize, int matrixSizeIncrement, int blockSize, int numberOfCpu,
                             int rankOfCpu, int numbreOfGpu, int rankOfGpu, int resourceCapacity) {
        for (int i = 1; i <= maxMatrixSize / matrixSizeIncrement; i++) {
            matrixSizeList.add(matrixSizeIncrement * i);
            blockSizeList.add(blockSize);
        }

        gridSimResourceConfig = new GridSimResourceConfig();
        gridSimResourceConfig.setArch("gpusim.MatrixMultiply-ExperimentPlugin.Arch");
        gridSimResourceConfig.setOs("gpusim.MatrixMultiply-ExperimentPlugin.OS");
        gridSimResourceConfig.setCostPerSec(1);
        gridSimResourceConfig.setTimeZone(0);
        gridSimResourceConfig.setAllocPolicy(0);
        gridSimResourceConfig.setBaudRate(resourceCapacity);
        gridSimResourceConfig.setCount(1);
        gridSimResourceConfig.setMachines(new LinkedList<GridSimMachineConfig>());

        // First Machine
        GridSimMachineConfig gridSimMachineConfig1 = new GridSimMachineConfig();
        gridSimMachineConfig1.setPeCount(numbreOfGpu);
        gridSimMachineConfig1.setPeRating(rankOfGpu);
        gridSimMachineConfig1.setCount(1);

        // Second Machine
        GridSimMachineConfig gridSimMachineConfig2 = new GridSimMachineConfig();
        gridSimMachineConfig2.setPeCount(numberOfCpu);
        gridSimMachineConfig2.setPeRating(rankOfCpu);
        gridSimMachineConfig2.setCount(1);

        gridSimResourceConfig.getMachines().add(gridSimMachineConfig1);
        gridSimResourceConfig.getMachines().add(gridSimMachineConfig2);
    }

    public static void generateMatrixMutiplyConfigs(int minMatrixSize, int maxMatrixSize, int matrixSizeIncrement, int blockSize, int numberOfCpu,
                                                    int rankOfCpu, int numberOfGpu, int rankOfGpu, int resourceCapacity, int linkCapacity, int loadOperationCost, int saveOperationCost) throws Exception {
        init(minMatrixSize, maxMatrixSize, matrixSizeIncrement, blockSize, numberOfCpu, rankOfCpu, numberOfGpu, rankOfGpu, resourceCapacity);

        GridSimConfig gridSimConfig = new GridSimConfig();
        gridSimConfig.setVersion(1);
        gridSimConfig.setLinkBaudRate(linkCapacity);
        gridSimConfig.setResources(new LinkedList<GridSimResourceConfig>());
        gridSimConfig.getResources().add(gridSimResourceConfig);
        gridSimConfig.setGridlets(new LinkedList<GridSimGridletConfig>());

        for (int i = 0; i < matrixSizeList.size(); i++) {

            GridSimGridletConfig gridSimGridletConfig = new GridSimGridletConfig();
            double length = blockSizeList.get(i) * Math.pow(matrixSizeList.get(i), 2) * saveOperationCost +
                    2 * Math.pow(matrixSizeList.get(i), 3) * loadOperationCost;
            long inputSize = 3 * blockSizeList.get(i);
            long outputSize = blockSizeList.get(i);
            int count = matrixSizeList.get(i) / blockSizeList.get(i);

            gridSimGridletConfig.setLength(length);
            gridSimGridletConfig.setInputSize(inputSize);
            gridSimGridletConfig.setOutputSize(outputSize);
            gridSimGridletConfig.setCount(count);

            gridSimConfig.getGridlets().add(gridSimGridletConfig);

            FileOutputStream out = new FileOutputStream("config" + i + ".xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(gridSimConfig);
            xmlEncoder.flush();
            xmlEncoder.close();

            gridSimConfig.getGridlets().remove();
        }
    }
}
