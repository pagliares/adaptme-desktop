package simulator.utils.enums;

public enum UserStoryPriority {

    MUST(3, "Must"), SHOULD(2, "Should"), COULD(1, "Could");

    private int value;
    private String name;

    private UserStoryPriority(int value, String name) {
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
