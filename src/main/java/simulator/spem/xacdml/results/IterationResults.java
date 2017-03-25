package simulator.spem.xacdml.results;

public class IterationResults {
	
	private String iterationOrReleaseName;
	private double timeIterationOrReleaseStarted;
	private double timeIterationOrReleaseFinished;
	public static int counter = 0;
	
	public IterationResults(String iterationOrReleaseName, double timeIterationOrReleaseStarted,
			double timeIterationOrFinished) {
		super();
		this.iterationOrReleaseName = iterationOrReleaseName;
		this.timeIterationOrReleaseStarted = timeIterationOrReleaseStarted;
		this.timeIterationOrReleaseFinished = timeIterationOrFinished;
		 
	}

	public String getIterationOrReleaseName() {
		return iterationOrReleaseName;
	}

	public void setIterationOrReleaseName(String iterationOrReleaseName) {
		this.iterationOrReleaseName = iterationOrReleaseName;
	}

	public double getTimeIterationOrReleaseStarted() {
		return timeIterationOrReleaseStarted;
	}

	public void setTimeIterationOrReleaseStarted(double timeIterationOrReleaseStarted) {
		this.timeIterationOrReleaseStarted = timeIterationOrReleaseStarted;
	}

	public double getTimeIterationOrFinished() {
		return timeIterationOrReleaseFinished;
	}

	public void setTimeIterationOrFinished(double timeIterationOrFinished) {
		this.timeIterationOrReleaseFinished = timeIterationOrFinished;
	}

	public static int getCounter() {
		return counter;
	}
	
	@Override
	public String toString() {
		String result = iterationOrReleaseName.split("_")[1] + " started at (day)..: " + Math.ceil(timeIterationOrReleaseStarted/480)  +  ", finished at (day)..: " + Math.ceil(timeIterationOrReleaseFinished/480)
				+ " and was executed  " + counter  +  " times";
		return result;
	}
}
