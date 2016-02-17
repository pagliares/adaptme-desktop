package model.spem.derived;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum BestFitDistribution {
	
	NONE("None"), CONSTANT("Constant"), UNIFORM("Uniform"), NORMAL("Normal"), NEGATIVE_EXPONENTIAL("Negative exponential"), POISSON("Poisson");

    private String name;

    private BestFitDistribution(String name) {
	this.name = name;
    }

    public static List<String> getList() {
	List<String> list = new ArrayList<>();
	for (BestFitDistribution distribution : BestFitDistribution.values()) {
	    list.add(distribution.getName());
	}
	return list;
    }

    public String getName() {
	return name;
    }

    public static BestFitDistribution getDistributionByName(String name) {
	for (BestFitDistribution distribution : BestFitDistribution.values()) {
	    if (name.equals(distribution.getName())) {
		return distribution;
	    }
	}
	return null;
    }

}
