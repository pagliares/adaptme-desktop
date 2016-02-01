package simulator.simple.task;

import java.util.List;

import adaptme.util.SimulationSample;
import executive.entity.Entity;
import model.spem.ProcessContentRepository;
import model.spem.config.TaskConfig;
import model.spem.util.FinishType;
import model.spem.util.ProcessContentType;
import simulator.base.WorkProduct;
import simulator.base.event.Event;
import simulator.simple.Simple;
import simulator.simple.entity.SimpleProject;
import simulator.utils.ApplicationContext;

public class SessionEnd extends Event {

    private String name;
    private long duration;
    private long finishAt;
    private ProcessContentRepository content;
    private WorkProduct workProduct;

    public SessionEnd(ApplicationContext applicationContext, ProcessContentRepository content, long duration,
	    long finishAt) {
	super(applicationContext);
	this.content = content;
	name = content.getName();
	this.duration = duration;
	this.finishAt = finishAt;
    }

    @Override
    public void doThisNow() {
	SimpleProject projet = (SimpleProject) getProject();
	if (Simple.isContatiner(content)) {
	    String identetion = Simple.getIndentation(content);
	    getLogger().println(identetion + name + " is finishing now at " + getExecutive().getCurrentClockTime());
	    if (content.getFather() == null) {
		if (content.isRepeatable()) {// TODO add on the louder the
					     // ability to get this from the uma
					     // model
		    getProject().setFinalized();
		} else {
		    projet.getSimple().buildContainer(content);
		    projet.getSimple().build(content);
		}
	    }
	} else if (content.getType() == ProcessContentType.TASK) {
	    TaskConfig taskConfig = projet.getTaskMeasurementConfigHash().get(name);
	    if (taskConfig.getFinishType() == FinishType.STATUS) {
		String identetion = Simple.getIndentation(content);
		getLogger().println(identetion + name + " is finishing now at " + getExecutive().getCurrentClockTime());
		List<WorkProduct> list = projet.getWorkProductHash().get(name);
		if (list != null) {
		    for (WorkProduct workProduct : list) {
			TaskConfig config = projet.getTaskMeasurementConfigHash().get(name);
			if (config.getFinishType() == FinishType.STATUS) {
			    workProduct.setStatus(name);
			}
		    }
		}
	    } else if (taskConfig.getFinishType() == FinishType.SIZE) {
		workProduct.setLock(false);
		workProduct.addDone((int) SimulationSample.getSampleFromLogNormalDistribution(3, 2));
		String identetion = Simple.getIndentation(content);
		getLogger().println(identetion + name + " is finishing now at " + getExecutive().getCurrentClockTime()
			+ " with " + getExecutive().getCurrentEntity() + " working on " + workProduct);
	    }
	}
	Entity entity = getExecutive().getCurrentEntity();
	getExecutive().addEntitieToBeKilled(entity);
    }

    public long getDuration() {
	return duration;
    }

    public void setDuration(long duration) {
	this.duration = duration;
    }

    public long getFinishAt() {
	return finishAt;
    }

    public void setFinishAt(long finishAt) {
	this.finishAt = finishAt;
    }

    public WorkProduct getWorkProduct() {
	return workProduct;
    }

    public void setWorkProduct(WorkProduct workProduct) {
	this.workProduct = workProduct;
    }

}
