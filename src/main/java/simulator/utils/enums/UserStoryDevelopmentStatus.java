package simulator.utils.enums;

public enum UserStoryDevelopmentStatus {

    TODO(1, "TODO"), IN_PROGRESS(2, "In progress"), DONE(3, "Done"), NOT_COMPLETED(4, "Not completed");

    private int value;
    private String name;

    private UserStoryDevelopmentStatus(int value, String name) {
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
