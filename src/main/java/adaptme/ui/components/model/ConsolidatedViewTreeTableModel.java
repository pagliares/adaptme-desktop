package adaptme.ui.components.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.eclipse.epf.uma.WorkOrder;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductDescriptor;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.modelmapper.ModelMapper;

import adaptme.base.MethodLibraryHash;
import adaptme.base.RoleDescriptorWithModelInfo;

public class ConsolidatedViewTreeTableModel extends AbstractTreeTableModel {

	private MethodLibraryHash hash;
	private List<Object> indexList;

	public ConsolidatedViewTreeTableModel(Process process, MethodLibraryHash hash) {
		super(process);
		this.hash = hash;
		indexList = new ArrayList<>();
		buildIndexList(process, indexList);
	}

	private WorkBreakdownElement getRootElements(Activity activity, int index) {
		List<Object> allRootElements = buildList(activity);
		return (WorkBreakdownElement) allRootElements.get(index);
	}

	private BreakdownElement getRootElements(TaskDescriptor taskDescriptor, int index) {
		List<Object> allRootElements = buildList(taskDescriptor);
		return (BreakdownElement) allRootElements.get(index);
	}

	private int getRootElemetnsChildCont(Activity activity) {
		List<Object> allRootElements = buildList(activity);
		return allRootElements.size();
	}

	private int getRootElemetnsChildCont(TaskDescriptor taskDescriptor) {
		List<Object> allRootElements = buildList(taskDescriptor);
		return allRootElements.size();
	}

	private List<Object> buildList(Activity activity) {
		List<Object> allRootElements = new ArrayList<>();
		List<Object> allElements;
		if (activity.getVariabilityType() == VariabilityType.NA) {
			allElements = activity.getBreakdownElementOrRoadmap();
		} else {
			Activity superActivity = (Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement());
			allElements = superActivity.getBreakdownElementOrRoadmap();
		}
		for (Object object : allElements) {
			if (object instanceof TaskDescriptor) {
				allRootElements.add(object);
			} else if (object instanceof Activity) {
				allRootElements.add(object);
			} else if (object instanceof Phase) {
				allRootElements.add(object);
			} else if (object instanceof Iteration) {
				allRootElements.add(object);
			} else if (object instanceof Milestone) {
				allRootElements.add(object);
			}
		}
		return allRootElements;
	}

	private List<Object> buildList(TaskDescriptor taskDescriptor) {
		List<Object> allRootElements = new ArrayList<>();
		List<JAXBElement<String>> list = taskDescriptor.getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
		for (JAXBElement<String> jaxbElement : list) {
			MethodElement element = (MethodElement) hash.getHashMap().get(jaxbElement.getValue());
			if (element instanceof RoleDescriptor) {
				RoleDescriptorWithModelInfo roleDescriptor = new RoleDescriptorWithModelInfo();
				roleDescriptor.setPresentationName(element.getPresentationName());
				roleDescriptor.setSuperActivity(taskDescriptor.getId());
				roleDescriptor.setId(element.getId());
				QName qName = jaxbElement.getName();
				if (qName.getLocalPart().equals("PerformedPrimarilyBy")) {
					roleDescriptor.setModelInfo("Primary Performer");
				} else if (qName.getLocalPart().equals("AdditionallyPerformedBy")) {
					roleDescriptor.setModelInfo("Additional Performer");
				}
				allRootElements.add(roleDescriptor);
			} else if (element instanceof WorkProductDescriptor) {
				WorkProductDescriptor workProductDescriptor = new WorkProductDescriptor();
				workProductDescriptor.setPresentationName(element.getPresentationName());
				workProductDescriptor.setSuperActivity(taskDescriptor.getId());
				workProductDescriptor.setId(element.getId());

				QName qName = jaxbElement.getName();
				if (qName.getLocalPart().equals("MandatoryInput")) {
					workProductDescriptor.setResponsibleRole("Mandatory Input");  // mudar esta linha caso queira que apareça apenas input
				} else if (qName.getLocalPart().equals("Output")) {
					workProductDescriptor.setResponsibleRole("Output");
				} else if (qName.getLocalPart().equals("OptionalInput")) {
					workProductDescriptor.setResponsibleRole("Optional Input");
				}

				allRootElements.add(workProductDescriptor);
			}
		}
		return allRootElements;
	}

