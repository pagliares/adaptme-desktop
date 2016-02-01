package model.spem.config;

import model.spem.derived.BestFitDistribution;

public class ContainerConfig {

    private String name;
    private double durationMean;
    private double durationStdDev;
    private String finishStatus = "";
    private BestFitDistribution bestFitDistribution;

    public double getDurationMean() {
	return durationMean;
    }

    public void setDurationMean(double durationMean) {
	this.durationMean = durationMean;
    }

    public double getDurationStdDev() {
	return durationStdDev;
    }

    public void setDurationStdDev(double durationStdDev) {
	this.durationStdDev = durationStdDev;
    }

    public BestFitDistribution getBestFitDistribution() {
	return bestFitDistribution;
    }

    public void setBestFitDistribution(BestFitDistribution bestFitDistribution) {
	this.bestFitDistribution = bestFitDistribution;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getFinishStatus() {
	return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
	this.finishStatus = finishStatus;
    }

}
