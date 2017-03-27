package simulator.spem.xacdml.results;

public class MilestoneResults extends SPEMResults {
	
	private double timeReached;
 	
	public MilestoneResults(String milestoneName, double timeReached) {
		super(milestoneName);
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
		String result = getName() + " reached at (day)..: " + Math.ceil(timeReached/480);
		return result;
	}

	@Override
	public double getTimeWorkBreakdownElementStarted() {
		return 0;
	}

	@Override
	public double getTimeWorkBreakdownElementFinished() {
		return 0;
	}	 
}
