package simulator.base;

import java.util.ArrayList;
import java.util.List;

public enum ActiveObserverType {
	
	NONE("None"), ACTIVE("Active"), PROCESSOR("Processor"), DELAY("Delay");
	
	private String name;

	private ActiveObserverType(String name) {
		this.name = name;
	}

	public static List<String> getList() {
		List<String> list = new ArrayList<>();
		for (ActiveObserverType activeObserverType : ActiveObserverType.values()) {
			list.add(activeObserverType.getName());
		}
		return list;
	}

	public String getName() {
		return name;
	}

	public static ActiveObserverType getActiveObserverTypeByName(String name) {
		for (ActiveObserverType activeObserverType : ActiveObserverType.values()) {
			if (name.equals(activeObserverType.getName())) {
				return activeObserverType;
			}
		}
		return null;
	}
}
