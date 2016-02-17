package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RunSimulationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private XACDMLTextAreaPanel defineXACDMLTextAreaPanel;
	private String xacdmlFile;

	public RunSimulationPanel(XACDMLTextAreaPanel defineXACDMLTextAreaPanel) {

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

				String s = null;
				try {

					Process p1 = Runtime.getRuntime().exec("javac -encoding ISO-8859-1 -cp xacdml_models/ xacdml_models/HBC.java");
					Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ HBC");

					String result = readXMLWithFileReader("HBC.out");
					textArea.append(result);


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
