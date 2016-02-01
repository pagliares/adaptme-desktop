package adaptme.ui.components;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CustomFileChooser extends JFileChooser {

    private static final long serialVersionUID = 6357259843651646344L;

    @Override
    public void approveSelection() {
	if (getDialogType() == SAVE_DIALOG) {
	    File selectedFile = getSelectedFile();
	    if ((selectedFile != null) && selectedFile.exists()) {
		int response = JOptionPane.showConfirmDialog(this,
			"The file " + selectedFile.getName()
				+ " already exists. Do you want to replace the existing file?",
			"Ovewrite file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (response != JOptionPane.YES_OPTION) {
		    setSelectedFile(null);
		    return;
		}
	    }
	}

	super.approveSelection();
    }
}
