package adaptme.ui.panel.base;

import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

public interface TabbedPanel {

    void save();

    ListDataListener getListDataListener();

    DocumentListener getDocumentListener();

    MutableTreeNode getNode();
}
