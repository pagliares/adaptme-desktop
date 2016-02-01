package adaptme.ui.components.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import adaptme.base.MethodLibraryHash;

public class TeamAllocationTreeTableModel extends AbstractTreeTableModel {

	private MethodLibraryHash hash;

	private BreakdownElement getRootElements(Activity activity, int index) {
		List<Object> allRootElements = buildList(activity);
		return (BreakdownElement) allRootElements.get(index);
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
			if (object instanceof RoleDescriptor) {
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

	public TeamAllocationTreeTableModel(Process process, MethodLibraryHash hash) {
		super(process);
		this.hash = hash;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof BreakdownElement) {
			BreakdownElement breakdownElement = (BreakdownElement) node;
			if (breakdownElement instanceof RoleDescriptor) {
				System.out.println("");
			}
			switch (column) {
			case 0:
				String name;
				if (breakdownElement instanceof Activity) {
					Activity activity = (Activity) breakdownElement;
					if (activity.getVariabilityType() != VariabilityType.NA) {
						name = ((Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement()))
								.getPresentationName();
					} else {
						name = breakdownElement.getPresentationName();
					}
				} else {
					name = breakdownElement.getPresentationName();
				}
				return name;
			case 1:
				if (breakdownElement instanceof Activity) {
					return modelInfo((Activity) breakdownElement);
				} else {
					return "";
				}
			case 2:
				// TODO indentificar a localiza��o do Team.
				return "";
			case 3:
				return breakdownElement.getClass().getSimpleName();
			case 4:
				return breakdownElement.isIsPlanned();
			case 5:
				return breakdownElement.isHasMultipleOccurrences();
			case 6:
				return breakdownElement.isIsOptional();
			}
		}
		return null;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof CapabilityPattern) {
			return getRootElements((CapabilityPattern) parent, index);
		} else if (parent instanceof Process) {
			return ((Process) parent).getBreakdownElementOrRoadmap().get(index);
		} else if (parent instanceof BreakdownElement) {
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
			return "Model Info";
		case 2:
			return "Team";
		case 3:
			return "Type";
		case 4:
			return "Planned";
		case 5:
			return "Multiple Occurrences";
		case 6:
			return "Optional";
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
		case 6:
			return Boolean.class;
		default:
			return super.getColumnClass(column);
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
}
