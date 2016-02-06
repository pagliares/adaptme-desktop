package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import adaptme.ui.components.CustomFileChooser;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import adaptme.util.RestoreMe;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;

import simulator.base.Role;
import simulator.base.Task;
import simulator.base.WorkProduct;
import xacdml.model.XACDMLBuilderFacade;
import xacdml.model.generated.Acd;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class DefineXACDMLTextAreaPanel extends JPanel {

	private AlternativeOfProcessPanel alternativeOfProcessPanel;
	private WorkProductResourcesPanel workProdutResourcesPanel;
	private RoleResourcesPanel roleResourcePanel;
	private XACDMLBuilderFacade xACDMLBuilderFacade;

	private Set<String> taskList;
	private JTextField acdIDTextField;
	private JTextArea textArea;
	
	public DefineXACDMLTextAreaPanel(AlternativeOfProcessPanel alternativeOfProcessPanel, Set<String> taskList,
			WorkProductResourcesPanel workProdutResourcesPanel, RoleResourcesPanel roleResourcePanel) {
		this.taskList = taskList;
		this.alternativeOfProcessPanel = alternativeOfProcessPanel;
		this.workProdutResourcesPanel = workProdutResourcesPanel;
		this.roleResourcePanel = roleResourcePanel;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("ACD ID");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(label);
		
		acdIDTextField = new JTextField();
		acdIDTextField.setColumns(10);
		panel.add(acdIDTextField);
		
		JLabel lblNewLabel = new JLabel("                 ");
		panel.add(lblNewLabel);
		
		JButton btnGenerateXacdml = new JButton("Generate XACDML");
		btnGenerateXacdml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(acdIDTextField.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(getPanel(), "The ACD Id is required");
					return; 
				}
				List<Role> roles = roleResourcePanel.getRoles();
				List<WorkProduct> workProducts = workProdutResourcesPanel.getWorkProducts();

				String result = xACDMLBuilderFacade.buildProcess(acdIDTextField.getText(), roles, workProducts, taskList);
				textArea.append(result);
			}
		});
		panel.add(btnGenerateXacdml);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		
		JButton saveXACDMLButton = new JButton("Save XACDML");
		saveXACDMLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fileContent = textArea.getText();
				String fileName = acdIDTextField.getText();
				saveXML(fileName, fileContent);
				 
				JOptionPane.showMessageDialog(getPanel(), "File saved successfully");

			}
		});
		panel_1.add(saveXACDMLButton);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		 textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		xACDMLBuilderFacade = new XACDMLBuilderFacade();
	}

	public JPanel getPanel() {
		return this;
	}


	public void saveXML(String fileName, String fileContent) {

		File f = new File("./xacdml_models/" + fileName + ".xacdml");

		try (FileWriter fw = new FileWriter(f)) {
			
			fw.write(fileContent.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JTextField getAcdIDTextField() {
		return acdIDTextField;
	}
	
	public void setAcdIDTextField(JTextField acdIDTextField) {
		this.acdIDTextField = acdIDTextField;
	}
}
 
