package simulator.spem.xacdml.results;

public class PhaseResults {
	private String phaseName;
	private double timePhaseStarted;
	private double TimePhaseFinished;
	
	public PhaseResults(String phaseName, double timePhaseStarted, double timePhaseFinished) {
		super();
		this.phaseName = phaseName;
		this.timePhaseStarted = timePhaseStarted;
		TimePhaseFinished = timePhaseFinished;
	}
	
	public double getTimePhaseStarted() {
		return timePhaseStarted;
	}
	public void setTimePhaseStarted(double timePhaseStarted) {
		this.timePhaseStarted = timePhaseStarted;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	
	@Override
	public String toString() {
		String result = phaseName + " started at (day)..: " + Math.ceil(timePhaseStarted/480)  +  " finished at (day)  : " +  Math.ceil(TimePhaseFinished/480);
		return result;
	}
}
