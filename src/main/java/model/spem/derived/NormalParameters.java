package model.spem.derived;

public class NormalParameters extends Parameters {

    private double average;
    private double standardDeviation;

    public double getMean() {
	return average;
    }

    public void setMean(double mean) {
	this.average = mean;
    }

    public double getStandardDeviation() {
	return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
	this.standardDeviation = standardDeviation;
    }

}
