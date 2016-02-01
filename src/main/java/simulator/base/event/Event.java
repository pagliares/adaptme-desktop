package simulator.base.event;

import executive.Executive;
import executive.event.Activity;
import simulator.base.entity.Project;
import simulator.utils.ApplicationContext;
import simulator.utils.log.Logger;

public abstract class Event implements Activity {

    ApplicationContext applicationContext;

    public Event(ApplicationContext applicationContext) {
	this.applicationContext = applicationContext;
    }

    public Executive getExecutive() {
	return applicationContext.getExecutive();
    }

    public Project getProject() {
	return applicationContext.getProject();
    }

    public Logger getLogger() {
	return applicationContext.getLogger();
    }

    public ApplicationContext getApplicationContext() {
	return applicationContext;
    }

}
