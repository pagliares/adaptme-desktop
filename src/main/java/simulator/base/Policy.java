package simulator.base;

import java.util.ArrayList;
import java.util.List;

public enum Policy {
	
	FIFO("Fifo"), STACK("Stack"), PRIORITY_QUEUE("Priority queue");  // observe que na tese do Jose Nilton, tudo e gerado como fifo (nao existe policy no dtd
	
	private String name;

	private Policy(String name) {
		this.name = name;
	}

	public static List<String> getList() {
		List<String> list = new ArrayList<>();
		for (Policy policy : Policy.values()) {
			list.add(policy.getName());
		}
		return list;
	}

	public String getName() {
		return name;
	}

	public static Policy getPolicyByName(String name) {
		for (Policy policy : Policy.values()) {
			if (name.equals(policy.getName())) {
				return policy;
			}
		}
		return null;
	}

}
