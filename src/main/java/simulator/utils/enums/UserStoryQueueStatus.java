package simulator.utils.enums;

public enum UserStoryQueueStatus {
    IN_PROJECT("In Project", 1), IN_RELEASE("In Release", 2), IN_ITERATION("In Iteration",
	    3), IN_DEVELOPMENT("In Development", 4), DONE("Done", 5), NOT_COMPLETED("Not Completed", 6);

    private UserStoryQueueStatus(String name, int value) {
	this.name = name;
	this.value = value;
    }

    private String name;
    private int value;

    @Override
    public String toString() {
	return name;
    }

    public int getValue() {
	return value;
    }
}
