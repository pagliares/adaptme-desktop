package simulator.base.event;

import org.eclipse.epf.uma.TaskDescriptor;

import simulator.utils.ApplicationContext;

public abstract class Session extends Event {

    private TaskDescriptor taskDescriptor;

    public Session(ApplicationContext applicationContext, TaskDescriptor taskDescriptor) {
	super(applicationContext);
	this.taskDescriptor = taskDescriptor;
    }

    public int adjustTimeWith(long sectionSize, long timeOfDevelopment, long timeToEnd) {
	if (timeOfDevelopment - timeToEnd > 0) {
	    sectionSize -= timeOfDevelopment - timeToEnd;
	}
	return (int) sectionSize;
    }

    public TaskDescriptor getTaskDescriptor() {
	return taskDescriptor;
    }

}
