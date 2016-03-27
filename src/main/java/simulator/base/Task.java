package simulator.base;

import java.util.ArrayList;
import java.util.List;

public class Task {
	private String name;
	private String bestFitProbabilityDistribution;
	private double mean;
	private double standardDeviation;
	private String measurementUnity;
	private double durationMean;
	private double durationStandardDeviation;
	private int minimumNumberOfDevelopersNeeded;
	
	private Role mainPerformer;
	private List<Role> additionalPerformers = new ArrayList<>();
	
	private List<WorkProductXACDML> inputWorkProducts = new ArrayList<>();
	private List<WorkProductXACDML> outputWorkProducts = new ArrayList<>();
	
	public void addAdditionaPerformer(Role role) {
		additionalPerformers.add(role);
	}
	
	public void addInputWorkProduct(WorkProductXACDML wp) {
		inputWorkProducts.add(wp);
	}
	
	public void addOutputWorkProduct(WorkProductXACDML wp) {
		outputWorkProducts.add(wp);
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBestFitProbabilityDistribution() {
		return bestFitProbabilityDistribution;
	}
	public void setBestFitProbabilityDistribution(String bestFitProbabilityDistribution) {
		this.bestFitProbabilityDistribution = bestFitProbabilityDistribution;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public String getMeasurementUnity() {
		return measurementUnity;
	}
	public void setMeasurementUnity(String measurementUnity) {
		this.measurementUnity = measurementUnity;
	}
	public double getDurationMean() {
		return durationMean;
	}
	public void setDurationMean(double durationMean) {
		this.durationMean = durationMean;
	}
	public double getDurationStandardDeviation() {
		return durationStandardDeviation;
	}
	public void setDurationStandardDeviation(double durationStandardDeviation) {
		this.durationStandardDeviation = durationStandardDeviation;
	}
	public int getMinimumNumberOfDevelopersNeeded() {
		return minimumNumberOfDevelopersNeeded;
	}
	public void setMinimumNumberOfDevelopersNeeded(int minimumNumberOfDevelopersNeeded) {
		this.minimumNumberOfDevelopersNeeded = minimumNumberOfDevelopersNeeded;
	}
	
	

}
