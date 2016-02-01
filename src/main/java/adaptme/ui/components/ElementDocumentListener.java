package adaptme.ui.components;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ElementDocumentListener implements DocumentListener {

    private boolean changed = false;

    public boolean hasChanged() {
	return changed;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
	changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
	changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
	changed = true;
    }

}
