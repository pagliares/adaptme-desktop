package model.spem.config;

import java.util.List;

import model.spem.derived.BestFitDistribution;
import model.spem.util.FinishType;
import model.spem.util.PredecessorType;

public class TaskConfig {

    private String name;
    private double durationMean;
    private double durationStdDev;
    private BestFitDistribution bestFitDistribution;
    private List<String> workProductDependencies;
    private FinishType finishType;
    private PredecessorType predecessorType;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

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

    public List<String> getWorkProductDependencies() {
	return workProductDependencies;
    }

    public void setWorkProductDependencies(List<String> workProductDependencies) {
	this.workProductDependencies = workProductDependencies;
    }

    public FinishType getFinishType() {
	return finishType;
    }

    public void setFinishType(FinishType finishType) {
	this.finishType = finishType;
    }

    public PredecessorType getPredecessorType() {
	return predecessorType;
    }

    public void setPredecessorType(PredecessorType predecessorType) {
	this.predecessorType = predecessorType;
    }

}
