package simulator.base;

import model.spem.derived.BestFitDistribution;

public class WorkProduct {
	
    private String name;
    
    private int done;
    private boolean demandWorkProduct;
	private int intialQuantity;

    private BestFitDistribution bestFitDistribution;

    private boolean lock;
    private String status;
    
    private String queueName = "" ;
    private int capacity;
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

     

	public boolean isDemandWorkProduct() {
		return demandWorkProduct;
	}

	public void setDemandWorkProduct(boolean demandWorkProduct) {
		this.demandWorkProduct = demandWorkProduct;
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
		return "WorkProduct [name=" + name + ", demandWorkProduct=" + demandWorkProduct
				+ ", bestFitDistribution=" + bestFitDistribution + ", queueName=" + queueName
				+ ", capacity=" + capacity + ", policy=" + policy + "]";
	}

	public int getIntialQuantity() {
		return intialQuantity;
	}

	public void setIntialQuantity(int intialQuantity) {
		this.intialQuantity = intialQuantity;
	}
}
