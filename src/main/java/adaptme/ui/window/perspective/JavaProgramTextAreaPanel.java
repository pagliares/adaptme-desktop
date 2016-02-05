package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;

public class JavaProgramTextAreaPanel extends JPanel {

	public JavaProgramTextAreaPanel() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JPanel northPanel = new JPanel();
		scrollPane.setColumnHeaderView(northPanel);

		JButton generateJavaProgramButton = new JButton("Generate Java Program");
		generateJavaProgramButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = null;
				try {
//				Process p = Runtime.getRuntime().exec("java -cp src/ hello.Hello");
				Process p = Runtime.getRuntime().exec("java -cp xacdml_models/ Stylizer xacdml_models/xacdml.xsl xacdml_models/HBC_Pagliares.xacdml");


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
	}	
	 
	public JPanel getPanel() {
		return this;
	}
	
}
