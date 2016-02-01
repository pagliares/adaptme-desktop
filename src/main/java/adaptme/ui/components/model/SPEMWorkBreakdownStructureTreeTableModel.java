package adaptme.ui.components.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.eclipse.epf.uma.WorkOrder;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import adaptme.base.MethodLibraryHash;
import adaptme.base.ObjectFactory;

public class SPEMWorkBreakdownStructureTreeTableModel extends AbstractTreeTableModel {

	private MethodLibraryHash hash;
	private List<WorkBreakdownElement> indexList;
	private Activity activity;

	private int columnCount = 6;

	public SPEMWorkBreakdownStructureTreeTableModel(Activity activity, MethodLibraryHash hash) {
		super(activity);
		this.activity = activity;
		this.hash = hash;
		indexList = new ArrayList<>();
		buildIndexList(this.activity, indexList);
	}

	public void remove(Object object) {
		List<Object> list = activity.getBreakdownElementOrRoadmap();
		for (int i = 0; i < list.size(); i++) {
			if (object == list.get(i)) {
				list.remove(i);
				return;
			}
			if (list.get(i) instanceof Activity) {
				removeChild(object, (Activity) list.get(i));
			} else if (list.get(i) instanceof Phase) {
				removeChild(object, (Activity) list.get(i));
			} else if (list.get(i) instanceof Iteration) {
				removeChild(object, (Activity) list.get(i));
			}
		}
		// update();
	}

	private void removeChild(Object object, Activity activity) {
		List<Object> list = activity.getBreakdownElementOrRoadmap();
		for (int i = 0; i < list.size(); i++) {
			if (object == list.get(i)) {
				list.remove(i);
			}
			if (list.get(i) instanceof Activity) {
				removeChild(object, (Activity) list.get(i));
			} else if (list.get(i) instanceof Phase) {
				removeChild(object, (Activity) list.get(i));
			} else if (list.get(i) instanceof Iteration) {
				removeChild(object, (Activity) list.get(i));
			}
		}
	}

	public void update() {
		indexList.clear();
		buildIndexList(activity, indexList);
	}

	private WorkBreakdownElement getRootElements(Activity activity, int index) {
		List<Object> allRootElements = buildList(activity);
		return (WorkBreakdownElement) allRootElements.get(index);
	}

	private int getRootElemetnsChildCont(Activity activity) {
		List<Object> allRootElements = buildList(activity);
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

	private void buildIndexList(WorkBreakdownElement workBreakdownElementt, List<WorkBreakdownElement> indexList2) {
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
					indexList2.add((WorkBreakdownElement) object);
					buildIndexList((WorkBreakdownElement) object, indexList2);
				}
			}
		}
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof WorkBreakdownElement) {
			WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) node;
			switch (column) {
			case 0:
				String name;
				if (workBreakdownElement instanceof Activity) {
					Activity activity = (Activity) workBreakdownElement;
					if (activity.getVariabilityType() != VariabilityType.NA) {
						name = ((Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement()))
								.getPresentationName();
					} else {
						name = workBreakdownElement.getPresentationName();
					}
				} else {
					name = workBreakdownElement.getPresentationName();
				}
				return name;
			case 1:
				return indexList.indexOf(node);
			case 2:
				return predecessorInfo(workBreakdownElement.getPredecessor());
			// case 3:
			// if (workBreakdownElement instanceof Activity) {
			// return modelInfo((Activity) workBreakdownElement);
			// } else {
			// return "";
			// }
			case 3:
				return workBreakdownElement.getClass().getSimpleName();
			// case 5:
			// return workBreakdownElement.isIsPlanned();
			case 4:
				return workBreakdownElement.isIsRepeatable();
			case 5:
				return workBreakdownElement.isHasMultipleOccurrences();
			// case 8:
			// return workBreakdownElement.isIsOngoing();
			// case 9:
			// return workBreakdownElement.isIsEventDriven();
			// case 10:
			// return workBreakdownElement.isIsOptional();
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

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof CapabilityPattern) {
			return getRootElements((CapabilityPattern) parent, index);
		} else if (parent instanceof Process) {
			return ((Process) parent).getBreakdownElementOrRoadmap().get(index);
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
		// case 3:
		// return "Model Info";
		case 3:
			return "Type";
		// case 5:
		// return "Planned";
		case 4:
			return "Repeatable";
		case 5:
			return "Multiple Occurrences";
		// case 8:
		// return "Ongoing";
		// case 9:
		// return "Event-Driven";
		// case 10:
		// return "Optional";
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
			return String.class;
		case 4:
		case 5:
			return Boolean.class;
		// case 6:
		// case 7:
		// case 8:
		// case 9:
		// case 10:
		default:
			return super.getColumnClass(column);
		}
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		if (node instanceof WorkBreakdownElement) {
			WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) node;
			switch (column) {
			case 0:
				workBreakdownElement.setPresentationName((String) value);
				break;
			case 1:
			case 2:
				processPredecessor((String) value, workBreakdownElement);
				break;
			case 3:
			case 4:
				break;
			case 5:
				workBreakdownElement.setIsPlanned((Boolean) value);
				break;
			case 6:
				workBreakdownElement.setIsRepeatable((Boolean) value);
				break;
			case 7:
				workBreakdownElement.setHasMultipleOccurrences((Boolean) value);
				break;
			case 8:
				workBreakdownElement.setIsOngoing((Boolean) value);
				break;
			case 9:
				workBreakdownElement.setIsEventDriven((Boolean) value);
				break;
			case 10:
				workBreakdownElement.setIsOptional((Boolean) value);
				break;
			}
		}
	}

	private void processPredecessor(String predecessors, WorkBreakdownElement element) {
		List<WorkOrder> predecessorsList = element.getPredecessor();
		predecessorsList.clear();
		if (predecessors.isEmpty()) {
			return;
		}
		predecessors = predecessors.replaceAll(" ", "");
		String[] predecessorsIndex = predecessors.split(",");
		int myIndex = indexList.indexOf(element);
		for (String index : predecessorsIndex) {
			if (Integer.parseInt(index) == myIndex) {
				predecessorsList.clear();
				return;
			}
			WorkOrder workOrder = ObjectFactory.newWorkOrder(
					(WorkBreakdownElement) hash.getHashMap().get(indexList.get(Integer.parseInt(index)).getId()));
			predecessorsList.add(workOrder);
		}
		System.out.println();
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		switch (column) {
		case 0:
			return true;
		case 1:
			return false;
		case 2:
			return true;
		case 3:
		case 4:
			return false;
		default:
			return true;
		}
	}

	private String modelInfo(Activity activity) {
		StringBuilder sb = new StringBuilder();
		VariabilityType vt = activity.getVariabilityType();
		if (vt == VariabilityType.NA) {
			return "";
		} else if (vt == VariabilityType.EXTENDS) {
			sb.append("extrends ");
		} else if (vt == VariabilityType.CONTRIBUTES) {
			sb.append("contributes ");
		} else if (vt == VariabilityType.REPLACES) {
			sb.append("replaces ");
		}

		MethodElement methodElement = (MethodElement) hash.getHashMap().get(activity.getVariabilityBasedOnElement());
		sb.append("'").append(methodElement.getName()).append("'");

		return sb.toString();
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
}
