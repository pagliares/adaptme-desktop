package simulator.base.entity;

import executive.entity.Entity;

public abstract class Project extends Entity {

    private boolean finalized;
    private long timeToEndDayOfWork;

    public Project(String name) {
	super(name);
    }

    public boolean isFinalized() {
	return finalized;
    }

    public void setFinalized() {
	finalized = true;
    }

    public long getTimeToEndDayOfWork() {
	return timeToEndDayOfWork;
    }

    public void setTimeToEndDayOfWork(long timeToEndDayOfWork) {
	this.timeToEndDayOfWork = timeToEndDayOfWork;
    }
}
