package simulator.utils.parameter;

// Table A2
public class ProjectSpecificParameter {

    private int numberOfUserStory;
    private int numberOfDevelopers;
    private int projectDuration;
    private int teamStartVelocity;
    private double releasePlanningDuration;
    private double iterationDuration;
    private double numberOfIterationsPerRelease;
    private double[] sessionDurationProbabilityArray;
    private double meanOfUserStoriesPointsEstimation;
    private double sdOfUserStoriesPointsEstimation;
    private double meanOfUserStoriesEstimationError;
    private double sdOfUserStoriesEstimationError;
    private double meanProductivityCoefficientsClasses;
    private double sdProductivityCoefficientsClasses;
    private double meanProductivityCoefficientsMetodos;
    private double sdProductivityCoefficientsMetodos;
    private double meanproductivityCoefficientsLines;
    private double sdproductivityCoefficientsLines;

    private int pairProgrammingAdoption;
    private int testFirstAdoption;

    private int soloTestFirstAdoption;
    private int soloTestAfterAdoption;
    private int pairTestFirstAdoption;
    private int pairTestAfterAdoption;
    private int refactoingAdoption;

    private int simulationDelay;
    private boolean pauseOnEndOfIteration;
    private boolean pauseOnEndOfDevelopment;
    private boolean pauseOnEndOfBusinessDay;

    public int getNumberOfUserStory() {
	return numberOfUserStory;
    }

    public void setNumberOfUserStory(int numberOfUserStory) {
	this.numberOfUserStory = numberOfUserStory;
    }

    public int getNumberOfDevelopers() {
	return numberOfDevelopers;
    }

    public void setNumberOfDevelopers(int numberOfDevelopers) {
	this.numberOfDevelopers = numberOfDevelopers;
    }

    public int getProjectDuration() {
	return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
	this.projectDuration = projectDuration;
    }

    public int getTeamStartVelocity() {
	return teamStartVelocity;
    }

    public void setTeamStartVelocity(int teamStartVelocity) {
	this.teamStartVelocity = teamStartVelocity;
    }

    public int getPairProgrammingAdoption() {
	return pairProgrammingAdoption;
    }

    public void setPairProgrammingAdoption(int pairProgrammingAdoption) {
	this.pairProgrammingAdoption = pairProgrammingAdoption;
    }

    public int getTestFirstAdoption() {
	return testFirstAdoption;
    }

    public void setTestFirstAdoption(int testFirstAdoption) {
	this.testFirstAdoption = testFirstAdoption;
    }

    public double getReleaseDuration() {
	return getIterationDuration() * getNumberOfIterationsPerRelease();
    }

    public double getReleasePlanningDuration() {
	return releasePlanningDuration;
    }

    public void setReleasePlanningDuration(double releasePlanningDuration) {
	this.releasePlanningDuration = releasePlanningDuration;
    }

    public double getIterationDuration() {
	return iterationDuration;
    }

    public void setIterationDuration(double iterationDuration) {
	this.iterationDuration = iterationDuration;
    }

    public double getNumberOfIterationsPerRelease() {
	return numberOfIterationsPerRelease;
    }

    public void setNumberOfIterationsPerRelease(double numberOfIterationsPerRelease) {
	this.numberOfIterationsPerRelease = numberOfIterationsPerRelease;
    }

    public double[] getSessionDurationProbabilityArray() {
	return sessionDurationProbabilityArray;
    }

    public void setSessionDurationProbabilityArray(double[] sessionDurationProbabilityArray) {
	this.sessionDurationProbabilityArray = sessionDurationProbabilityArray;
    }

    public double getMeanOfUserStoriesPointsEstimation() {
	return meanOfUserStoriesPointsEstimation;
    }

    public void setMeanOfUserStoriesPointsEstimation(double meanOfUserStoriesPointsEstimation) {
	this.meanOfUserStoriesPointsEstimation = meanOfUserStoriesPointsEstimation;
    }

    public double getSdOfUserStoriesPointsEstimation() {
	return sdOfUserStoriesPointsEstimation;
    }

    public void setSdOfUserStoriesPointsEstimation(double sdOfUserStoriesPointsEstimation) {
	this.sdOfUserStoriesPointsEstimation = sdOfUserStoriesPointsEstimation;
    }

    public double getMeanOfUserStoriesEstimationError() {
	return meanOfUserStoriesEstimationError;
    }

    public void setMeanOfUserStoriesEstimationError(double meanOfUserStoriesEstimationError) {
	this.meanOfUserStoriesEstimationError = meanOfUserStoriesEstimationError;
    }

