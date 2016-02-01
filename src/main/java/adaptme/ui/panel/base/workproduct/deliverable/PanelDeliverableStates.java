package adaptme.ui.panel.base.workproduct.deliverable;

import org.eclipse.epf.uma.Deliverable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelStates;
import adaptme.ui.window.AdaptMeUI;

public class PanelDeliverableStates extends WorkProductPanelStates {

    private static final long serialVersionUID = -4389667223372137042L;

    // private Deliverable deliverable;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelDeliverableStates(Deliverable deliverable, TabbedPanel tabbedPanel, MethodLibraryHash hash,
	    AdaptMeUI owner) {
	super(deliverable, tabbedPanel, hash, owner);
	// this.deliverable = deliverable;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
    }

}
