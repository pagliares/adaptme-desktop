package model.spem.derived;

public class NormalParameters extends Parameters {

    private double mean;
    private double standardDeviation;

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

}
