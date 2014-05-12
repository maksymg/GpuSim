package com.mgnyniuk.experiment;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimGridletConfig;
import com.gpusim2.config.GridSimMachineConfig;
import com.gpusim2.config.GridSimResourceConfig;
import com.mgnyniuk.util.Calc;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maksym on 5/4/14.
 */
public class NBodyExperiment {

    private Integer minN;
    private Integer maxN;
    private Integer minTPB;
    private Integer maxTPB;

    private Integer gpuCoreRating;
    private Integer limitationDivider;
    private Double smallTPBPenaltyWeight;
    private Double largeTPBPenaltyWeight;
    private Double multiplicativeLengthScaleFactor;
    private Double additiveLengthScaleFactor;

    public NBodyExperiment() {
        this.minN = 1024;
        this.maxN = 1048576;
        this.minTPB = 1;
        this.maxTPB = 1024;

        this.gpuCoreRating = 1000;
        this.limitationDivider = 128;
        this.smallTPBPenaltyWeight = 0.7;
        this.largeTPBPenaltyWeight = 0.05;
        this.multiplicativeLengthScaleFactor = 0.1;
        this.additiveLengthScaleFactor = 0.0;
    }

    public NBodyExperiment(Integer minN, Integer maxN, Integer minTPB, Integer maxTPB, Integer gpuCoreRating,
                           Integer limitationDivider, Double smallTPBPenaltyWeight, Double largeTPBPenaltyWeight,
                           Double multiplicativeLengthScaleFactor, Double additiveLengthScaleFactor) {
        this.minN = minN;
        this.maxN = maxN;
        this.minTPB = minTPB;
        this.maxTPB = maxTPB;

        this.gpuCoreRating = gpuCoreRating;
        this.limitationDivider = limitationDivider;
        this.smallTPBPenaltyWeight = smallTPBPenaltyWeight;
        this.largeTPBPenaltyWeight = largeTPBPenaltyWeight;
        this.multiplicativeLengthScaleFactor = multiplicativeLengthScaleFactor;
        this.additiveLengthScaleFactor = additiveLengthScaleFactor;
    }

    public List<Input> createInputs() {
        List<Input> inputs = new ArrayList<>();
        for (int currentN = minN; currentN <= maxN; currentN *= 2) {
            for (int currentTPB = minTPB; currentTPB <= maxTPB; currentTPB *= 2) {
                inputs.add(new Input(currentN, currentTPB));
            }
        }

        return inputs;
    }

    private GridSimConfig createSimulationConfig(Input input) {

        // Create machines config with only one machine with only one PE
        GridSimMachineConfig gridSimMachineConfig = new GridSimMachineConfig();
        gridSimMachineConfig.setPeCount(1);
        gridSimMachineConfig.setPeRating(gpuCoreRating);
        gridSimMachineConfig.setCount(1);

        // Create resources config with resources count equals to TPB
        GridSimResourceConfig gridSimResourceConfig = new GridSimResourceConfig();
        gridSimResourceConfig.setArch("gpusim.NBody-ExperimentPlugin.Arch");
        gridSimResourceConfig.setOs("gpusim.NBody-ExperimentPlugin.OS");
        gridSimResourceConfig.setBaudRate(1e+10);
        gridSimResourceConfig.setCostPerSec(1.0);
        gridSimResourceConfig.setMachines(new LinkedList<GridSimMachineConfig>());
        gridSimResourceConfig.setCount(input.getThreadsPerBlock());

        gridSimResourceConfig.getMachines().add(gridSimMachineConfig);

        // Create gridlet config
        GridSimGridletConfig gridSimGridletConfig = createGridletConfig(input.getN(), input.getThreadsPerBlock(), limitationDivider,
                smallTPBPenaltyWeight, largeTPBPenaltyWeight, multiplicativeLengthScaleFactor, additiveLengthScaleFactor);

        GridSimConfig gridSimConfig = new GridSimConfig();
        gridSimConfig.setLinkBaudRate(1e+10);
        gridSimConfig.setVersion(1);
        gridSimConfig.setGridlets(new LinkedList<>());
        gridSimConfig.setResources(new LinkedList<>());

        gridSimConfig.getGridlets().add(gridSimGridletConfig);
        gridSimConfig.getResources().add(gridSimResourceConfig);

        return gridSimConfig;

    }

