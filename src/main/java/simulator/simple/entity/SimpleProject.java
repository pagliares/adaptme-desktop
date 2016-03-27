package simulator.simple.entity;

import java.util.HashMap;
import java.util.List;

import executive.entity.Entity;
import model.spem.ProcessContentRepository;
import model.spem.config.ContainerConfig;
import model.spem.config.RoleConfig;
import model.spem.config.TaskConfig;
import model.spem.util.FinishType;
import simulator.base.WorkProductXACDML;
import simulator.base.entity.Project;
import simulator.simple.Simple;

public class SimpleProject extends Project {

    private long duration;
    private HashMap<String, List<WorkProductXACDML>> workProductHash;
    private HashMap<String, ContainerConfig> sessionMeasurementConfigHash;
    private HashMap<String, Entity> conteinerEntityHash;
    private HashMap<String, TaskConfig> taskMeasurementConfigHash;
    private HashMap<String, List<Developer>> developersHash;
    private HashMap<String, RoleConfig> roleMeasurementConfigHash;
    private Simple simple;

    public SimpleProject(String name) {
	super(name);
    }

    public long getDuration() {
	return duration;
    }

    public void setDuration(long duration) {
	this.duration = duration;
    }

    public boolean hasWorkProductToImplementInThePredecessorContainer(ProcessContentRepository predecessor) {
	for (ProcessContentRepository processContentRepository : predecessor.getChildren()) {
	    List<WorkProductXACDML> list = workProductHash.get(processContentRepository.getName());
	    for (WorkProductXACDML workProduct : list) {
		TaskConfig taskConfig = taskMeasurementConfigHash.get(processContentRepository.getName());
		if (taskConfig.getFinishType() == FinishType.STATUS) {
		    ContainerConfig containerConfig = sessionMeasurementConfigHash.get(predecessor.getName());
		    if (!workProduct.getStatus().equals(containerConfig.getFinishStatus())) {
			return true;
		    }
		} else {
		    if (workProduct.getDone() < workProduct.getCapacity()) {
			return true;
		    }
		}

	    }
	}
	return false;
    }

    public void setSessionMeasurementConfigHash(HashMap<String, ContainerConfig> sessionMeasurementConfigHash) {
	this.sessionMeasurementConfigHash = sessionMeasurementConfigHash;
    }

    public HashMap<String, ContainerConfig> getSessionMeasurementConfigHash() {
	return sessionMeasurementConfigHash;
    }

    public void setConteinerEntityHash(HashMap<String, Entity> conteinerEntityHash) {
	this.conteinerEntityHash = conteinerEntityHash;
    }

    public HashMap<String, Entity> getConteinerEntityHash() {
	return conteinerEntityHash;
    }

    public HashMap<String, List<WorkProductXACDML>> getWorkProductHash() {
	return workProductHash;
    }

    public void setWorkProductHash(HashMap<String, List<WorkProductXACDML>> workProductHash) {
	this.workProductHash = workProductHash;
    }

    public void setTaskMeasurementConfigHash(HashMap<String, TaskConfig> taskMeasurementConfigHash) {
	this.taskMeasurementConfigHash = taskMeasurementConfigHash;
    }

    public HashMap<String, TaskConfig> getTaskMeasurementConfigHash() {
	return taskMeasurementConfigHash;
    }

    public void setDevelopersHash(HashMap<String, List<Developer>> developersHash) {
	this.developersHash = developersHash;
    }

    public HashMap<String, List<Developer>> getDevelopersHash() {
	return developersHash;
    }

    public void setRoleMeasurementConfigHash(HashMap<String, RoleConfig> roleMeasurementConfigHash) {
	this.roleMeasurementConfigHash = roleMeasurementConfigHash;
    }

    public HashMap<String, RoleConfig> getRoleMeasurementConfigHash() {
	return roleMeasurementConfigHash;
    }

    public WorkProductXACDML getWorkProductToImplement(ProcessContentRepository content) {
	List<WorkProductXACDML> workProducts = getWorkProductHash().get(content.getName());
	for (WorkProductXACDML workProduct : workProducts) {
	    if (!workProduct.isLock() && workProduct.getDone() < workProduct.getCapacity()) {
		return workProduct;
	    }
	}
	return null;
    }

    public void setSimple(Simple simple) {
	this.simple = simple;
    }

    public Simple getSimple() {
	return simple;
    }

}
