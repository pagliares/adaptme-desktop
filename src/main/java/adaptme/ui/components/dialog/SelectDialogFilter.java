package adaptme.ui.components.dialog;

public enum SelectDialogFilter {
    SUPPORTING_MATERIALS("Supporting Materials"), CONTENT_PACKAGES("Content Packages"), METHOD_PLUGINS(
	    "Method Plug-ins"), ROLE("Role"), ROLE_PRIMARY_PERFORMERS("Role"), ROLE_ADDITIONAL_PERFORMERS(
		    "Role"), WORK_PRODUCTS("Work Products"), WORK_PRODUCTS_MANDATORY_INPUTS(
			    "Work Products"), WORK_PRODUCTS_OPTIONAL_INPUTS("Work Products"), WORK_PRODUCTS_OUTPUTS(
				    "Work Products"), GUIDANCE("Guidance"), TASK("Task"), ROLESET(
					    "Role Set"), CUSTOM_CATEGORIES("Custom Categories"), ARTIFACT(
						    "Artifact"), DOMAIN("Domain"), WORK_PRODUCT_KINDS(
							    "Work Product Kinds"), OUTCOME("Outcome"), DELIVERABLE(
								    "Deliverable"), DISCIPLANE("Discipline");

    private String text;

    SelectDialogFilter(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }

    @Override
    public String toString() {
	return this.text;
    }
}
