package simulator.base;

import model.spem.derived.BestFitDistribution;

public class WorkProduct {
	
    private String name;
    private int size;
    private int done;
    private boolean demandWorkProduct = false;
    
    private BestFitDistribution bestFitDistribution = BestFitDistribution.NORMAL;

    private boolean lock;
    private String status;
    
    private int quantity;
    private String queueName = "Queue name";
    private int capacity;
    private Policy policy = Policy.FIFO;
    private String observerQueueLenghtName = "Queue name";
    private String observerQueueLenghtTimeName = "0";

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
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

    @Override
    public String toString() {
	return name + " size " + size + " done " + done + ", status " + status;
    }

	public boolean isDemandWorkProduct() {
		return demandWorkProduct;
	}

	public void setDemandWorkProduct(boolean demandWorkProduct) {
		this.demandWorkProduct = demandWorkProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getObserverQueueLenghtName() {
		return observerQueueLenghtName;
	}

	public void setObserverQueueLenghtName(String observerQueueLenghtName) {
		this.observerQueueLenghtName = observerQueueLenghtName;
	}

	public String getObserverQueueLenghtTimeName() {
		return observerQueueLenghtTimeName;
	}

	public void setObserverQueueLenghtTimeName(String observerQueueLenghtTimeName) {
		this.observerQueueLenghtTimeName = observerQueueLenghtTimeName;
	}

	public BestFitDistribution getBestFitDistribution() {
		return bestFitDistribution;
	}

	public void setBestFitDistribution(BestFitDistribution bestFitDistribution) {
		this.bestFitDistribution = bestFitDistribution;
	}

}
