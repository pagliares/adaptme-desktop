package adaptme.base;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Checklist;
import org.eclipse.epf.uma.Concept;
import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.ContentElement;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.Discipline;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.EstimationConsiderations;
import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.Guideline;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.Practice;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.ProcessPackage;
import org.eclipse.epf.uma.Report;
import org.eclipse.epf.uma.ReusableAsset;
import org.eclipse.epf.uma.Roadmap;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleSet;
import org.eclipse.epf.uma.SupportingMaterial;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.Template;
import org.eclipse.epf.uma.TermDefinition;
import org.eclipse.epf.uma.Tool;
import org.eclipse.epf.uma.ToolMentor;
import org.eclipse.epf.uma.Whitepaper;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.util.EPFConstants;

public class TreeNodeFactory {
    private final MethodLibrary library;
    private final MethodLibraryHash hash;

    public TreeNodeFactory(MethodLibraryWrapper wrapper) {
	this.library = wrapper.getMethodLibrary();
	this.hash = wrapper.getMethodLibraryHash();
    }

    public MutableTreeNode buildTree() {
	MutableTreeNode rootItem = new DefaultMutableTreeNode(
		(Element) new ElementWrapper(library, library.getPresentationName(), null));

	for (MethodPlugin methodPlugin : library.getMethodPlugin()) {
	    ((DefaultMutableTreeNode) rootItem).add(buildTree(methodPlugin));
	}

	return rootItem;
    }

