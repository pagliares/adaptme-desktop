package simulator.base.event;

import simulator.utils.ApplicationContext;

public class OnProjectFinish extends Event {

    public OnProjectFinish(ApplicationContext applicationContext) {
	super(applicationContext);
	this.applicationContext = applicationContext;
    }

    @Override
    public void doThisNow() {
	getProject().setFinalized();
    }

}
