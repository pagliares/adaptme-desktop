package model.spem.util;

import java.util.ArrayList;
import java.util.List;

public enum StartConfiguration {
    DISTRIBUTION("Distribution"), MANUALLY("Manually"), Task("Process Task");
    private String name;

    private StartConfiguration(String name) {
	this.name = name;
    }

    public static List<String> getList() {
	List<String> list = new ArrayList<>();
	for (StartConfiguration distribution : StartConfiguration.values()) {
	    list.add(distribution.getName());
	}
	return list;
    }

    public String getName() {
	return name;
    }
}
