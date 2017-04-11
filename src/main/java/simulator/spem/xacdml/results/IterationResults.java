package simulator.spem.xacdml.results;

public class IterationResults extends SPEMResults {
	
	private double timeIterationOrReleaseStarted;
	private double timeIterationOrReleaseFinished;
 	
	public IterationResults(String name, double timeIterationOrReleaseStarted,
			double timeIterationOrFinished) {
		super(name);
	 
		this.timeIterationOrReleaseStarted = timeIterationOrReleaseStarted;
		this.timeIterationOrReleaseFinished = timeIterationOrFinished;
 	}

	@Override
	public double getTimeWorkBreakdownElementStarted() {
		return timeIterationOrReleaseStarted;
	}

	@Override
	public double getTimeWorkBreakdownElementFinished() {
		return timeIterationOrReleaseFinished;
	}
}
