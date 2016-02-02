package adaptme.dynamic.gui;

import javax.swing.JPanel;

import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GenerateSimulationModelPanel {
	private JPanel panel;
	
	public GenerateSimulationModelPanel() {
		panel = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 450, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
	}

	public Component getPanel() {
		return panel;
	}
}
