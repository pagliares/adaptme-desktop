package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunSimulationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private XACDMLTextAreaPanel defineXACDMLTextAreaPanel;
//	private String xacdmlFile;

	public RunSimulationPanel(XACDMLTextAreaPanel defineXACDMLTextAreaPanel) {

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
		
		JButton btnCompileExperimentationProgram = new JButton("Compile experimentation program");
		JButton runJavaProgramButton = new JButton("Run experimentation program");
		
		btnCompileExperimentationProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				String s = null;
				String xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
				try {

					Process p1 = Runtime.getRuntime().exec("javac -encoding ISO-8859-1 -cp xacdml_models/ xacdml_models/"+xacdmlFile+".java");
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p1.getInputStream()));
					BufferedReader stdError = new BufferedReader(new InputStreamReader(p1.getErrorStream()));
					
					// read the output from the command
					textArea.append("Compiling..:\n");
					while ((s = stdInput.readLine()) != null) {
						System.out.println(s);
						textArea.append(s + "\n");
					}
					
					textArea.append("Success !");
					
				 
					// read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					while ((s = stdError.readLine()) != null) {
						System.out.println(s);
						textArea.append(s + "\n");
					}
					
					runJavaProgramButton.setEnabled(true);
					 
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e1) {
					textArea.append(e1.getMessage());
					System.out.println("exception happened - here's what I know: ");
					e1.printStackTrace();
					
					System.exit(-1);
				}
			}
			
		});

	
		runJavaProgramButton.setEnabled(false);
		runJavaProgramButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				String s = null;
				String xacdmlFile = defineXACDMLTextAreaPanel.getAcdIDTextField().getText();
				try {
					Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ "+ "xacdml_models/"+xacdmlFile);
        
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					 
					// read the output from the command
					textArea.setText("");
					textArea.append("Interpreting..:\n");
					while ((s = stdInput.readLine()) != null) {
						System.out.println(s);
						textArea.append(s + "\n");
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String result = readXMLWithFileReader(xacdmlFile+".out");
					textArea.append(result);

					// read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					while ((s = stdError.readLine()) != null) {
						System.out.println(s);
						textArea.append(s + "\n");
					}
					
 
				} catch (IOException e1) {
					textArea.append(e1.getMessage());
					System.out.println("exception happened - here's what I know: ");
					e1.printStackTrace();
					
					System.exit(-1);
				}
			}
		});
		
		
		northPanel.add(btnCompileExperimentationProgram);

		northPanel.add(runJavaProgramButton);
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
