package adaptme.ui.panel.base.process.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import org.eclipse.epf.uma.Task;

public class TaskTransferable implements Serializable, Transferable {

    private static final long serialVersionUID = -6451237035673462829L;
    public final static DataFlavor TASK_FLAVOR = new DataFlavor(
	    DataFlavor.javaJVMLocalObjectMimeType + "; class=adaptme.ui.panel.base.process.dnd.TaskTransferable",
	    "TaskTransferable");
    public static DataFlavor[] flavors = { TaskTransferable.TASK_FLAVOR };

    private Task task;

    public TaskTransferable(Task task) {
	this.task = task;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
	if (this.isDataFlavorSupported(flavor)) {
	    return this;
	} else {
	    throw new UnsupportedFlavorException(flavor);
	}
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
	return TaskTransferable.flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
	DataFlavor[] flavs = this.getTransferDataFlavors();
	for (int i = 0; i < flavs.length; i++) {
	    if (flavs[i].equals(flavor)) {
		return true;
	    }
	}
	return false;
    }

    public Task getTask() {
	return task;
    }

}
