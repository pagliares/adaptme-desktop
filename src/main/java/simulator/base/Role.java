package simulator.base;

public class Role {
	
	private String name;
	private int intialQuantity;
	private QueueType queueType;
	private boolean observeStationaryTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIntialQuantity() {
		return intialQuantity;
	}
	public void setIntialQuantity(int intialQuantity) {
		this.intialQuantity = intialQuantity;
	}
	public boolean isObserveStationaryTime() {
		return observeStationaryTime;
	}
	public void setObserveStationaryTime(boolean observeStationaryTime) {
		this.observeStationaryTime = observeStationaryTime;
	}
	public QueueType getQueueType() {
		return queueType;
	}
	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

}
