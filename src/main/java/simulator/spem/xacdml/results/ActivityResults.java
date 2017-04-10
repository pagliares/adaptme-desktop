package simulator.spem.xacdml.results;

public class ActivityResults extends SPEMResults {
	
	private double timeActivityStarted;
	private double timeActivityFinished;
	
	public ActivityResults(String activityName, double timeActivityStarted, double timeActivityFinished) {
		super(activityName);
		this.timeActivityStarted = timeActivityStarted;
		this.timeActivityFinished = timeActivityFinished;
	}

	@Override
	public double getTimeWorkBreakdownElementStarted() {
		return timeActivityStarted;
	}

	@Override
	public double getTimeWorkBreakdownElementFinished() {
		return timeActivityFinished;
	}
}
