package simulator.base;

import adaptme.ui.window.perspective.VariableType;
import model.spem.derived.BestFitDistribution;

public class WorkProduct implements Comparable<WorkProduct>{
	
    private String name; 
    private int done;
 	private int intialQuantity;
 	private VariableType variableType;
 	private String inputOrOutput = "Input";
 	private boolean generateActivity;
 	private String taskName;

    private BestFitDistribution bestFitDistribution;

    private boolean lock;
    private String status;
    
    private String queueName = "" ;
    private int capacity = 50;
    private Policy policy;
    
	private ActiveObserverType observerType;
	private QueueType queueType;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public int getDone() {
	return done;
    }

    public void setDone(int done) {
	this.done = done;
    }

    public void addDone(int value) {
	done += value;
    }

    public boolean isLock() {
	return lock;
    }

    public void setLock(boolean lock) {
	this.lock = lock;
    }

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}


	public BestFitDistribution getBestFitDistribution() {
		return bestFitDistribution;
	}

	public void setBestFitDistribution(BestFitDistribution bestFitDistribution) {
		this.bestFitDistribution = bestFitDistribution;
	}
	
	public ActiveObserverType getObserverType() {
		return observerType;
	}

	public void setObserverType(ActiveObserverType observerType) {
		this.observerType = observerType;
	}

	public QueueType getQueueType() {
		return queueType;
	}

	public void setQueueType(QueueType queueType) {
		this.queueType = queueType;
	}

	@Override
	public String toString() {
		return "WorkProduct [name=" + name + bestFitDistribution +"=" + bestFitDistribution + ", queueName=" + queueName
				+ ", capacity=" + capacity + ", policy=" + policy + "]";
	}

	public int getIntialQuantity() {
		return intialQuantity;
	}

	public void setIntialQuantity(int intialQuantity) {
		this.intialQuantity = intialQuantity;
	}
	
	public VariableType getVariableType() {
		return variableType;
	}

	public void setVariableType(VariableType variableType) {
		this.variableType = variableType;
	}

	public String getInputOrOutput() {
		return inputOrOutput;
	}

	public void setInputOrOutput(String inputOrOutput) {
		this.inputOrOutput = inputOrOutput;
	}

	public boolean isGenerateActivity() {
		return generateActivity;
	}

	public void setGenerateActivity(boolean generateActivity) {
		this.generateActivity = generateActivity;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputOrOutput == null) ? 0 : inputOrOutput.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkProduct other = (WorkProduct) obj;
		if (inputOrOutput == null) {
			if (other.inputOrOutput != null)
				return false;
		} else if (!inputOrOutput.equals(other.inputOrOutput))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(WorkProduct o) {
		return this.getName().compareTo(o.getName());
	}
	
	 
	
 }
