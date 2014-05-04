package com.mgnyniuk.experiment;

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
}
