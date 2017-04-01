package simulator.base;

import model.spem.util.BehaviourAtEndOfIterationType;
import model.spem.util.ConditionToProcessType;
import model.spem.util.DependencyType;
import model.spem.util.ProcessContentType;
import model.spem.util.ProcessingQuantityType;

public class ExtendedXACDMLAttributes {
	
	private ProcessContentType spemType;
	
	private DependencyType dependencyType;
	private ProcessingQuantityType processingQuantityType;
	private ConditionToProcessType conditionToProcessType;
	private BehaviourAtEndOfIterationType behaviourAtEndOfIterationType;
	
	private double timebox;
	private String parent;
	private int quantityResourcesNeededByActivity;
	
	
	public ExtendedXACDMLAttributes(ProcessContentType spemType, DependencyType dependencyType,
			ProcessingQuantityType processingQuantityType, ConditionToProcessType conditionToProcessType,
			BehaviourAtEndOfIterationType behaviourAtEndOfIterationType, double timebox, String parent,
			int quantityResourcesNeededByActivity) {
		super();
		this.spemType = spemType;
		this.dependencyType = dependencyType;
		this.processingQuantityType = processingQuantityType;
		this.conditionToProcessType = conditionToProcessType;
		this.behaviourAtEndOfIterationType = behaviourAtEndOfIterationType;
		this.timebox = timebox;
		this.parent = parent;
		this.quantityResourcesNeededByActivity = quantityResourcesNeededByActivity;
	}


	public ProcessContentType getSpemType() {
		return spemType;
	}


	public void setSpemType(ProcessContentType spemType) {
		this.spemType = spemType;
	}


	public DependencyType getDependencyType() {
		return dependencyType;
	}


	public void setDependencyType(DependencyType dependencyType) {
		this.dependencyType = dependencyType;
	}


	public ProcessingQuantityType getProcessingQuantityType() {
		return processingQuantityType;
	}


	public void setProcessingQuantityType(ProcessingQuantityType processingQuantityType) {
		this.processingQuantityType = processingQuantityType;
	}


	public ConditionToProcessType getConditionToProcessType() {
		return conditionToProcessType;
	}


	public void setConditionToProcessType(ConditionToProcessType conditionToProcessType) {
		this.conditionToProcessType = conditionToProcessType;
	}


	public BehaviourAtEndOfIterationType getBehaviourAtEndOfIterationType() {
		return behaviourAtEndOfIterationType;
	}


	public void setBehaviourAtEndOfIterationType(BehaviourAtEndOfIterationType behaviourAtEndOfIterationType) {
		this.behaviourAtEndOfIterationType = behaviourAtEndOfIterationType;
	}


	public double getTimebox() {
		return timebox;
	}


	public void setTimebox(double timebox) {
		this.timebox = timebox;
	}


	public String getParent() {
		return parent;
	}


	public void setParent(String parent) {
		this.parent = parent;
	}


	public int getQuantityResourcesNeededByActivity() {
		return quantityResourcesNeededByActivity;
	}


	public void setQuantityResourcesNeededByActivity(int quantityResourcesNeededByActivity) {
		this.quantityResourcesNeededByActivity = quantityResourcesNeededByActivity;
	}
	
 
}
