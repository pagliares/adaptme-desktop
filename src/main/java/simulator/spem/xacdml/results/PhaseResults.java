package simulator.spem.xacdml.results;

public class PhaseResults {
	private double timePhaseStarted;
	private double TimePhaseFinished;
	
	public PhaseResults(double timePhaseStarted, double timePhaseFinished) {
		super();
		this.timePhaseStarted = timePhaseStarted;
		TimePhaseFinished = timePhaseFinished;
	}
	
	public double getTimePhaseStarted() {
		return timePhaseStarted;
	}
	public void setTimePhaseStarted(double timePhaseStarted) {
		this.timePhaseStarted = timePhaseStarted;
	}
	
	@Override
	public String toString() {
		String result = " started at (day)..: " + timePhaseStarted/480  +  " finished at (day)  : " +  TimePhaseFinished/480;
		return result;
	}
}
