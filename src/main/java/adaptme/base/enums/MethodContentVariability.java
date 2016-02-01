package adaptme.base.enums;

import org.eclipse.epf.uma.VariabilityType;

public enum MethodContentVariability {

    NOT_APPLICAPLE("Not Applicable"), CONTRIBUTES("Contributes"), EXTENDS("Extends"), REPLACES(
	    "Replaces"), EXTENDS_REPLACES("Extends and Replaces");

    private String name;
    private VariabilityType variabilityType;

    MethodContentVariability(String name) {
	this.name = name;
    }

    public void setVariabilityType(VariabilityType variabilityType) {
	this.variabilityType = variabilityType;
    }

    public VariabilityType getVariabilityType() {
	return variabilityType;
    }

    @Override
    public String toString() {
	return name;
    }

    public static MethodContentVariability make(VariabilityType variabilityType) {
	MethodContentVariability methodContentVariability;
	if (variabilityType == VariabilityType.CONTRIBUTES) {
	    methodContentVariability = MethodContentVariability.CONTRIBUTES;
	} else if (variabilityType == VariabilityType.EXTENDS) {
	    methodContentVariability = MethodContentVariability.EXTENDS;
	} else if (variabilityType == VariabilityType.REPLACES) {
	    methodContentVariability = MethodContentVariability.REPLACES;
	} else if (variabilityType == VariabilityType.EXTENDS_REPLACES) {
	    methodContentVariability = MethodContentVariability.EXTENDS_REPLACES;
	} else {
	    methodContentVariability = MethodContentVariability.NOT_APPLICAPLE;
	}
	methodContentVariability.setVariabilityType(variabilityType);
	return methodContentVariability;
    }
}
