package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

 
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;

import simulator.base.Role;
import simulator.base.Task;
import simulator.base.WorkProduct;
import xacdml.model.XACDMLBuilderFacade;
import xacdml.model.generated.Acd;

public class DefineXACDMLTextAreaPanel extends JPanel {
    
	private AlternativeOfProcessPanel alternativeOfProcessPanel;
	private WorkProductResourcesPanel workProdutResourcesPanel;
	private RoleResourcesPanel roleResourcePanel;
	private XACDMLBuilderFacade xACDMLBuilderFacade;
//	private List<Task> taskList;
	private Set<String> taskList;
	
	public DefineXACDMLTextAreaPanel(AlternativeOfProcessPanel alternativeOfProcessPanel, Set<String> taskList, WorkProductResourcesPanel workProdutResourcesPanel, 
			RoleResourcesPanel roleResourcePanel) {
		this.taskList = taskList;
		this.alternativeOfProcessPanel = alternativeOfProcessPanel;
		this.workProdutResourcesPanel = workProdutResourcesPanel;
		this.roleResourcePanel = roleResourcePanel;
		
		xACDMLBuilderFacade = new XACDMLBuilderFacade();
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	
		JButton btnGenerateXacdml = new JButton("Generate XACDML");
		btnGenerateXacdml.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String acdId = "Pegar acd id do panel";
				List<Role> roles = roleResourcePanel.getRoles();
				List<WorkProduct> workProducts = workProdutResourcesPanel.getWorkProducts();
				
				String result = xACDMLBuilderFacade.buildProcess(acdId,roles, workProducts, taskList);
				textArea.append(result);
				
//				Acd acd = xACDMLBuilderFacade.buildEntities(roleResourcePanel.getRoles(), workProdutResourcesPanel.getWorkProducts());
//				acd = xACDMLBuilderFacade.buildDeadStates(acd, roleResourcePanel.getRoles(), workProdutResourcesPanel.getWorkProducts());
//				acd = xACDMLBuilderFacade.buildGenerateActivities(acd, workProdutResourcesPanel.getWorkProducts());
//				acd = xACDMLBuilderFacade.buildActivities(acd, taskList);
//				acd = xACDMLBuilderFacade.buildDestroyActivities(acd, workProdutResourcesPanel.getWorkProducts());
//				
//				Acd acd = t.buildProcess("HBC_Pagliares");
//				try {
//					String result = xACDMLBuilderFacade.persistProcessInXMLWithJAXBOnlyString(acd, "ACD");
//					textArea.append(result);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				
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
