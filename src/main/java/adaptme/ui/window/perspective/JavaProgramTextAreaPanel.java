package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;


public class JavaProgramTextAreaPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private XACDMLTextAreaPanel defineXACDMLTextAreaPanel;

	public JavaProgramTextAreaPanel(XACDMLTextAreaPanel defineXACDMLTextAreaPanel) {
		
		this.defineXACDMLTextAreaPanel = defineXACDMLTextAreaPanel;
		 
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JPanel northPanel = new JPanel();
		scrollPane.setColumnHeaderView(northPanel);
			 
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton generateJavaProgramButton = new JButton("Generate experimentation program");
		generateJavaProgramButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				String s = null;
				try {
					
				defineXACDMLTextAreaPanel.setAcdIDTextField(defineXACDMLTextAreaPanel.getAcdIDTextField());
			    String xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
				Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ Stylizer xacdml_models/xacdml.xsl xacdml_models/"+xacdmlFile+".xacdml");
 				
             

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				 
				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
					textArea.append(s + "\n");
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}

			} catch (IOException e1) {
				System.out.println("exception happened - here's what I know: ");
				e1.printStackTrace();
				System.exit(-1);
			}
			}
		});
		
		northPanel.add(generateJavaProgramButton);	 

		JButton saveJavaProgramButton = new JButton("Save experimentation program");
		saveJavaProgramButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				String fileContent = textArea.getText();

				saveXML("DynamicExperimentationProgramProxy", fileContent);
				JOptionPane.showMessageDialog(getPanel(), "DynamicExperimentationProgramProxy.java successfully saved to folder src/main/java/adaptme/");
				IDynamicExperimentationProgramProxy epp = DynamicExperimentationProgramProxyFactory.newInstance();  // Reload the class
		
 			}
		});
		panel.add(saveJavaProgramButton);
	}	
	
	public void saveXML(String fileName, String fileContent) {
		
		File f = new File("src/main/java/adaptme/" + fileName + ".java");


		try (FileWriter fw = new FileWriter(f)) {
			
			fw.write(fileContent.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	 
	public JPanel getPanel() {
		return this;
	}
	
}
