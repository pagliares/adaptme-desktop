package adaptme.ui.panel.base.process.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.tree.TreePath;

public class BreakdownElementTransferable implements Transferable, Serializable {

    private static final long serialVersionUID = -4585736746805612046L;

    public final static DataFlavor BREAKDOWN_ELEMENT_FLAVOR = new DataFlavor(
	    DataFlavor.javaJVMLocalObjectMimeType
		    + "; class=adaptme.ui.panel.base.process.dnd.BreakdownElementTransferable",
	    "BreakdownElementTransferable");
    public static DataFlavor[] flavors = { BreakdownElementTransferable.BREAKDOWN_ELEMENT_FLAVOR };

    TreePath treePath;

    public BreakdownElementTransferable(TreePath selectionPath) {
	this.treePath = selectionPath;
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
	return BreakdownElementTransferable.flavors;
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

    public TreePath getTreePath() {
	return treePath;
    }

}
