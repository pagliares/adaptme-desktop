package simulator.spem.xacdml.results;

public class ActivityResults extends SPEMResults {

	private int quantityOfActivities = 0;
	
	public ActivityResults(String activityName) {
		super(activityName);
		quantityOfActivities++;
	}
	
	public int getQuantityOfActivities() {
		return quantityOfActivities;
	}
	public void setQuantityOfActivities(int quantityOfActivities) {
		this.quantityOfActivities = quantityOfActivities;
	}
	
	public void addQuantityOfActivities() {
		quantityOfActivities++;
	}
	
	@Override
	public String toString() {
		String result = getName().split("_")[1] + " was executed  " + quantityOfActivities  +  " times";
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
