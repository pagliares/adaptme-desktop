package simulator.spem.xacdml.results;

public class IterationResults extends SPEMResults {
	
	private double timeIterationOrReleaseStarted;
	private double timeIterationOrReleaseFinished;
	private int quantityOfIterations = 0;
	
	public IterationResults(String name, double timeIterationOrReleaseStarted,
			double timeIterationOrFinished) {
		super(name);
	 
		this.timeIterationOrReleaseStarted = timeIterationOrReleaseStarted;
		this.timeIterationOrReleaseFinished = timeIterationOrFinished;
		quantityOfIterations++;
	}

	public double getTimeIterationOrReleaseStarted() {
		return timeIterationOrReleaseStarted;
	}

	public void setTimeIterationOrReleaseStarted(double timeIterationOrReleaseStarted) {
		this.timeIterationOrReleaseStarted = timeIterationOrReleaseStarted;
	}

	public int getQuantityOfIterations() {
		return quantityOfIterations;
	}
	
	public void addQuantityOfIterations() {
		quantityOfIterations++;
	}
	
	@Override
	public String toString() {
		String result = getName().split("_")[1] + " started at (day)..: " + Math.ceil(timeIterationOrReleaseStarted/480)  +  ", finished at (day)..: " + Math.ceil(timeIterationOrReleaseFinished/480)
				+ " and was executed  " + quantityOfIterations  +  " times";
		return result;
	}

	@Override
	public double getTimeWorkBreakdownElementStarted() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTimeWorkBreakdownElementFinished() {
		// TODO Auto-generated method stub
		return 0;
	}
}
