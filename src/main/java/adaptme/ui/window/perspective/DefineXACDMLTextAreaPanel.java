package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import simulator.base.WorkProduct;

public class DefineXACDMLTextAreaPanel extends JPanel {
    
	List<WorkProduct> workProducts;
	 
	public DefineXACDMLTextAreaPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnGenerateXacdml = new JButton("Generate XACDML");
		btnGenerateXacdml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(btnGenerateXacdml, BorderLayout.NORTH);
		
		JButton btnGenerateJavaProgram = new JButton("Generate Java Program");
		add(btnGenerateJavaProgram, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	//	textArea.append(workProducts.toString());

	}
	
	public void setWorkProducts(List<WorkProduct> workProducts) {
		this.workProducts = workProducts;
	}
	
	public JPanel getPanel() {
		return this;
	}

}
