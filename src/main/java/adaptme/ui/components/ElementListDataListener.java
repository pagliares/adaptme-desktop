package adaptme.ui.components;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ElementListDataListener implements ListDataListener {

    private boolean changed = false;

    @Override
    public void intervalAdded(ListDataEvent e) {
	changed = true;
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
	changed = true;
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
	changed = true;
    }

    public boolean hasChanged() {
	return changed;
    }

}
