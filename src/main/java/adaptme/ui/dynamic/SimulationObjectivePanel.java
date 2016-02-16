package adaptme.ui.dynamic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import adaptme.base.MethodLibraryWrapper;
import adaptme.ui.window.AdaptMeUI;
import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import model.spem.ProcessRepository;

public class SimulationObjectivePanel {
    private JPanel panel;
    private JLabel lblTheObjective;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JButton btnNextCalibrateSimualation;
 
    public SimulationObjectivePanel(JTabbedPane tabbedPane, AlternativeOfProcessPanel alternativeOfProcessPanel) {
	panel = new JPanel();

	lblTheObjective = new JLabel("Simulation objective");

	scrollPane = new JScrollPane();

	btnNextCalibrateSimualation = new JButton("Next: 2. identification of alternative of process");
	btnNextCalibrateSimualation.addActionListener(new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(textArea.getText().trim().isEmpty()){
				JOptionPane.showMessageDialog(panel, "The objective field is required");
				return; 
			}
			SPEMDrivenPerspectivePanel.processRepository.setSimulationObjective(textArea.getText());
			tabbedPane.addTab("2. Identification of alternative of process", null, alternativeOfProcessPanel.getPanel(), null);
			tabbedPane.setSelectedIndex(1);
		}
	});
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup().addContainerGap()
			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 573,
							Short.MAX_VALUE)
						.addComponent(lblTheObjective))
					.addContainerGap())
			.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
				.addComponent(btnNextCalibrateSimualation).addGap(33)))));
	gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup().addGap(23).addComponent(lblTheObjective)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
			.addComponent(btnNextCalibrateSimualation).addGap(21)));

	textArea = new JTextArea();
	scrollPane.setViewportView(textArea);
	panel.setLayout(gl_panel);
    }

    public Component getPanel() {
	return panel;
    }
}
