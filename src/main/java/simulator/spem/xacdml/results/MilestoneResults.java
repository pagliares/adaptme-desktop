package simulator.spem.xacdml.results;

public class MilestoneResults {
	
	private String milestoneName;
	private double timeReached;
 	
	public MilestoneResults(String milestoneName, double timeReached) {
		super();
		this.milestoneName = milestoneName;
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
		String result = milestoneName + " reached at (day)..: " + Math.ceil(timeReached/480);
		return result;
	}

	public String getMilestoneName() {
		return milestoneName;
	}

	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}
}
