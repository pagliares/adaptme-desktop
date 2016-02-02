package simulator.base;

public class Role {
	private String name;
	private String intialQuantity;
	private boolean observeStationaryTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntialQuantity() {
		return intialQuantity;
	}
	public void setIntialQuantity(String intialQuantity) {
		this.intialQuantity = intialQuantity;
	}
	public boolean isObserveStationaryTime() {
		return observeStationaryTime;
	}
	public void setObserveStationaryTime(boolean observeStationaryTime) {
		this.observeStationaryTime = observeStationaryTime;
	}

}
