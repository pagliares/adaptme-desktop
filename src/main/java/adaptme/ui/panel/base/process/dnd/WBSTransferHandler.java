package adaptme.ui.panel.base.process.dnd;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkProduct;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class WBSTransferHandler extends TransferHandler {

    private static final long serialVersionUID = -1241545057835823515L;
    MethodLibraryHash hash;

    public WBSTransferHandler(MethodLibraryHash hash) {
	this.hash = hash;
    }

    @Override
    public boolean canImport(TransferSupport support) {
	support.setShowDropLocation(true);
	if (support.isDataFlavorSupported(BreakdownElementTransferable.BREAKDOWN_ELEMENT_FLAVOR)) {
	    try {
		JXTreeTable treeTable = (JXTreeTable) support.getComponent();
		Point dropPoint = support.getDropLocation().getDropPoint();
		TreePath dropPath = treeTable.getPathForLocation(dropPoint.x, dropPoint.y);

		if (dropPath == null) {
		    return false;
		}

		javax.swing.JTable.DropLocation dropLocation = (javax.swing.JTable.DropLocation) support
			.getDropLocation();
		if (!dropLocation.isInsertRow()) {
		    return false;
		}

		Transferable transferable = support.getTransferable();
		TreePath movingPath = ((BreakdownElementTransferable) transferable
			.getTransferData(BreakdownElementTransferable.BREAKDOWN_ELEMENT_FLAVOR)).getTreePath();
		if (dropPath == movingPath) {
		    return false;
		}
		if (dropPath.getParentPath().getLastPathComponent() == movingPath.getParentPath()
			.getLastPathComponent()) {
		    return true;
		}
	    } catch (UnsupportedFlavorException | IOException e) {
		e.printStackTrace();
	    }
	} else if (support.isDataFlavorSupported(TaskTransferable.TASK_FLAVOR)) {
	    if (support.getComponent() instanceof JXTreeTable) {

		JXTreeTable treeTable = (JXTreeTable) support.getComponent();
		Point dropPoint = support.getDropLocation().getDropPoint();
		TreePath dropPath = treeTable.getPathForLocation(dropPoint.x, dropPoint.y);

		if (dropPath == null) {
		    return false;
		}

		Object object = dropPath.getLastPathComponent();
		Object parrent = dropPath.getParentPath().getLastPathComponent();

		if (object instanceof CapabilityPattern) {
		    return false;
		}

		if (object instanceof Activity) {
		    Activity activity = (Activity) object;
		    return checkVariabilityType(activity);
		}

		if (object instanceof Milestone) {
		    Activity activity = (Activity) parrent;
		    return checkVariabilityType(activity);
		}

		if (object instanceof TaskDescriptor) {
		    Activity activity = (Activity) parrent;
		    return checkVariabilityType(activity);
		}
		return true;
	    } else {
		return false;
	    }

	}
	return false;
    }

    private boolean checkVariabilityType(Activity activity) {
	if (activity.getVariabilityType() != VariabilityType.NA) {
	    return false;
	}
	return true;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
	if (c instanceof JXTreeTable) {
	    JXTreeTable treeTable = (JXTreeTable) c;
	    return new BreakdownElementTransferable(treeTable.getPathForRow(treeTable.getSelectedRow()));
	} else if (c instanceof JTree) {
	    JTree tree = (JTree) c;
	    Object object = ((ElementWrapper) ((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent())
		    .getUserObject()).getElement();
	    if (object instanceof Task) {
		return new TaskTransferable((Task) object);
	    } else {
		return null;
	    }

	} else {
	    return null;
	}
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {

    }

    @Override
    public int getSourceActions(JComponent c) {
	return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport support) {
	try {
	    Transferable transferable = support.getTransferable();
	    if (transferable.isDataFlavorSupported(BreakdownElementTransferable.BREAKDOWN_ELEMENT_FLAVOR)) {
		JXTreeTable treeTable = (JXTreeTable) support.getComponent();
		Point dropPoint = support.getDropLocation().getDropPoint();
		TreePath dropPath = treeTable.getPathForLocation(dropPoint.x, dropPoint.y);
		BreakdownElement dropElement = (BreakdownElement) dropPath.getLastPathComponent();
		TreePath movingPath = ((BreakdownElementTransferable) transferable
			.getTransferData(BreakdownElementTransferable.BREAKDOWN_ELEMENT_FLAVOR)).getTreePath();
		BreakdownElement movingElement = (BreakdownElement) movingPath.getLastPathComponent();
		Activity parent = (Activity) dropPath.getParentPath().getLastPathComponent();
		List<Object> breakdownElement = parent.getBreakdownElementOrRoadmap();
		int index = breakdownElement.indexOf(dropElement);
		breakdownElement.remove(movingElement);
		breakdownElement.add(index, movingElement);
		treeTable.updateUI();
		((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
		treeTable.expandAll();
		treeTable.setColumnSelectionInterval(index, index);
		return true;
	    } else if (transferable.isDataFlavorSupported(TaskTransferable.TASK_FLAVOR)) {
		JXTreeTable treeTable = (JXTreeTable) support.getComponent();
		Point dropPoint = support.getDropLocation().getDropPoint();
		TreePath dropPath = treeTable.getPathForLocation(dropPoint.x, dropPoint.y);
		Activity dropElement = (Activity) dropPath.getLastPathComponent();
		List<Object> dropElementBreakdownElement = dropElement.getBreakdownElementOrRoadmap();
		Task task = ((TaskTransferable) transferable.getTransferData(TaskTransferable.TASK_FLAVOR)).getTask();

		Activity parent = (Activity) dropPath.getParentPath().getLastPathComponent();
		List<Object> parentBreakdownElement = parent.getBreakdownElementOrRoadmap();
		int index = parentBreakdownElement.indexOf(dropElement);

		TaskDescriptor taskDescriptor;
		if (task.getVariabilityType() != VariabilityType.NA && hash != null) {
		    Task rootTask = (Task) hash.getHashMap().get(task.getVariabilityBasedOnElement());
		    taskDescriptor = ObjectFactory.newTraskDescriptor(rootTask, dropElement.getId());
		} else {
		    taskDescriptor = ObjectFactory.newTraskDescriptor(task, dropElement.getId());
		}

		javax.swing.JTable.DropLocation dropLocation = (javax.swing.JTable.DropLocation) support
			.getDropLocation();
		TreePath treePathTop = treeTable.getPathForRow(dropLocation.getRow());
		TreePath treePathBottom = treeTable.getPathForLocation(dropLocation.getDropPoint().x,
			dropLocation.getDropPoint().y);
		if (treePathTop != treePathBottom) {
		    index++;
		}
		if (dropLocation.isInsertRow()) {
		    parentBreakdownElement.add(index, taskDescriptor);
		} else {
		    if (dropElement instanceof Activity) {
			Activity activity = dropElement;
			activity.getBreakdownElementOrRoadmap().add(taskDescriptor);
		    }
		}
		for (String elementID : task.getPerformedBy()) {
		    Element element = hash.getHashMap().get(elementID);
		    if (element instanceof Role) {
			dropElementBreakdownElement
				.add(ObjectFactory.newRoleDescriptor((Role) element, dropElement.getId()));
		    }
		}
		for (JAXBElement<String> jaxbElement : task.getMandatoryInputOrOutputOrAdditionallyPerformedBy()) {
		    Element element = hash.getHashMap().get(jaxbElement.getValue());
		    if (element instanceof WorkProduct) {
			dropElementBreakdownElement.add(
				ObjectFactory.newWorkProductDescriptor((WorkProduct) element, dropElement.getId()));
		    }
		}
		for (JAXBElement<String> jaxbElement : task.getMandatoryInputOrOutputOrAdditionallyPerformedBy()) {
		    Element element = hash.getHashMap().get(jaxbElement.getValue());
		    if (element instanceof Role) {
			dropElementBreakdownElement
				.add(ObjectFactory.newRoleDescriptor((Role) element, dropElement.getId()));
		    }
		}
		treeTable.updateUI();
		((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
		treeTable.expandAll();
		treeTable.setColumnSelectionInterval(index, index);
		return true;
	    }

	} catch (UnsupportedFlavorException | IOException e) {
	    e.printStackTrace();
	}
	return false;
    }
}
