package simulator.base;

public class WorkProduct {
    private String name;
    private int size;
    private int done;
    private boolean demandWorkProduct;
    private boolean lock;
    private String status;

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

}
