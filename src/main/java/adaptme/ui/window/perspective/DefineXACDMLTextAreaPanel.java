package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import simulator.base.WorkProduct;

public class DefineXACDMLTextAreaPanel extends JPanel {
    
	private AlternativeOfProcessPanel alternativeOfProcessPanel;
	private WorkProductResourcesPanel workProdutResourcesPanel;
	private RoleResourcesPanel roleResourcePanel;
	
	public DefineXACDMLTextAreaPanel(AlternativeOfProcessPanel alternativeOfProcessPanel, WorkProductResourcesPanel workProdutResourcesPanel, 
			RoleResourcesPanel roleResourcePanel) {
		this.alternativeOfProcessPanel = alternativeOfProcessPanel;
		this.workProdutResourcesPanel = workProdutResourcesPanel;
		this.roleResourcePanel = roleResourcePanel;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	
		JButton btnGenerateXacdml = new JButton("Generate XACDML");
		btnGenerateXacdml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				textArea.append(workProdutResourcesPanel.getWorkProducts().toString());
				
			}
		});
		add(btnGenerateXacdml, BorderLayout.NORTH);
		
		JButton btnGenerateJavaProgram = new JButton("Generate Java Program");
		add(btnGenerateJavaProgram, BorderLayout.SOUTH);
	}
	
	public JPanel getPanel() {
		return this;
	}

}
