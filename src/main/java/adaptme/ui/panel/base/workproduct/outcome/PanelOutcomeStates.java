package adaptme.ui.panel.base.workproduct.outcome;

import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelStates;
import adaptme.ui.window.AdaptMeUI;

public class PanelOutcomeStates extends WorkProductPanelStates {

    private static final long serialVersionUID = 3667832748159571947L;

    public PanelOutcomeStates(WorkProduct workProduct, TabbedPanel tabbedPanel, MethodLibraryHash mlHash,
	    AdaptMeUI owner) {
	super(workProduct, tabbedPanel, mlHash, owner);

    }

}
