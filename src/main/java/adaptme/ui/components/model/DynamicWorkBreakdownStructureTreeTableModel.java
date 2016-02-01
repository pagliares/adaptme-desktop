package adaptme.ui.components.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import simulator.time.WorkDayLocalDate;
import simulator.uma.dynamic.Dynamic;

public class DynamicWorkBreakdownStructureTreeTableModel extends AbstractTreeTableModel {

    private int columnCount = 5;

    public DynamicWorkBreakdownStructureTreeTableModel(Activity activity) {
	super(activity);
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
	List<Object> allElements = activity.getBreakdownElementOrRoadmap();
	for (int i = 0; i < allElements.size(); i++) {
	    if (allElements.get(i) instanceof TaskDescriptor) {
		allRootElements.add(allElements.get(i));
	    } else if (allElements.get(i) instanceof Activity) {
		allRootElements.add(allElements.get(i));
	    } else if (allElements.get(i) instanceof Phase) {
		allRootElements.add(allElements.get(i));
	    } else if (allElements.get(i) instanceof Iteration) {
		allRootElements.add(allElements.get(i));
	    } else if (allElements.get(i) instanceof Milestone) {
		allRootElements.add(allElements.get(i));
	    }
	}
	return allRootElements;
    }

    @Override
    public int getColumnCount() {
	return columnCount;
    }

    @Override
    public Object getValueAt(Object node, int column) {
	if (node instanceof Dynamic) {
	    Dynamic dynamic = (Dynamic) node;
	    switch (column) {
	    case 0:
		return dynamic.getDynamicName();
	    case 1:
		return WorkDayLocalDate.toRealDate(dynamic.getDate());
	    case 2:
		return dynamic.getLocalStartTime();
	    case 3:
		return dynamic.getLocalEndTime();
	    case 4:
		return LocalTime.of(0, 0).plusMinutes(dynamic.getDuration()).toString();
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
	    return "Date";
	case 2:
	    return "Start Time";
	case 3:
	    return "End Time";
	case 4:
	    return "Duration";
	default:
	    return super.getColumnName(column);
	}
    }

    @Override
    public Class<?> getColumnClass(int column) {
	switch (column) {
	case 0:
	case 1:
	case 2:
	case 3:
	case 4:
	    return String.class;
	default:
	    return super.getColumnClass(column);
	}
    }

    @Override
    public void setValueAt(Object value, Object node, int column) {
	if (node instanceof WorkBreakdownElement) {
	    switch (column) {
	    case 0:
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    }
	}
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
	switch (column) {
	case 0:
	case 1:
	case 2:
	case 3:
	case 4:
	    return false;
	default:
	    return true;
	}
    }

    public void setColumnCount(int columnCount) {
	this.columnCount = columnCount;
    }
}
