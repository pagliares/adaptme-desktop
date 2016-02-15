package adaptme.ui.window.perspective;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;

public class ProbabilityDistributionInnerPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBox;
	private JScrollPane scrollPane;
	private JLabel panelTitleLabel;
	private JLabel selectedDemandWorkProductLabel;
	private Parameters parameters;
	
	public ProbabilityDistributionInnerPanel(int i) { 
		 
		this.setName("panel.:" + i);
		this.setBorder(new TitledBorder(null, "Probability distribution parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
 		JLabel label = new JLabel("Best fit probability distribution");
		
 		comboBox  = new JComboBox<String>();
  		setDistribution(BestFitDistribution.getList());
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		panelTitleLabel = new JLabel("Generate activity for demand work product :");
		
		selectedDemandWorkProductLabel = new JLabel("");
		GroupLayout gl_probabilityDistributionsPanel = new GroupLayout(this);
		gl_probabilityDistributionsPanel.setHorizontalGroup(
			gl_probabilityDistributionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
							.addComponent(panelTitleLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(selectedDemandWorkProductLabel)))
					.addContainerGap())
		);
		gl_probabilityDistributionsPanel.setVerticalGroup(
			gl_probabilityDistributionsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(panelTitleLabel)
						.addComponent(selectedDemandWorkProductLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(16))
		);
		this.setLayout(gl_probabilityDistributionsPanel);
 		
		comboBox.addItemListener(e -> {
		    String s = (String) comboBox.getSelectedItem();
		    parameters = Parameters.createParameter(BestFitDistribution.getDistributionByName(s));
		    scrollPane.setViewportView(new ParametersPanel(parameters).getPanel());
		    scrollPane.revalidate();
		    scrollPane.repaint();
		});
	}
	
	public void setDistribution(List<String> list) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
			comboBox.setModel(model);
	 }

	public JLabel getSelectedDemandWorkProductLabel() {
		return selectedDemandWorkProductLabel;
	}

	public void setSelectedDemandWorkProductLabel(JLabel selectedDemandWorkProductLabel) {
		this.selectedDemandWorkProductLabel = selectedDemandWorkProductLabel;
	}

	public JLabel getPanelTitleLabel() {
		return panelTitleLabel;
	}

	public void setPanelTitleLabel(JLabel panelTitleLabel) {
		this.panelTitleLabel = panelTitleLabel;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

}