    public double getSdOfUserStoriesEstimationError() {
	return sdOfUserStoriesEstimationError;
    }

    public void setSdOfUserStoriesEstimationError(double sdOfUserStoriesEstimationError) {
	this.sdOfUserStoriesEstimationError = sdOfUserStoriesEstimationError;
    }

    public double getMeanProductivityCoefficientsClasses() {
	return meanProductivityCoefficientsClasses;
    }

    public void setMeanProductivityCoefficientsClasses(double meanProductivityCoefficientsClasses) {
	this.meanProductivityCoefficientsClasses = meanProductivityCoefficientsClasses;
    }

    public double getSdProductivityCoefficientsClasses() {
	return sdProductivityCoefficientsClasses;
    }

    public void setSDProductivityCoefficientsClasses(double sdProductivityCoefficientsClasses) {
	this.sdProductivityCoefficientsClasses = sdProductivityCoefficientsClasses;
    }

    public double getMeanProductivityCoefficientsMetodos() {
	return meanProductivityCoefficientsMetodos;
    }

    public void setMeanProductivityCoefficientsMetodos(double meanProductivityCoefficientsMetodos) {
	this.meanProductivityCoefficientsMetodos = meanProductivityCoefficientsMetodos;
    }

    public double getSdProductivityCoefficientsMetodos() {
	return sdProductivityCoefficientsMetodos;
    }

    public void setSDProductivityCoefficientsMetodos(double sdProductivityCoefficientsMetodos) {
	this.sdProductivityCoefficientsMetodos = sdProductivityCoefficientsMetodos;
    }

    public double getMeanProductivityCoefficientsLines() {
	return meanproductivityCoefficientsLines;
    }

    public void setMeanproductivityCoefficientsLines(double meanproductivityCoefficientsLines) {
	this.meanproductivityCoefficientsLines = meanproductivityCoefficientsLines;
    }

    public double getSdproductivityCoefficientsLines() {
	return sdproductivityCoefficientsLines;
    }

    public void setSDproductivityCoefficientsLines(double sdproductivityCoefficientsLines) {
	this.sdproductivityCoefficientsLines = sdproductivityCoefficientsLines;
    }

    public int getSimulationDelay() {
	return simulationDelay;
    }

    public void setSimulationDelay(int simulationDelay) {
	this.simulationDelay = simulationDelay;
    }

    public boolean isPauseOnEndOfIteration() {
	return pauseOnEndOfIteration;
    }

    public void setPauseOnEndOfIteration(boolean pauseOnEndOfSprint) {
	pauseOnEndOfIteration = pauseOnEndOfSprint;
    }

    public boolean isPauseOnEndOfDevelopment() {
	return pauseOnEndOfDevelopment;
    }

    public void setPauseOnEndOfDevelopment(boolean pauseOnEndOfDevelopment) {
	this.pauseOnEndOfDevelopment = pauseOnEndOfDevelopment;
    }

    public boolean isPauseOnEndOfBusinessDay() {
	return pauseOnEndOfBusinessDay;
    }

    public void setPauseOnEndOfBusinessDay(boolean pauseOnEndOfBusinessDay) {
	this.pauseOnEndOfBusinessDay = pauseOnEndOfBusinessDay;
    }

    public int getSoloTestFirstAdoption() {
	return soloTestFirstAdoption;
    }

    public void setSoloTestFirstAdoption(int soloTestFirstAdoption) {
	this.soloTestFirstAdoption = soloTestFirstAdoption;
    }

    public int getSoloTestAfterAdoption() {
	return soloTestAfterAdoption;
    }

    public void setSoloTestAfterAdoption(int soloTestAfterAdoption) {
	this.soloTestAfterAdoption = soloTestAfterAdoption;
    }

    public int getPairTestFirstAdoption() {
	return pairTestFirstAdoption;
    }

    public void setPairTestFirstAdoption(int pairTestFirstAdoption) {
	this.pairTestFirstAdoption = pairTestFirstAdoption;
    }

    public int getPairTestAfterAdoption() {
	return pairTestAfterAdoption;
    }

    public void setPairTestAfterAdoption(int pairTestAfterAdoption) {
	this.pairTestAfterAdoption = pairTestAfterAdoption;
    }

    public int getRefactoingAdoption() {
	return refactoingAdoption;
    }

    public void setRefactoringAdoption(int refactoingAdoption) {
	this.refactoingAdoption = refactoingAdoption;
    }

}
