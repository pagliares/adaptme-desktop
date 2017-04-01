package adaptme.ui.dynamic.simulation.alternative.process;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;
import model.spem.util.ProcessContentType;

public class ExtendedXACDMLAttributesPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public ExtendedXACDMLAttributesPanel() {
		setBorder(null);
		
		JLabel lblBehaviourAtThe = new JLabel("Behaviour at the end of an iteration");
		
		JLabel lblExtendedXacdmlAttributes = new JLabel("Extended XACDML attributes:");
		lblExtendedXacdmlAttributes.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		JComboBox comboBox_3 = new JComboBox();
		
		JLabel label_1 = new JLabel("SPEM type");
		
		JComboBox<ProcessContentType> comboBox_4 = new JComboBox<ProcessContentType>();
		
		JLabel label_2 = new JLabel("Timebox");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel label_3 = new JLabel("Processing quantity");
		
		JComboBox comboBox = new JComboBox();
		
		JLabel label_4 = new JLabel("Parent");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel label_5 = new JLabel("Dependency type");
		
		JComboBox comboBox_1 = new JComboBox();
		
		JLabel label_6 = new JLabel("Condition to process");
		
		JComboBox comboBox_2 = new JComboBox();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblExtendedXacdmlAttributes, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(label_1)
					.addGap(12)
					.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addGap(123)
					.addComponent(label_2)
					.addGap(26)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(label_3)
					.addGap(12)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(61)
					.addComponent(label_4)
					.addGap(12)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(109)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(139)
							.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBehaviourAtThe, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(233)
							.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(lblExtendedXacdmlAttributes, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(label_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(label_2))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(label_3))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(label_4))
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(label_5))
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(label_6))
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblBehaviourAtThe))
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		setLayout(groupLayout);

	}
}
