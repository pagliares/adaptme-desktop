package adaptme.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Process;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;

public class OpenToSimulatorAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private Process process;
    private AlternativeOfProcessPanel spemManagementPanel;

	private MethodLibraryHash methodLibraryHash;

    public OpenToSimulatorAction(String title, AlternativeOfProcessPanel spemManagementPanel) {
	super(title);
	this.spemManagementPanel = spemManagementPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (process != null) {
	    spemManagementPanel.openProcessToSimulate(process, methodLibraryHash);
	}
    }

    public void setProcess(Process process) {
	this.process = process;
    }

	public void setMethodLibraryHash(MethodLibraryHash methodLibraryHash) {
		this.methodLibraryHash = methodLibraryHash;		
	}

}
