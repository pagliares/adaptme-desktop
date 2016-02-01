package adaptme.dynamic.gui.task;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import adaptme.dynamic.gui.UpdatePanel;

public class TaskPanel implements UpdatePanel {

	private JPanel panel;
	private JScrollPane scrollPane;
	private List<UpdatePanel> updatePanels;

	public TaskPanel() {
		panel = new JPanel();
		scrollPane = new JScrollPane();
		updatePanels = new ArrayList<>();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(panel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel.setSize(100, 200);
	}

	public void addPanel(UpdatePanel comp) {
		panel.add(comp.getPanel());
		updatePanels.add(comp);
	}

	@Override
	public void updateContent() {
		for (UpdatePanel updatePanel : updatePanels) {
			updatePanel.updateContent();
		}
	}

	@Override
	public JComponent getPanel() {
		return scrollPane;
	}

}
