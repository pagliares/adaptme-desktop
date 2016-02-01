package model.spem.config;

import model.spem.derived.BestFitDistribution;

public class WorkProductConfig {

    private String name;
    private double sizeMean;
    private double sizeStdDev;
    private BestFitDistribution bestFitDistribution;

    public double getSizeMean() {
	return sizeMean;
    }

    public void setSizeMean(double sizeMean) {
	this.sizeMean = sizeMean;
    }

    public double getSizeStdDev() {
	return sizeStdDev;
    }

    public void setSizeStdDev(double sizeStdDev) {
	this.sizeStdDev = sizeStdDev;
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

}