    private MutableTreeNode buildTree(MethodPlugin methodPlugin) {
	MutableTreeNode methodPluginItems = new DefaultMutableTreeNode(
		new ElementWrapper(methodPlugin, methodPlugin.getName(), EPFConstants.methodPluginIcon));

	MutableTreeNode methodContentItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.methodContentText, EPFConstants.methodContentIcon));
	MutableTreeNode processItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.processesText, EPFConstants.processesIcon));

	((DefaultMutableTreeNode) methodPluginItems).add(methodContentItems);
	((DefaultMutableTreeNode) methodPluginItems).add(processItems);

	MutableTreeNode contentPackageItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.contentPackagesText, EPFConstants.contentPackagesIcon));
	((DefaultMutableTreeNode) methodContentItems).add(contentPackageItems);
	MutableTreeNode customCategoryItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.customCategoriesText, EPFConstants.customCategoryIcon));

	MutableTreeNode capabilityPatternItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.capabilityPatternsText, EPFConstants.capabilityPatternsIcon));
	((DefaultMutableTreeNode) processItems).add(capabilityPatternItems);

	MutableTreeNode deliveryProcessItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.deliveryProcessesText, EPFConstants.deliveryProcessesIcon));
	((DefaultMutableTreeNode) processItems).add(deliveryProcessItems);

	for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
	    if (methodPackage instanceof ContentCategoryPackage) {
		((DefaultMutableTreeNode) methodContentItems).add(buildTree((ContentCategoryPackage) methodPackage));
		ArrayList<CustomCategory> listCustomCategory = new ArrayList<>();
		for (ContentCategory contentCategory : ((ContentCategoryPackage) methodPackage).getContentCategory()) {
		    if (contentCategory instanceof CustomCategory) {
			listCustomCategory.add((CustomCategory) contentCategory);
		    }
		}

		ArrayList<CustomCategory> clone = new ArrayList<>();
		for (CustomCategory customCategory : listCustomCategory) {
		    clone.add(customCategory);
		}
		for (CustomCategory customCategory : listCustomCategory) {
		    if (!isRoot(customCategory, listCustomCategory)) {
			clone.remove(customCategory);
		    }
		}

		buildCustomCategoryTree(clone, customCategoryItems);
		((DefaultMutableTreeNode) methodContentItems).add(customCategoryItems);
	    } else if (methodPackage instanceof ContentPackage) {
		((DefaultMutableTreeNode) contentPackageItems).add(buildTree((ContentPackage) methodPackage));
	    } else if (methodPackage instanceof ProcessPackage) {
		buildTreeProcessPackage(capabilityPatternItems, deliveryProcessItems, methodPackage);
	    }
	}
	return methodPluginItems;
    }

    private void buildTreeProcessPackage(MutableTreeNode capabilityPatternItems, MutableTreeNode deliveryProcessItems,
	    MethodPackage methodPackage) {
	if (methodPackage instanceof ProcessComponent) {
	    ProcessComponent pc = (ProcessComponent) methodPackage;
	    if (pc.getProcess() instanceof CapabilityPattern) {
		((DefaultMutableTreeNode) capabilityPatternItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(pc, pc.getName(), EPFConstants.capabilityPatternIcon)));
	    } else {
		((DefaultMutableTreeNode) deliveryProcessItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(pc, pc.getName(), EPFConstants.deliveryProcessIcon)));
	    }
	} else {
	    ProcessPackage pp = (ProcessPackage) methodPackage;
	    List<Object> list = pp.getReusedPackageOrMethodPackage();
	    MutableTreeNode subCapabilityPatternItems = new DefaultMutableTreeNode(
		    new ElementWrapper(pp, pp.getName(), EPFConstants.processesIcon));
	    for (Object object : list) {
		if (object instanceof ProcessComponent) {
		    ProcessComponent pc = (ProcessComponent) object;
		    if (pc.getProcess() instanceof DeliveryProcess) {
			((DefaultMutableTreeNode) deliveryProcessItems).add(buildTreeDeliveryProcess(pp));
			break;
		    } else if (pc.getProcess() instanceof CapabilityPattern) {
			((DefaultMutableTreeNode) capabilityPatternItems).add(buildTreeCapabilityPattern(pp));
			break;
		    }
		} else {
		    // TODO Mudar o m�todo recursivo para tratar delivery
		    // process
		    ((DefaultMutableTreeNode) capabilityPatternItems).add(subCapabilityPatternItems);
		    buildTreeProcessPackage(subCapabilityPatternItems, deliveryProcessItems, (MethodPackage) object);
		}
	    }
	}
    }

    private MutableTreeNode buildTreeCapabilityPattern(ProcessPackage processPackage) {
	List<Object> list = processPackage.getReusedPackageOrMethodPackage();
	MutableTreeNode node = new DefaultMutableTreeNode(
		new ElementWrapper(processPackage, processPackage.getName(), EPFConstants.processesIcon));
	for (Object object : list) {
	    if (object instanceof ProcessComponent) {
		ProcessComponent pc = (ProcessComponent) object;
		((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
			new ElementWrapper(pc, pc.getName(), EPFConstants.capabilityPatternIcon)));
	    } else if (object instanceof ProcessPackage) {
		ProcessPackage pc = (ProcessPackage) object;
		((DefaultMutableTreeNode) node).add(buildTreeCapabilityPattern(pc));
	    }
	}

	return node;
    }

    private MutableTreeNode buildTreeDeliveryProcess(ProcessPackage processPackage) {
	List<Object> list = processPackage.getReusedPackageOrMethodPackage();
	MutableTreeNode node = new DefaultMutableTreeNode(
		new ElementWrapper(processPackage, processPackage.getName(), EPFConstants.processesIcon));
	for (Object object : list) {
	    if (object instanceof ProcessComponent) {
		ProcessComponent pc = (ProcessComponent) object;
		((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
			new ElementWrapper(pc, pc.getName(), EPFConstants.deliveryProcessIcon)));
	    } else if (object instanceof ProcessPackage) {
		ProcessPackage pc = (ProcessPackage) object;
		((DefaultMutableTreeNode) node).add(buildTreeDeliveryProcess(pc));
	    }
	}

	return node;
    }

    public MutableTreeNode buildTree(ContentCategoryPackage contentCategoryPackage) {
	MutableTreeNode standardCategoriesItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.standardCategoriesText, EPFConstants.categoriesIcon));

	MutableTreeNode disciplinesItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.disciplinesText, EPFConstants.disciplinesIcon));
	MutableTreeNode domainsItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.domainsText, EPFConstants.domainsIcon));
	MutableTreeNode workProductKindsItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.workProductKindsText, EPFConstants.workProductKindIcon));
	MutableTreeNode roleSetsItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.roleSetText, EPFConstants.roleSetIcon));
	MutableTreeNode toolsItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.toolsText, EPFConstants.toolsIcon));

	((DefaultMutableTreeNode) standardCategoriesItems).add(disciplinesItems);
	((DefaultMutableTreeNode) standardCategoriesItems).add(domainsItems);
	((DefaultMutableTreeNode) standardCategoriesItems).add(workProductKindsItems);
	((DefaultMutableTreeNode) standardCategoriesItems).add(roleSetsItems);
	((DefaultMutableTreeNode) standardCategoriesItems).add(toolsItems);

	for (ContentCategory element : contentCategoryPackage.getContentCategory()) {
	    if (element instanceof Discipline) {
		((DefaultMutableTreeNode) disciplinesItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.disciplinesIcon)));
	    } else if (element instanceof Domain) {
		((DefaultMutableTreeNode) domainsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.domainsIcon)));
	    } else if (element instanceof WorkProductType) {
		((DefaultMutableTreeNode) workProductKindsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.workProductKindIcon)));
	    } else if (element instanceof RoleSet) {
		((DefaultMutableTreeNode) roleSetsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.roleSetIcon)));
	    } else if (element instanceof Tool) {
		((DefaultMutableTreeNode) toolsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.toolsIcon)));
	    }
	}
	return standardCategoriesItems;
    }

    private void buildCustomCategoryTree(ArrayList<CustomCategory> listCustomCategory,
	    MutableTreeNode customCategoryItems) {

	for (CustomCategory customCategory : listCustomCategory) {
	    MutableTreeNode node = new DefaultMutableTreeNode(
		    new ElementWrapper(customCategory, customCategory.getName(), EPFConstants.customCategoryIcon));
	    ((DefaultMutableTreeNode) customCategoryItems).add(node);
	    for (JAXBElement<String> jaxbeElement : customCategory.getCategorizedElementOrSubCategory()) {
		NamedElement element = (NamedElement) hash.getHashMap().get(jaxbeElement.getValue());
		if (element instanceof CustomCategory) {
		    buildCustomCategoryTreeLeaf((CustomCategory) element, node);
		} else {
		    readType(node, element);
		}
	    }
	}
    }

    private void buildCustomCategoryTreeLeaf(CustomCategory customCategory, MutableTreeNode customCategoryItems) {
	MutableTreeNode node = new DefaultMutableTreeNode(
		new ElementWrapper(customCategory, customCategory.getName(), EPFConstants.customCategoryIcon));
	((DefaultMutableTreeNode) customCategoryItems).add(node);
	for (JAXBElement<String> jaxbeElement : customCategory.getCategorizedElementOrSubCategory()) {
	    NamedElement element = (NamedElement) hash.getHashMap().get(jaxbeElement.getValue());
	    if (hash.getHashMap().get(jaxbeElement.getValue()) instanceof CustomCategory) {
		buildCustomCategoryTreeLeaf((CustomCategory) element, node);
	    } else {
		readType(node, element);
	    }
	}
    }

    public static MutableTreeNode buildTree(ContentPackage contentPackage) {

	MutableTreeNode contentPackageItems = new DefaultMutableTreeNode(
		new ElementWrapper(contentPackage, contentPackage.getName(), EPFConstants.methodContentIcon));

	MutableTreeNode roleItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.rolesText, EPFConstants.rolesIcon));
	((DefaultMutableTreeNode) contentPackageItems).add(roleItems);
	MutableTreeNode tasksItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.tasksText, EPFConstants.tasksIcon));
	((DefaultMutableTreeNode) contentPackageItems).add(tasksItems);
	MutableTreeNode workProductsItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.workProductText, EPFConstants.workProductIcon));
	((DefaultMutableTreeNode) contentPackageItems).add(workProductsItems);
	MutableTreeNode guidanceItems = new DefaultMutableTreeNode(
		new ElementWrapper(null, EPFConstants.guidanceText, EPFConstants.guidanceIcon));
	((DefaultMutableTreeNode) contentPackageItems).add(guidanceItems);

	List<ContentElement> contentElements = contentPackage.getContentElement();

	for (ContentElement element : contentElements) {
	    if (element instanceof Role) {
		((DefaultMutableTreeNode) roleItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.roleIcon)));
	    } else if (element instanceof Task) {
		((DefaultMutableTreeNode) tasksItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.taskIcon)));
	    } else if (element instanceof Artifact) {
		((DefaultMutableTreeNode) workProductsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.artifactIcon)));
	    } else if (element instanceof Deliverable) {
		((DefaultMutableTreeNode) workProductsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.deliverableIcon)));
	    } else if (element instanceof Outcome) {
		((DefaultMutableTreeNode) workProductsItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.outcomeIcon)));
	    } else if (element instanceof Checklist) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.checklistIcon)));
	    } else if (element instanceof Concept) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.conceptIcon)));
	    } else if (element instanceof Example) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.exampleIcon)));
	    } else if (element instanceof Guideline) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.guidelineIcon)));
	    } else if (element instanceof EstimationConsiderations) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.estimatingConsiderationsIcon)));
	    } else if (element instanceof Practice) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.practiceIcon)));
	    } else if (element instanceof Report) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.reportIcon)));
	    } else if (element instanceof ReusableAsset) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.reusableAssetIcon)));
	    } else if (element instanceof Roadmap) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.roadmapIcon)));
	    } else if (element instanceof SupportingMaterial) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.supportingMaterialIcon)));
	    } else if (element instanceof Template) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.templateIcon)));
	    } else if (element instanceof TermDefinition) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.termDefinitionIcon)));
	    } else if (element instanceof ToolMentor) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.toolMentorIcon)));
	    } else if (element instanceof Whitepaper) {
		((DefaultMutableTreeNode) guidanceItems).add(new DefaultMutableTreeNode(
			new ElementWrapper(element, element.getName(), EPFConstants.whitepaperIcon)));
	    }

	}
	for (Object subContentPackage : contentPackage.getReusedPackageOrMethodPackage()) {
	    if (subContentPackage instanceof ContentPackage) {
		((DefaultMutableTreeNode) contentPackageItems).add(buildTree((ContentPackage) subContentPackage));
	    }
	}
	return contentPackageItems;
    }

    private void readType(MutableTreeNode node, NamedElement element) {
	if (element instanceof Role) {
	    ((DefaultMutableTreeNode) node).add(
		    new DefaultMutableTreeNode(new ElementWrapper(element, element.getName(), EPFConstants.roleIcon)));
	} else if (element instanceof Task) {
	    ((DefaultMutableTreeNode) node).add(
		    new DefaultMutableTreeNode(new ElementWrapper(element, element.getName(), EPFConstants.taskIcon)));
	} else if (element instanceof Artifact) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.artifactIcon)));
	} else if (element instanceof Deliverable) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.deliverableIcon)));
	} else if (element instanceof Outcome) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.outcomeIcon)));
	} else if (element instanceof Checklist) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.checklistIcon)));
	} else if (element instanceof Concept) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.conceptIcon)));
	} else if (element instanceof Example) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.exampleIcon)));
	} else if (element instanceof Guideline) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.guidelineIcon)));
	} else if (element instanceof EstimationConsiderations) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.estimatingConsiderationsIcon)));
	} else if (element instanceof Practice) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.practiceIcon)));
	} else if (element instanceof Report) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.reportIcon)));
	} else if (element instanceof ReusableAsset) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.reusableAssetIcon)));
	} else if (element instanceof Roadmap) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.roadmapIcon)));
	} else if (element instanceof SupportingMaterial) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.supportingMaterialIcon)));
	} else if (element instanceof Template) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.templateIcon)));
	} else if (element instanceof TermDefinition) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.termDefinitionIcon)));
	} else if (element instanceof ToolMentor) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.toolMentorIcon)));
	} else if (element instanceof Whitepaper) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.whitepaperIcon)));
	} else if (element instanceof SupportingMaterial) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.supportingMaterialIcon)));
	} else if (element instanceof RoleSet) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.roleSetIcon)));
	} else if (element instanceof WorkProductType) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.workProductIcon)));
	} else if (element instanceof Concept) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.conceptIcon)));
	} else if (element instanceof Discipline) {
	    ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(
		    new ElementWrapper(element, element.getName(), EPFConstants.disciplinesIcon)));
	} else {
	    ((DefaultMutableTreeNode) node)
		    .add(new DefaultMutableTreeNode(new ElementWrapper(element, element.getName(), null)));
	}
    }

    private boolean isRoot(CustomCategory customCategory, ArrayList<CustomCategory> listCustomCategory) {

	for (CustomCategory customCategoryTest : listCustomCategory) {
	    for (JAXBElement<String> element : customCategoryTest.getCategorizedElementOrSubCategory()) {
		if (customCategory.equals(hash.getHashMap().get(element.getValue()))) {
		    return false;
		}
	    }
	}
	return true;
    }
}
