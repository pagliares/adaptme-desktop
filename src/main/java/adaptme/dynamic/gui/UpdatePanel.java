package adaptme.dynamic.gui;

import javax.swing.JComponent;

public interface UpdatePanel {
    void updateContent();

    JComponent getPanel();
}
