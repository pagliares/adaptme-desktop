package simulator.base;

import java.util.ArrayList;
import java.util.List;

public enum QueueType {
	
	QUEUE("Queue"), STACK("Stack"), SET("Set");

	private String value;

	private QueueType(String value) {
		this.value = value;
	}

	public static List<String> getList() {
		List<String> list = new ArrayList<>();
		for (QueueType queueType : QueueType.values()) {
			list.add(queueType.getName());
		}
		return list;
	}

	public String getName() {
		return value;
	}

	public static QueueType gettQueueTypeByName(String name) {
		for (QueueType queueType : QueueType.values()) {
			if (name.equals(queueType.getName())) {
				return queueType;
			}
		}
		return null;
	}
}
