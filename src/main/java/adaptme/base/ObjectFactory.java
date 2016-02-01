package adaptme.base;

import java.util.UUID;

import javax.swing.JOptionPane;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.ArtifactDescription;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.DeliverableDescription;
import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.DeliveryProcessDescription;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.ProcessDescription;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleDescription;
import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescription;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.eclipse.epf.uma.WorkOrder;
import org.eclipse.epf.uma.WorkOrderType;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductDescription;
import org.eclipse.epf.uma.WorkProductDescriptor;

import simulator.uma.dynamic.DynamicActivity;
import simulator.uma.dynamic.DynamicIteration;
import simulator.uma.dynamic.DynamicProcess;
import simulator.uma.dynamic.DynamicTaskDescriptor;

public class ObjectFactory {

    private static MethodLibraryHash hash;

    public static TaskDescriptor newTaskDescriptor(String name, BreakdownElement breakdownElement) {

	TaskDescriptor taskDescriptor = new TaskDescriptor();
	taskDescriptor.setName(name);
	taskDescriptor.setPresentationName(name);
	taskDescriptor.setId("_" + UUID.randomUUID());
	taskDescriptor.setSuperActivity(breakdownElement.getId());
	hash.getHashMap().put(taskDescriptor.getId(), taskDescriptor);
	return taskDescriptor;
    }

    public static Phase newPhase(String name, BreakdownElement breakdownElement) {
	Phase phase = new Phase();

	phase.setName(name);
	phase.setPresentationName(name);
	phase.setSuperActivity(breakdownElement.getId());
	phase.setId("_" + UUID.randomUUID());
	phase.setVariabilityType(VariabilityType.NA);
	hash.getHashMap().put(phase.getId(), phase);
	return phase;
    }

    public static Milestone newMilestone(String name, BreakdownElement breakdownElement) {
	Milestone milestone = new Milestone();

	milestone.setName(name);
	milestone.setPresentationName(name);
	milestone.setId("_" + UUID.randomUUID());
	milestone.setSuperActivity(breakdownElement.getId());
	hash.getHashMap().put(milestone.getId(), milestone);

	return milestone;
    }

    public static Iteration newIteration(String name, BreakdownElement breakdownElement) {
	Iteration iteration = new Iteration();

	iteration.setName(name);
	iteration.setPresentationName(name);
	iteration.setId("_" + UUID.randomUUID());
	iteration.setSuperActivity(breakdownElement.getId());
	iteration.setVariabilityType(VariabilityType.NA);
	hash.getHashMap().put(iteration.getId(), iteration);

	return iteration;
    }

    public static Activity newActivity(String name, BreakdownElement breakdownElement) {
	Activity activity = new Activity();

	activity.setName(name);
	activity.setPresentationName(name);
	activity.setSuperActivity(breakdownElement.getId());
	activity.setId("_" + UUID.randomUUID());
	activity.setVariabilityType(VariabilityType.NA);
	hash.getHashMap().put(activity.getId(), activity);

	return activity;
    }

    public static RoleDescriptor newRoleDescriptor(Role role, String superActivity) {
	RoleDescriptor roleDescriptor = new RoleDescriptor();
	if (role.getVariabilityType() == VariabilityType.NA) {
	    roleDescriptor.setBriefDescription(role.getBriefDescription());
	    roleDescriptor.setId("" + UUID.randomUUID());
	    roleDescriptor.setName(role.getName());
	    roleDescriptor.setPresentation(role.getPresentation());
	    roleDescriptor.setPresentationName(role.getPresentationName());
	    roleDescriptor.setRole(role.getId());
	    roleDescriptor.setSuperActivity(superActivity);
	}
	hash.getHashMap().put(roleDescriptor.getId(), roleDescriptor);
	return roleDescriptor;
    }

    public static WorkProductDescriptor newWorkProductDescriptor(WorkProduct workProduct, String superActivity) {
	WorkProductDescriptor workProductDescriptor = new WorkProductDescriptor();
	if (workProduct.getVariabilityType() == VariabilityType.NA) {
	    workProductDescriptor.setBriefDescription(workProduct.getBriefDescription());
	    workProductDescriptor.setId("" + UUID.randomUUID());
	    workProductDescriptor.setName(workProduct.getName());
	    workProductDescriptor.setPresentation(workProduct.getPresentation());
	    workProductDescriptor.setPresentationName(workProduct.getPresentationName());
	    workProductDescriptor.setWorkProduct(workProduct.getId());
	    workProductDescriptor.setSuperActivity(superActivity);
	}
	hash.getHashMap().put(workProductDescriptor.getId(), workProductDescriptor);
	return workProductDescriptor;
    }

