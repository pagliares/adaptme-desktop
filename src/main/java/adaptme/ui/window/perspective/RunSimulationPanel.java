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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RunSimulationPanel extends JPanel {
	private DefineXACDMLTextAreaPanel defineXACDMLTextAreaPanel;
	private String xacdmlFile;

	public RunSimulationPanel(DefineXACDMLTextAreaPanel defineXACDMLTextAreaPanel) {
		
		this.defineXACDMLTextAreaPanel = defineXACDMLTextAreaPanel;
		this.xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JPanel northPanel = new JPanel();
		scrollPane.setColumnHeaderView(northPanel);
			 
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton generateJavaProgramButton = new JButton("Run Java program");
		generateJavaProgramButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
//				if(classNameTextField.getText().trim().isEmpty()){
//					JOptionPane.showMessageDialog(getPanel(), "The class name is required");
//					return; 
//				}
				String s = null;
				try {
					
//			    xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
//				Process p1 = Runtime.getRuntime().exec("javac -encoding ISO-8859-1 -cp xacdml_models/ xacdml_models/mancu.java");
//				Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ mancu");
				
				Process p1 = Runtime.getRuntime().exec("javac -encoding ISO-8859-1 -cp xacdml_models/ xacdml_models/HBC.java");
				Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ HBC");

				String result = readXMLWithFileReader("HBC.out");
				textArea.append(result);

//				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				//
				// read the output from the command
//				System.out.println("Here is the standard output of the command:\n");
//				while ((s = stdInput.readLine()) != null) {
//					System.out.println(s);
//					textArea.append(s + "\n");
//				}

				// read any errors from the attempted command
//				System.out.println("Here is the standard error of the command (if any):\n");
//				while ((s = stdError.readLine()) != null) {
//					System.out.println(s);
//				}

//				System.exit(0);
			} catch (IOException e1) {
				System.out.println("exception happened - here's what I know: ");
				e1.printStackTrace();
				System.exit(-1);
			}
			}
		});
		
		northPanel.add(generateJavaProgramButton);
	}	
	
	public void saveXML(String fileName, String fileContent) {

		File f = new File("./xacdml_models/" + fileName + ".java");

		try (FileWriter fw = new FileWriter(f)) {
			
			fw.write(fileContent.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String readXMLWithFileReader(String fileName) throws IOException {

//		fileName = DATADIR + fileName;
		StringBuilder builder = new StringBuilder();
		FileReader reader = new FileReader(new File(fileName));

		int content;
		while ((content = reader.read()) != -1) {
			builder.append((char) content);
		}
		reader.close();

		String response = builder.toString();
		System.out.println(response);
		return response;

	}

	 
	public JPanel getPanel() {
		return this;
	}
	
}
