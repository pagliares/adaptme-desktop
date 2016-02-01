package simulator.utils.parameter;

//Table A3
public class InnerModelParameter {

    private double macroUserStoryCoefficient;
    private double[] prioritiesProbabilityArray;
    private double learningCoefficientSolo;
    private double learningCoefficientPair;
    private double problemReportsRate;
    private double maximumProbabilityOfRefactoringSession;
    private double maximumDaysWithoutRefactoring;
    private double refactoringModelCoefficient;
    private double minimumDefectInjectionRate;
    private double maximumDefectInjectionRate;
    private double probabilityToFixDefectWithTDD;
    private double probabilityToFixDefectWithoutTDD;
    private double testFistInfluence;
    private double pairProgrammingInfluence;

    public double getMacroUserStoryCoefficient() {
	return macroUserStoryCoefficient;
    }

    public double[] getPrioritiesProbabilityArray() {
	return prioritiesProbabilityArray;
    }

    public double getLearningCoefficientSolo() {
	return learningCoefficientSolo;
    }

    public double getLearningCoefficientPair() {
	return learningCoefficientPair;
    }

    public double getProblemReportsRate() {
	return problemReportsRate;
    }

    public double getMaximumProbabilityOfRefactoringSession() {
	return maximumProbabilityOfRefactoringSession;
    }

    public double getMaximumDaysWithoutRefactoring() {
	return maximumDaysWithoutRefactoring;
    }

    public double getRefactoringModelCoefficient() {
	return refactoringModelCoefficient;
    }

    public double getMinimumDefectInjectionRate() {
	return minimumDefectInjectionRate;
    }

    public double getMaximumDefectInjectionRate() {
	return maximumDefectInjectionRate;
    }

    public double getProbabilityToFixDefectWithTDD() {
	return probabilityToFixDefectWithTDD;
    }

    public double getProbabilityToFixDefectWithoutTDD() {
	return probabilityToFixDefectWithoutTDD;
    }

    public double getTestFistInfluence() {
	return testFistInfluence;
    }

    public void setTestFistInfluence(double testFistInfluence) {
	this.testFistInfluence = testFistInfluence;
    }

    public double getPairProgrammingInfluence() {
	return pairProgrammingInfluence;
    }

    public void setPairProgrammingInfluence(double pairProgrammingInfluence) {
	this.pairProgrammingInfluence = pairProgrammingInfluence;
    }

}
