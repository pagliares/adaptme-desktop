package simulator.spem.xacdml.results;

public abstract class SPEMResults {
	private String name;

	public SPEMResults(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public abstract double getTimeWorkBreakdownElementStarted();
	
	public abstract double getTimeWorkBreakdownElementFinished();

		
	
	
	 

}