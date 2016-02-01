package model.spem.derived;

public class BinomialParameters extends Parameters {

    private int trials;
    private int probabilityOfSuccess;

    public int getTrials() {
	return trials;
    }

    public void setTrials(int trials) {
	this.trials = trials;
    }

    public int getProbabilityOfSuccess() {
	return probabilityOfSuccess;
    }

    public void setProbabilityOfSuccess(int probabilityOfSuccess) {
	this.probabilityOfSuccess = probabilityOfSuccess;
    }

}
