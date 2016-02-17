package simulator.base;

import java.util.ArrayList;
import java.util.List;

public enum QueueObserverType {
	NONE("None"), TIME("Time"), LENGTH("Length"), STATIONARY("Stationary");
	
	private String name;

	private QueueObserverType(String name) {
		this.name = name;
	}

	public static List<String> getList() {
		List<String> list = new ArrayList<>();
		for (QueueObserverType queueObserverType : QueueObserverType.values()) {
			list.add(queueObserverType.getName());
		}
		return list;
	}

	public String getName() {
		return name;
	}

	public static QueueObserverType getQueueObserverTypeByName(String name) {
		for (QueueObserverType queueObserverType : QueueObserverType.values()) {
			if (name.equals(queueObserverType.getName())) {
				return queueObserverType;
			}
		}
		return null;
	}
}
