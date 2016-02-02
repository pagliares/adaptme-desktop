package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class JavaProgramTextAreaPanel extends JPanel {

	 
	public JavaProgramTextAreaPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

	}
	
	public JPanel getPanel() {
		return this;
	}

}
