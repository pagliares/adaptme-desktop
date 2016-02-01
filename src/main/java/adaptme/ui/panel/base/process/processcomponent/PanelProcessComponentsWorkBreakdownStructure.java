package adaptme.ui.panel.base.process.processcomponent;

import org.eclipse.epf.uma.Process;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.process.PanelSPEMWorkBreakdownStructure;

public class PanelProcessComponentsWorkBreakdownStructure extends PanelSPEMWorkBreakdownStructure {

    private static final long serialVersionUID = -3925720347270181921L;

    public PanelProcessComponentsWorkBreakdownStructure(Process process, MethodLibraryHash hash) {
	super(process, hash);
    }

    public void save() {
	super.save();
    }
}