    public static TaskDescriptor newTraskDescriptor(Task task, String superActivity) {

	TaskDescriptor taskDescriptor = new TaskDescriptor();
	if (task.getVariabilityType() == VariabilityType.NA) {
	    taskDescriptor.setName(task.getName());
	    taskDescriptor.setPresentation(task.getPresentation());
	    taskDescriptor.setBriefDescription(task.getBriefDescription());
	    taskDescriptor.setId("" + UUID.randomUUID());
	    taskDescriptor.setPresentationName(task.getPresentationName());
	    taskDescriptor.setSuperActivity(superActivity);
	    taskDescriptor.setTask(task.getId());
	}
	hash.getHashMap().put(taskDescriptor.getId(), taskDescriptor);

	return taskDescriptor;
    }

    public static Artifact newArtifact(ContentPackage contentPackage) {
	Artifact artifact = new Artifact();
	String name = "new_artifact";
	String presetationName = "New Artifact";
	int numberOfEqualsName = getNumberOfEqualsName(contentPackage, name);
	if (numberOfEqualsName != 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	artifact.setId("" + UUID.randomUUID());
	artifact.setName(name);
	artifact.setPresentationName(presetationName);
	artifact.setIsAbstract(false);
	artifact.setVariabilityType(VariabilityType.NA);
	ArtifactDescription artifactDescription = new ArtifactDescription();
	artifact.setPresentation(artifactDescription);
	hash.getHashMap().put(artifact.getId(), artifact);
	return artifact;
    }

    public static Deliverable newDeliverable(ContentPackage contentPackage) {
	Deliverable deliverable = new Deliverable();
	String name = "new_deliverable";
	String presetationName = "New Deliverable";
	int numberOfEqualsName = getNumberOfEqualsName(contentPackage, name);
	if (numberOfEqualsName != 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	deliverable.setId("" + UUID.randomUUID());
	deliverable.setName(name);
	deliverable.setPresentationName(presetationName);
	deliverable.setIsAbstract(false);
	deliverable.setVariabilityType(VariabilityType.NA);
	DeliverableDescription deliverableDescription = new DeliverableDescription();
	deliverable.setPresentation(deliverableDescription);
	hash.getHashMap().put(deliverable.getId(), deliverable);
	return deliverable;
    }

    public static Outcome newOutcome(ContentPackage contentPackage) {
	Outcome outcome = new Outcome();

	String name = "new_outcome";
	String presetationName = "New Outcome";
	int numberOfEqualsName = getNumberOfEqualsName(contentPackage, name);
	if (numberOfEqualsName != 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	outcome.setId("" + UUID.randomUUID());
	outcome.setName(name);
	outcome.setPresentationName(presetationName);
	outcome.setIsAbstract(false);
	outcome.setVariabilityType(VariabilityType.NA);
	WorkProductDescription outcomeDescription = new WorkProductDescription();
	outcome.setPresentation(outcomeDescription);
	hash.getHashMap().put(outcome.getId(), outcome);
	return outcome;
    }

    public static Role newRole(ContentPackage contentPackage) {

	Role role = new Role();

	String name = "new_role";
	String presetationName = "New Role";
	int numberOfEqualsName = getNumberOfEqualsName(contentPackage, name);
	if (numberOfEqualsName != 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	role.setId("" + UUID.randomUUID());
	role.setName(name);
	role.setPresentationName(presetationName);
	role.setVariabilityType(VariabilityType.NA);
	RoleDescription roleDescription = new RoleDescription();

	role.setPresentation(roleDescription);

	hash.getHashMap().put(role.getId(), role);
	return role;
    }

    public static Task newTask(ContentPackage contentPackage) {

	Task task = new Task();
	String name = "new_task";
	String presetationName = "New Task";
	int numberOfEqualsName = getNumberOfEqualsName(contentPackage, name);
	if (numberOfEqualsName > 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	task.setId("" + UUID.randomUUID());
	task.setName(name);
	task.setPresentationName(presetationName);
	task.setVariabilityType(VariabilityType.NA);
	TaskDescription taskDescription = new TaskDescription();
	task.setPresentation(taskDescription);

	hash.getHashMap().put(task.getId(), task);
	return task;
    }

    public static ProcessComponent newProcessComponent(String name, Process process) {

	ProcessComponent processComponent = new ProcessComponent();
	processComponent.setName(name);
	processComponent.setPresentationName(name);
	processComponent.setId("" + UUID.randomUUID());
	processComponent.setProcess(process);

	hash.getHashMap().put(processComponent.getId(), processComponent);
	return processComponent;
    }

    public static DynamicIteration newDynamicIteration(String name, Process breakdownElement) {
	DynamicIteration dynamicIteration = new DynamicIteration();

	dynamicIteration.setName(name);
	dynamicIteration.setPresentationName(name);
	dynamicIteration.setId("_" + UUID.randomUUID());
	dynamicIteration.setSuperActivity(breakdownElement.getId());
	dynamicIteration.setVariabilityType(VariabilityType.NA);
	return dynamicIteration;
    }

    public static DynamicActivity newDynamicActivity(String name, DynamicIteration breakdownElement) {
	DynamicActivity dynamicActivity = new DynamicActivity();

	dynamicActivity.setName(name);
	dynamicActivity.setPresentationName(name);
	dynamicActivity.setSuperActivity(breakdownElement.getId());
	dynamicActivity.setId("_" + UUID.randomUUID());
	dynamicActivity.setVariabilityType(VariabilityType.NA);
	return dynamicActivity;
    }

    public static DynamicTaskDescriptor newDynamicTaskDescriptor(String name, Activity breakdownElement) {

	DynamicTaskDescriptor taskDescriptor = new DynamicTaskDescriptor();
	taskDescriptor.setName(name);
	taskDescriptor.setPresentationName(name);
	taskDescriptor.setId("_" + UUID.randomUUID());
	taskDescriptor.setSuperActivity(breakdownElement.getId());
	return taskDescriptor;
    }

    public static DynamicProcess newDynamicProcess(String name) {
	DynamicProcess dynamicProcess = new DynamicProcess();
	dynamicProcess.setVariabilityType(VariabilityType.NA);
	dynamicProcess.setName(name);
	dynamicProcess.setPresentationName(name);
	dynamicProcess.setId("_" + UUID.randomUUID());
	return dynamicProcess;
    }

    public static DeliveryProcess newDeliveryProcess(String name) {
	DeliveryProcess process = new DeliveryProcess();
	process.setName(name);
	process.setPresentationName(name);
	process.setId("_" + UUID.randomUUID());
	process.setVariabilityType(VariabilityType.NA);
	DeliveryProcessDescription processDescription = new DeliveryProcessDescription();
	process.setPresentation(processDescription);
	hash.getHashMap().put(process.getId(), process);
	return process;
    }

    public static CapabilityPattern newCapabilityPattern(String name) {
	CapabilityPattern capabilityPattern = new CapabilityPattern();
	capabilityPattern.setName(name);
	capabilityPattern.setPresentationName(name);
	capabilityPattern.setId("" + UUID.randomUUID());
	capabilityPattern.setVariabilityType(VariabilityType.NA);
	ProcessDescription processDescription = new ProcessDescription();
	capabilityPattern.setPresentation(processDescription);
	hash.getHashMap().put(capabilityPattern.getId(), capabilityPattern);
	return capabilityPattern;
    }

    public static ContentPackage newContentPackage(MethodPlugin methodPlugin) {
	String name = "new_content_package";
	String presetationName = "New Content Package";
	int numberOfEqualsName = getNumberOfEqualsName(methodPlugin, name);
	if (numberOfEqualsName > 0) {
	    name += "_" + numberOfEqualsName;
	    presetationName += " " + numberOfEqualsName;
	}
	ContentPackage contentPackage = new ContentPackage();
	contentPackage.getContentElement();
	contentPackage.getOwnedRuleOrMethodElementProperty();
	contentPackage.getReusedPackageOrMethodPackage();
	contentPackage.setName(name);
	contentPackage.setPresentationName(presetationName);
	contentPackage.setId("_" + UUID.randomUUID());
	contentPackage.setOrderingGuide("");
	contentPackage.setBriefDescription("");
	contentPackage.setSuppressed(false);
	contentPackage.setGlobal(false);
	contentPackage.setOrderingGuide("");
	hash.getHashMap().put(contentPackage.getId(), contentPackage);
	return contentPackage;
    }

    private static int getNumberOfEqualsName(ContentPackage contentPackage, String name) {
	int number = 0;
	String newName = new String(name);
	for (NamedElement element : contentPackage.getContentElement()) {
	    if (element.getName().equals(newName)) {
		number++;
		newName = name + "_" + number;
	    }
	}
	return number;
    }

    private static int getNumberOfEqualsName(MethodPlugin methodPlugin, String name) {
	int number = 0;
	String newName = new String(name);
	for (NamedElement element : methodPlugin.getMethodPackage()) {
	    if (element.getName().equals(newName)) {
		number++;
		newName = name + "_" + number;
	    }
	}
	return number;
    }

    public static String showInputDialogForName() {
	String name;
	while (true) {
	    name = JOptionPane.showInputDialog("Insert the name:");
	    if (name == null) {
		break;
	    }
	    if (!name.isEmpty()) {
		break;
	    }
	    JOptionPane.showMessageDialog(null, "Name can't be empty");
	}
	return name;
    }

    public static WorkOrder newWorkOrder(WorkBreakdownElement element) {
	WorkOrder workOrder = new WorkOrder();
	workOrder.setId(UUID.randomUUID() + "");
	workOrder.setValue(element.getId());
	workOrder.setLinkType(WorkOrderType.FINISH_TO_START);
	workOrder.setProperties("");
	return workOrder;
    }

    public static MethodLibraryHash getHash() {
	return hash;
    }

    public static void setHash(MethodLibraryHash hash) {
	ObjectFactory.hash = hash;
    }

}