	private void buildIndexList(WorkBreakdownElement workBreakdownElementt, List<Object> indexList) {
		if (workBreakdownElementt instanceof Activity) {
			List<Object> allElements;
			if (((Activity) workBreakdownElementt).getVariabilityType() == VariabilityType.NA) {
				allElements = ((Activity) workBreakdownElementt).getBreakdownElementOrRoadmap();
			} else {
				Activity superActivity = (Activity) hash.getHashMap()
						.get(((Activity) workBreakdownElementt).getVariabilityBasedOnElement());
				allElements = superActivity.getBreakdownElementOrRoadmap();
			}
			for (Object object : allElements) {
				if (object instanceof WorkBreakdownElement) {
					indexList.add(object);
					buildIndexList((WorkBreakdownElement) object, indexList);
				}
			}
		}
	}

	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof WorkBreakdownElement) {
			// WorkBreakdownElement workBreakdownElement =
			// (WorkBreakdownElement) node;
			switch (column) {
			case 0:
				String name;
				if (node instanceof Activity) {
					Activity activity = (Activity) node;
					if (activity.getVariabilityType() != VariabilityType.NA) {
						name = ((Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement()))
								.getPresentationName();
					} else {
						name = ((MethodElement) node).getPresentationName();
					}
				} else {
					name = ((MethodElement) node).getPresentationName();
				}
				return name;
			case 1:
				int index = indexList.indexOf(node);
				return index > -1 ? index : "";
			case 2:
				return predecessorInfo(((WorkBreakdownElement) node).getPredecessor());
			case 3:
				return null;
			case 4:
				return node.getClass().getSimpleName();
			case 5:
				return ((BreakdownElement) node).isIsPlanned();
			case 6:
				return ((WorkBreakdownElement) node).isIsRepeatable();
			case 7:
				return ((BreakdownElement) node).isHasMultipleOccurrences();
			case 8:
				return ((WorkBreakdownElement) node).isIsOngoing();
			case 9:
				return ((WorkBreakdownElement) node).isIsEventDriven();
			case 10:
				return ((BreakdownElement) node).isIsOptional();
			case 11:
				return ((BreakdownElement) node).isIsOptional();
			}
		}
		if (node instanceof WorkProductDescriptor) {
			switch (column) {
			case 3:
				if (node instanceof Activity) {
					return modelInfo((Activity) node);
				} else if (node instanceof WorkProductDescriptor) {
					return ((WorkProductDescriptor) node).getResponsibleRole();
				}
			}
		}
		if (node instanceof RoleDescriptorWithModelInfo) {
			switch (column) {
			case 3:
				return ((RoleDescriptorWithModelInfo) node).getModelInfo();
			}
		}
		return null;
	}

	private String predecessorInfo(List<WorkOrder> predecessor) {
		StringBuilder predecessorList = new StringBuilder("");
		for (WorkOrder workOrder : predecessor) {
			if (!predecessorList.toString().equals("")) {
				predecessorList.append(", ");
			}
			predecessorList.append(indexList.indexOf(hash.getHashMap().get(workOrder.getValue())));
		}
		return predecessorList.toString();
	}

	private String modelInfo(Activity activity) {
		StringBuilder sb = new StringBuilder();
		VariabilityType vt = activity.getVariabilityType();
		if (vt == VariabilityType.NA) {
			return "";
		} else if (vt == VariabilityType.EXTENDS) {
			sb.append("extends ");
		} else if (vt == VariabilityType.CONTRIBUTES) {
			sb.append("contributes ");
		} else if (vt == VariabilityType.REPLACES) {
			sb.append("replaces ");
		}

		MethodElement methodElement = (MethodElement) hash.getHashMap().get(activity.getVariabilityBasedOnElement());
		sb.append("'").append(methodElement.getName()).append("'");

		return sb.toString();
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof TaskDescriptor) {
			return getRootElements((TaskDescriptor) parent, index);
		} else if (parent instanceof Activity) {
			return getRootElements((Activity) parent, index);
		}
		return "Erro";
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof CapabilityPattern) {
			return getRootElemetnsChildCont((CapabilityPattern) parent);
		} else if (parent instanceof Activity) {
			return getRootElemetnsChildCont((Activity) parent);
		} else if (parent instanceof TaskDescriptor) {
			return getRootElemetnsChildCont((TaskDescriptor) parent);
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return 0;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Presentation Name";
		case 1:
			return "Index";
		case 2:
			return "Predecessors";
		case 3:
			return "Model Info";
		case 4:
			return "Type";
		case 5:
			return "Planned";
		case 6:
			return "Repeatable";
		case 7:
			return "Multiple Occurrences";
		case 8:
			return "Ongoing";
		case 9:
			return "Event-Driven";
		case 10:
			return "Optional";
		case 11:
			return "Team";
		case 12:
			return "Work product Type";
		default:
			return super.getColumnName(column);
		}
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return WorkBreakdownElement.class;
		case 1:
		case 2:
		case 3:
		case 4:
			return String.class;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			return Boolean.class;
		case 11:
			return String.class;
		case 12:
			return String.class;
		default:
			return super.getColumnClass(column);
		}
	}
}
