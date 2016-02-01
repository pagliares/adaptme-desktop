package model.spem.util;

import java.util.ArrayList;
import java.util.List;

public enum TimeEnum {
    SECONDS("Seconds"), MINUTES("Minutes"), HOURS("Hours"), DAYS("Days"), MONTHS("Months");
    private String name;

    private TimeEnum(String name) {
	this.name = name;
    }

    public static List<String> getList() {
	List<String> list = new ArrayList<>();
	for (TimeEnum distribution : TimeEnum.values()) {
	    list.add(distribution.getName());
	}
	return list;
    }

    public String getName() {
	return name;
    }
}