    public void serializeSimulationConfigs(List<Input> inputs) throws FileNotFoundException {

        for(int i=0; i < inputs.size(); i++) {
            GridSimConfig gridSimConfig = createSimulationConfig(inputs.get(i));

            FileOutputStream out = new FileOutputStream("config" + i + ".xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(gridSimConfig);
            xmlEncoder.flush();
            xmlEncoder.close();
        }

    }

    private GridSimGridletConfig createGridletConfig(int n, int threadsPerBlock, double limitationsDivider, double smallTPBPenaltyWeight,
                                     double largeTPBPenaltyWeight, double multiplicativeLengthScaleFactor, double additiveLengthScaleFactor) {

        double smallTPBPenalty = (smallTPBPenaltyWeight * n * Calc.log(n, 2) * Calc.log(n, 2) * Calc.log(threadsPerBlock, 2)) / (threadsPerBlock);
        double largeTPBPenalty = threadsPerBlock * n * largeTPBPenaltyWeight;

        double length = (n * limitationsDivider + smallTPBPenalty + largeTPBPenalty) * multiplicativeLengthScaleFactor +
                additiveLengthScaleFactor;
        Double count = n / limitationsDivider;
        int inputSize = 1;
        int outputSize = 1;

        GridSimGridletConfig gridSimGridletConfig = new GridSimGridletConfig();
        gridSimGridletConfig.setLength(length);
        gridSimGridletConfig.setInputSize(inputSize);
        gridSimGridletConfig.setOutputSize(outputSize);
        gridSimGridletConfig.setCount(count.intValue());

        return gridSimGridletConfig;
    }

    public Integer getMinN() {
        return minN;
    }

    public void setMinN(Integer minN) {
        this.minN = minN;
    }

    public Integer getMaxN() {
        return maxN;
    }

    public void setMaxN(Integer maxN) {
        this.maxN = maxN;
    }

    public Integer getMinTPB() {
        return minTPB;
    }

    public void setMinTPB(Integer minTPB) {
        this.minTPB = minTPB;
    }

    public Integer getMaxTPB() {
        return maxTPB;
    }

    public void setMaxTPB(Integer maxTPB) {
        this.maxTPB = maxTPB;
    }

    public Integer getGpuCoreRating() {
        return gpuCoreRating;
    }

    public void setGpuCoreRating(Integer gpuCoreRating) {
        this.gpuCoreRating = gpuCoreRating;
    }

    public Integer getLimitationDivider() {
        return limitationDivider;
    }

    public void setLimitationDivider(Integer limitationDivider) {
        this.limitationDivider = limitationDivider;
    }

    public Double getSmallTPBPenaltyWeight() {
        return smallTPBPenaltyWeight;
    }

    public void setSmallTPBPenaltyWeight(Double smallTPBPenaltyWeight) {
        this.smallTPBPenaltyWeight = smallTPBPenaltyWeight;
    }

    public Double getLargeTPBPenaltyWeight() {
        return largeTPBPenaltyWeight;
    }

    public void setLargeTPBPenaltyWeight(Double largeTPBPenaltyWeight) {
        this.largeTPBPenaltyWeight = largeTPBPenaltyWeight;
    }

    public Double getMultiplicativeLengthScaleFactor() {
        return multiplicativeLengthScaleFactor;
    }

    public void setMultiplicativeLengthScaleFactor(Double multiplicativeLengthScaleFactor) {
        this.multiplicativeLengthScaleFactor = multiplicativeLengthScaleFactor;
    }

    public Double getAdditiveLengthScaleFactor() {
        return additiveLengthScaleFactor;
    }

    public void setAdditiveLengthScaleFactor(Double additiveLengthScaleFactor) {
        this.additiveLengthScaleFactor = additiveLengthScaleFactor;
    }

    public class Input {

        private Integer n;
        private Integer threadsPerBlock;

        public Input(Integer currentN, Integer currentTPB) {
            this.n = currentN;
            this.threadsPerBlock = currentTPB;
        }

        public Integer getN() {
            return n;
        }

        public void setN(Integer n) {
            this.n = n;
        }

        public Integer getThreadsPerBlock() {
            return threadsPerBlock;
        }

        public void setThreadsPerBlock(Integer threadPerBlock) {
            this.threadsPerBlock = threadPerBlock;
        }
    }
}
