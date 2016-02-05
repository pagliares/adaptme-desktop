package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JavaProgramTextAreaPanel extends JPanel {
	
	private JTextField classNameTextField;
	private DefineXACDMLTextAreaPanel defineXACDMLTextAreaPanel;
	private String xacdmlFile;

	public JavaProgramTextAreaPanel(DefineXACDMLTextAreaPanel defineXACDMLTextAreaPanel) {
		
		this.defineXACDMLTextAreaPanel = defineXACDMLTextAreaPanel;
		this.xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JPanel northPanel = new JPanel();
		scrollPane.setColumnHeaderView(northPanel);
		
		JLabel lblProgramName = new JLabel("Class name (no extension)");
		northPanel.add(lblProgramName);
		
		classNameTextField = new JTextField();
//		String javaFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
//		System.out.println(xacdmlFile + "teste");
		classNameTextField.setText(xacdmlFile+".java");
//		classNameTextField.setEditable(false);
		northPanel.add(classNameTextField);
		classNameTextField.setColumns(10);
			 
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton generateJavaProgramButton = new JButton("Generate");
		generateJavaProgramButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				if(classNameTextField.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(getPanel(), "The class name is required");
					return; 
				}
				String s = null;
				try {
					
			    xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
				Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ Stylizer xacdml_models/xacdml.xsl xacdml_models/"+xacdmlFile+".xacdml");


				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				//
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

//				System.exit(0);
			} catch (IOException e1) {
				System.out.println("exception happened - here's what I know: ");
				e1.printStackTrace();
				System.exit(-1);
			}
			}
		});
		
		northPanel.add(generateJavaProgramButton);	 

		
		JButton runJavaProgramButton = new JButton("Save Java program");
		runJavaProgramButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileContent = textArea.getText();
				String fileName = classNameTextField.getText();
				saveXML(fileName, fileContent);
				 
				JOptionPane.showMessageDialog(getPanel(), "File saved successfully");
			}
		});
		panel.add(runJavaProgramButton);
	}	
	
	public void saveXML(String fileName, String fileContent) {

		File f = new File("./xacdml_models/" + fileName + ".java");

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
