package model.spem.derived;

public class PoissonParameters extends Parameters {
    private double mean;
    private double epsilon;
    private int maxIterations;

    public double getMean() {
	return mean;
    }

    public void setMean(double mean) {
	this.mean = mean;
    }

    public double getEpsilon() {
	return epsilon;
    }

    public void setEpsilon(double epsilon) {
	this.epsilon = epsilon;
    }

    public int getMaxIterations() {
	return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
	this.maxIterations = maxIterations;
    }

}
