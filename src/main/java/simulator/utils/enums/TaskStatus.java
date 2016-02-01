package simulator.utils.enums;

public enum TaskStatus {

    TODO(1, "TODO"), IN_PROGRESS(2, "In progress"), DONE(3, "Done");

    private int value;
    private String name;

    private TaskStatus(int value, String name) {
	this.value = value;
	this.name = name;
    }

    public int getValue() {
	return value;
    }

    @Override
    public String toString() {
	return name;
    }
}
