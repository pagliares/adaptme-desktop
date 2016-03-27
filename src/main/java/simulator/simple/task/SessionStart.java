package simulator.simple.task;

import java.util.List;

import adaptme.util.SimulationSample;
import executive.entity.Entity;
import model.spem.ProcessContentRepository;
import model.spem.config.TaskConfig;
import model.spem.derived.Parameters;
import model.spem.util.FinishType;
import model.spem.util.ProcessContentType;
import simulator.base.WorkProductXACDML;
import simulator.base.event.Event;
import simulator.simple.Simple;
import simulator.simple.entity.Developer;
import simulator.simple.entity.SimpleProject;
import simulator.utils.ApplicationContext;

public class SessionStart extends Event {

    private ProcessContentRepository content;
    private String name;

    public SessionStart(ApplicationContext applicationContext, ProcessContentRepository content, String name) {
	super(applicationContext);
	this.content = content;
	this.name = name;
    }

    @Override
    public void doThisNow() {
	SimpleProject project = (SimpleProject) getProject();
	if (getExecutive().getCurrentClockTime() >= project.getDuration()) {
	    project.setFinalized();
	    return;
	}
	ProcessContentRepository father = content.getFather();
	if (father != null) {
	    Entity conteinerEntity = project.getConteinerEntityHash().get(father.getName());
	    if (conteinerEntity != null && conteinerEntity.isAvailable()) {
		return;
	    }
	}
	if (Simple.isContatiner(content)) {
	    if (Simple.hasPredecessors(content)) {
		ProcessContentRepository predecessor = content.getPredecessors().get(0);
		if (!project.hasWorkProductToImplementInThePredecessorContainer(predecessor)) {
		    //Parameters parameters = content.getSample().getParameters();
		    long duration = (long) Math.ceil(SimulationSample.getSampleFromLogNormalDistribution(0, 0));
		    long finishAt = getExecutive().getCurrentClockTime() + duration;
		    SessionEnd sessionEnd = new SessionEnd(getApplicationContext(), content, duration, finishAt);
		    Entity entity = project.getConteinerEntityHash().get(name);
		    if (entity == null) {
			entity = new Entity(content.getName()) {
			};
		    } else {
			if (!entity.isAvailable()) {
			    return;
			}
		    }
		    String identetion = Simple.getIndentation(content);
		    getLogger().println(identetion + entity.getName() + " will start at "
			    + getExecutive().getCurrentClockTime() + " and finish at " + finishAt);
		    getExecutive().schedule(entity, sessionEnd, duration);
		    getExecutive().addNewEntity(entity);
		    project.getConteinerEntityHash().put(content.getName(), entity);
		}
	    }
	} else if (content.getType() == ProcessContentType.TASK) {
	    TaskConfig config = project.getTaskMeasurementConfigHash().get(content.getName());
	    if (config.getFinishType() == FinishType.STATUS) {
		String currentStatus = project.getWorkProductHash().get(content.getName()).get(0).getStatus();
		for (String workProductDependency : config.getWorkProductDependencies()) {
		    if (currentStatus.equals(workProductDependency)) {
			Parameters parameters = content.getSample().getParameters();
			long duration = (long) Math.ceil(SimulationSample.getSampleFromLogNormalDistribution(0, 0));
			long finishAt = getExecutive().getCurrentClockTime() + duration;
			SessionEnd sessionEnd = new SessionEnd(getApplicationContext(), content, duration, finishAt);
			Entity entity = new Entity(content.getMainRole().getName()) {
			};
			String indentation = Simple.getIndentation(content);
			getLogger().println(
				indentation + content.getName() + " with " + entity.getName() + " will start at "
					+ getExecutive().getCurrentClockTime() + " and finish at " + finishAt);
			getExecutive().schedule(entity, sessionEnd, duration);
			getExecutive().addNewEntity(entity);
			project.getConteinerEntityHash().put(content.getName(), entity);
		    }
		}
	    } else if (config.getFinishType() == FinishType.SIZE) {
		Entity entity = getIdleEntity(content.getMainRole().getName());
		if (entity == null) {
		    return;
		}
		WorkProductXACDML workProduct = project.getWorkProductToImplement(content);
		if (workProduct == null) {
		    return;
		}
		workProduct.setLock(true);
		String currentStatus = workProduct.getStatus();
		String workProductDependency = config.getWorkProductDependencies().get(0);
		if (currentStatus.equals(workProductDependency)) {
		    Parameters parameters = content.getSample().getParameters();
		    long duration = (long) Math.ceil(SimulationSample.getSampleFromLogNormalDistribution(0, 0));
		    long finishAt = getExecutive().getCurrentClockTime() + duration;
		    SessionEnd sessionEnd = new SessionEnd(getApplicationContext(), content, duration, finishAt);
		    sessionEnd.setWorkProduct(workProduct);
		    getExecutive().setCStarted();
		    String indentation = Simple.getIndentation(content);
		    getLogger().println(indentation + content.getName() + " with " + entity.getName() + " working on "
			    + workProduct.getName() + " will start at " + getExecutive().getCurrentClockTime()
			    + " and finish at " + finishAt);
		    getExecutive().schedule(entity, sessionEnd, duration);
		    getExecutive().addNewEntity(entity);
		    project.getConteinerEntityHash().put(content.getName(), entity);
		}
	    }
	}
    }

    private Entity getIdleEntity(String name) {
	SimpleProject project = (SimpleProject) getProject();
	List<Developer> developers = project.getDevelopersHash().get(name);
	for (Developer developer : developers) {
	    if (developer.isAvailable()) {
		return developer;
	    }
	}
	return null;
    }
}
