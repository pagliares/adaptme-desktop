package model.spem.derived;

public class PoissonParameters extends Parameters {
    private double average;
//    private double epsilon;
//    private int maxIterations;

    public double getMean() {
	return average;
    }

    public void setMean(double mean) {
	this.average = mean;
    }

//    public double getEpsilon() {
//	return epsilon;
//    }
//
//    public void setEpsilon(double epsilon) {
//	this.epsilon = epsilon;
//    }
//
//    public int getMaxIterations() {
//	return maxIterations;
//    }
//
//    public void setMaxIterations(int maxIterations) {
//	this.maxIterations = maxIterations;
//    }

}
