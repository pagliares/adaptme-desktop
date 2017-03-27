package simulator.spem.xacdml.results;

public class PhaseResults extends SPEMResults {
	private double timePhaseStarted;
	private double timePhaseFinished;
	
	public PhaseResults(String phaseName, double timePhaseStarted, double timePhaseFinished) {
		super(phaseName);
		this.timePhaseStarted = timePhaseStarted;
		timePhaseFinished = timePhaseFinished;
	}
	
	public double getTimePhaseStarted() {
		return timePhaseStarted;
	}
	public void setTimePhaseStarted(double timePhaseStarted) {
		this.timePhaseStarted = timePhaseStarted;
	}
	
	@Override
	public String toString() {
		String result = getName() + " started at (day)..: " + Math.ceil(timePhaseStarted/480)  +  " finished at (day)  : " +  Math.ceil(timePhaseFinished/480);
		return result;
	}

	@Override
	public double getTimeWorkBreakdownElementStarted() {
		return timePhaseStarted;
	}

	@Override
	public double getTimeWorkBreakdownElementFinished() {
		return timePhaseFinished;
	}
}
