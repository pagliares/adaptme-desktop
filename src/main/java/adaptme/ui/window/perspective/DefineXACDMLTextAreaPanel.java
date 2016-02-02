package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class DefineXACDMLTextAreaPanel extends JPanel {

	 
	public DefineXACDMLTextAreaPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnGenerateXacdml = new JButton("Generate XACDML");
		add(btnGenerateXacdml, BorderLayout.NORTH);
		
		JButton btnGenerateJavaProgram = new JButton("Generate Java Program");
		add(btnGenerateJavaProgram, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

	}
	
	public JPanel getPanel() {
		return this;
	}

}
