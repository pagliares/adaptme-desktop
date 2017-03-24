package simulator.spem.xacdml.results;

public class MilestoneResults {
	private double timeReached;
 	
	public MilestoneResults(double timeReached) {
		super();
		this.timeReached = timeReached;
 	}
	
	public double getTimeReached() {
		return timeReached;
	}

	public void setTimeReached(double timeReached) {
		this.timeReached = timeReached;
	}
	
	@Override
	public String toString() {
		String result = " reached at (day)..: " + timeReached/480;
		return result;
	}
}
