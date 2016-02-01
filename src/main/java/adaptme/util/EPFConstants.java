package adaptme.util;

import javax.swing.ImageIcon;

/**
 * 
 * @author eugf
 */
public class EPFConstants {

    public static final String artifactText = "Artifact";
    public static final String capabilityPatternText = "Capability Pattern";
    public static final String capabilityPatternsText = "Capability Patterns";
    public static final String checklistText = "Checklist";
    public static final String conceptText = "Concept";
    public static final String contentPackageText = "Content Package";
    public static final String contentPackagesText = "Content Packages";
    public static final String customCategoryText = "Custom Category";
    public static final String customCategoriesText = "Custom Categories";
    public static final String deliverableText = "Deliverable";
    public static final String deliveryProcessText = "Delivery Process";
    public static final String deliveryProcessesText = "Delivery Processes";
    public static final String disciplineText = "Discipline";
    public static final String disciplinesText = "Disciplines";
    public static final String disciplineGroupingText = "Discipline Grouping";
    public static final String domainsText = "Domains";
    public static final String exampleText = "Example";
    public static final String guidanceText = "Guidance";
    public static final String guidelineText = "Guideline";
    public static final String methodContentText = "Method Content";
    public static final String methodPluginText = "Method Plugin";
    public static final String outcomeText = "Outcome";
    public static final String practiceText = "Practice";
    public static final String processesText = "Processes";
    public static final String processPackageText = "Process Package";
    public static final String reportText = "Report";
    public static final String reusableAssetText = "Resusable Asset";
    public static final String roadmapText = "Roadmap";
    public static final String roleSetGroupingText = "Role Set Grouping";
    public static final String roleSetText = "Role Set";
    public static final String rolesText = "Roles";
    public static final String roleText = "Role";
    public static final String standardCategoriesText = "Standard Categories";
    public static final String tasksText = "Tasks";
    public static final String taskText = "Task";
    public static final String templateText = "Template";
    public static final String termDefinitionText = "Term Definition";
    public static final String toolMentorText = "Tool";
    public static final String toolsText = "Tools";
    public static final String whitepaperText = "Whitepaper";
    public static final String workProductText = "Work Product";
    public static final String workProductKindText = "Work Product Kind";
    public static final String workProductKindsText = "Work Product Kinds";
    public static final String supportingMaterialText = "Supporting Material";
    public static final String estimatingConsiderationsText = "Estimating Considerations";
    public static final String section = "Section";

    public static final String ICONS_PKG = "/resources/icons/";

    public static final ImageIcon artifactIcon = getImage("Artifact.gif");
    public static final ImageIcon capabilityPatternIcon = getImage("CapabilityPattern.gif");
    public static final ImageIcon capabilityPatternsIcon = getImage("CapabilityPatterns.gif");
    public static final ImageIcon categoriesIcon = getImage("Categories.gif");
    public static final ImageIcon checklistIcon = getImage("Checklist.gif");
    public static final ImageIcon conceptIcon = getImage("Concept.gif");
    public static final ImageIcon contentPackagesIcon = getImage("ContentPackages.gif");
    public static final ImageIcon customCategoryIcon = getImage("CustomCategory.gif");
    public static final ImageIcon deliverableIcon = getImage("Deliverable.gif");
    public static final ImageIcon deliveryProcessesIcon = getImage("DeliveryProcesses.gif");
    public static final ImageIcon deliveryProcessIcon = getImage("DeliveryProcess.gif");
    public static final ImageIcon disciplineIcon = getImage("Discipline.gif");
    public static final ImageIcon disciplinesIcon = getImage("Disciplines.gif");
    public static final ImageIcon domainsIcon = getImage("Domains.gif");
    public static final ImageIcon exampleIcon = getImage("Example.gif");
    public static final ImageIcon guidanceIcon = getImage("Guidance.gif");
    public static final ImageIcon guidelineIcon = getImage("Guideline.gif");
    public static final ImageIcon methodContentIcon = getImage("MethodContent.gif");
    public static final ImageIcon methodPluginIcon = getImage("MethodPlugin.gif");
    public static final ImageIcon outcomeIcon = getImage("Outcome.gif");
    public static final ImageIcon practiceIcon = getImage("Practice.gif");
    public static final ImageIcon processesIcon = getImage("Processes.gif");
    public static final ImageIcon reportIcon = getImage("Report.gif");
    public static final ImageIcon reusableAssetIcon = getImage("ReusableAsset.gif");
    public static final ImageIcon roadmapIcon = getImage("Roadmap.gif");
    public static final ImageIcon roleIcon = getImage("Role.gif");
    public static final ImageIcon roleSetGroupingIcon = getImage("RoleSetGrouping.gif");
    public static final ImageIcon roleSetIcon = getImage("RoleSet.gif");
    public static final ImageIcon rolesIcon = getImage("Roles.gif");
    public static final ImageIcon taskIcon = getImage("Task.gif");
    public static final ImageIcon tasksIcon = getImage("Tasks.gif");
    public static final ImageIcon templateIcon = getImage("Template.gif");
    public static final ImageIcon termDefinitionIcon = getImage("TermDefinition.gif");
    public static final ImageIcon toolMentorIcon = getImage("ToolMentor.gif");
    public static final ImageIcon toolsIcon = getImage("Tools.gif");
    public static final ImageIcon whitepaperIcon = getImage("Whitepaper.gif");
    public static final ImageIcon workProductIcon = getImage("WorkProduct.gif");
    public static final ImageIcon workProductKindIcon = getImage("WorkProductType.gif");
    public static final ImageIcon supportingMaterialIcon = getImage("SupportingMaterial.gif");
    public static final ImageIcon estimatingConsiderationsIcon = getImage("EstimatingConsiderations.gif");
    public static final ImageIcon sectionIcon = getImage("Section.gif");

    public static final ImageIcon activityIcon = getImage("Activity.gif");
    public static final ImageIcon milestoneIcon = getImage("Milestone.gif");
    public static final ImageIcon phaseIcon = getImage("Phase.gif");
    public static final ImageIcon taskDescriptorIcon = getImage("TaskDescriptor.gif");
    public static final ImageIcon iterationIcon = getImage("Iteration.gif");
    public static final ImageIcon roleDescriptor = getImage("RoleDescriptor.gif");
    public static final ImageIcon workProductDescriptor = getImage("WorkProductDescriptor.gif");

    private static ImageIcon getImage(String name) {
	return new ImageIcon(EPFConstants.class.getResource(ICONS_PKG + "epf/" + name));
    }
}
