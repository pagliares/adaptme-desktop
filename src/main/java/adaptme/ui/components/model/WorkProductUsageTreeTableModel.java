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
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductDescriptor;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import adaptme.base.MethodLibraryHash;

public class WorkProductUsageTreeTableModel extends AbstractTreeTableModel {

	private Process process;
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
			if (object instanceof WorkProductDescriptor) {
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

	public WorkProductUsageTreeTableModel(Process process, MethodLibraryHash hash) {
		super(process);
		this.process = process;
		this.hash = hash;
	}

	@Override
	public int getColumnCount() {
		return 9;
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
				if (breakdownElement instanceof WorkProductDescriptor) {
					Process subProcess = (Process) process.getBreakdownElementOrRoadmap().get(0);
					return modelInfo((WorkProductDescriptor) breakdownElement,
							subProcess.getBreakdownElementOrRoadmap());
				} else if (breakdownElement instanceof Activity) {
					return modelInfo((Activity) breakdownElement);
				}
			case 2:
				if (breakdownElement instanceof WorkProductDescriptor) {
					WorkProductDescriptor workProductDescriptor = (WorkProductDescriptor) breakdownElement;
					return workProductDescriptor.getActivityEntryState();
				} else {
					return "";
				}
			case 3:
				if (breakdownElement instanceof WorkProductDescriptor) {
					WorkProductDescriptor workProductDescriptor = (WorkProductDescriptor) breakdownElement;
					return workProductDescriptor.getActivityExitState();
				} else {
					return "";
				}
			case 4:
				return "";
			case 5:
				return breakdownElement.getClass().getSimpleName();
			case 6:
				return breakdownElement.isIsPlanned();
			case 7:
				return breakdownElement.isHasMultipleOccurrences();
			case 8:
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
			return "Entry State";
		case 3:
			return "Exit State";
		case 4:
			return "Deliverable";
		case 5:
			return "Type";
		case 6:
			return "Planned";
		case 7:
			return "Multiple Occurrences";
		case 8:
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
		case 4:
		case 5:
			return String.class;
		case 6:
		case 7:
		case 8:
			return Boolean.class;
		default:
			return super.getColumnClass(column);
		}
	}

	private String modelInfo(WorkProductDescriptor breakdownElement, List<Object> list) {
		StringBuilder sb = new StringBuilder("");
		WorkProduct workProduct = (WorkProduct) hash.getHashMap().get(breakdownElement.getWorkProduct());
		Task task;
		String mandatoryInput = null;
		String output = null;
		String optionalInput = null;
		for (Object object : list) {
			if (object instanceof Activity) {
				Activity activity = (Activity) object;
				if (activity.getVariabilityType() != VariabilityType.NA) {
					Activity subActivity = (Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement());
					return modelInfo(breakdownElement, subActivity.getBreakdownElementOrRoadmap());
				}
			}
			if (object instanceof TaskDescriptor) {
				task = (Task) hash.getHashMap().get(((TaskDescriptor) object).getTask());
				if (task != null) {
					List<JAXBElement<String>> listjJaxbElements = task
							.getMandatoryInputOrOutputOrAdditionallyPerformedBy();
					for (JAXBElement<String> jaxbElement : listjJaxbElements) {
						if (jaxbElement.getValue().equals(workProduct.getId())) {
							QName qName = jaxbElement.getName();
							if (qName.getLocalPart().equals("MandatoryInput")) {
								mandatoryInput = "Mandatory Input";
							} else if (qName.getLocalPart().equals("Output")) {
								output = "Output";
							} else if (qName.getLocalPart().equals("OptionalInput")) {
								optionalInput = "Optional Input";
							}
						}
					}
				}
			}
		}
		if (mandatoryInput != null) {
			sb.append(mandatoryInput);
		}
		if (optionalInput != null) {
			if (sb.toString().equals("")) {
				sb.append(optionalInput);
			} else {
				sb.append(", ").append(optionalInput);
			}
		}
		if (output != null) {
			if (sb.toString().equals("")) {
				sb.append(output);
			} else {
				sb.append(", ").append(output);
			}
		}
		return sb.toString();
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
