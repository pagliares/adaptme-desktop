package adaptme.ui.window.perspective;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import simulator.base.WorkProduct;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;

public class ExperimentationPanel extends JPanel {
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private TableColumnModel modeloColuna;
	private JComboBox<VariableType> comboBoxVariableType = new JComboBox<>();
	private JTextField textField_3;

	/**
	 * Create the panel.
	 */
	public ExperimentationPanel() {
		setBorder(new TitledBorder(null, "Experimentation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Run-in period. Welch's method (non-terminating systems)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(32, 316, 630, 69);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblWindowFrame = new JLabel("Time window");
		lblWindowFrame.setBounds(20, 33, 89, 16);
		panel_1.add(lblWindowFrame);
		
		textField_2 = new JTextField();
		textField_2.setBounds(122, 27, 75, 28);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblRuninTime = new JLabel("Run-in time");
		lblRuninTime.setBounds(300, 33, 89, 16);
		panel_1.add(lblRuninTime);
		
		textField_3 = new JTextField();
		textField_3.setBounds(389, 27, 69, 28);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Line chart for run-in period (Welch's method)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(32, 389, 630, 295);
		add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/pagliares/Dropbox/projetosEclipse/workspaceHirata/Adaptme_Experimentation/src/main/java/LineChart.png"));
		lblNewLabel.setBounds(37, 23, 530, 266);
		panel_3.add(lblNewLabel);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Run control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(32, 692, 630, 69);
		add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNumberOfReplications = new JLabel("Number of replications");
		lblNumberOfReplications.setBounds(6, 29, 145, 16);
		panel_4.add(lblNumberOfReplications);
		
		textField_1 = new JTextField();
		textField_1.setBounds(163, 23, 66, 28);
		panel_4.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSimulate = new JButton("Simulate");
		btnSimulate.setBounds(256, 24, 98, 29);
		panel_4.add(btnSimulate);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "System type", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setBounds(32, 249, 630, 61);
		add(panel_6);
		panel_6.setLayout(null);
		
		JCheckBox chckbxStopOnB = new JCheckBox("Non-terminating");
		chckbxStopOnB.setBounds(18, 23, 165, 23);
		panel_6.add(chckbxStopOnB);
		
		JCheckBox chckbxStopOnEnd = new JCheckBox("Terminating");
		chckbxStopOnEnd.setBounds(435, 23, 189, 23);
		panel_6.add(chckbxStopOnEnd);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Project ending condition", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.setBounds(32, 177, 630, 69);
		add(panel_7);
		panel_7.setLayout(null);
		
		JLabel lblDurationTime = new JLabel("Duration time");
		lblDurationTime.setBounds(21, 33, 87, 16);
		panel_7.add(lblDurationTime);
		
		textField = new JTextField();
		textField.setBounds(118, 27, 74, 28);
		panel_7.add(textField);
		textField.setColumns(10);
		
		JLabel lblWorkProductFinishes = new JLabel("No more work product");
		lblWorkProductFinishes.setBounds(292, 31, 165, 16);
		panel_7.add(lblWorkProductFinishes);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("User story");
		comboBox.addItem("Methods");
		comboBox.addItem("Lines of code");
		comboBox.addItem("Classes");
		comboBox.addItem("Bug");
		
		
		
		comboBox.setBounds(450, 27, 156, 27);
		panel_7.add(comboBox);
		
		comboBoxVariableType.addItem(VariableType.INDEPENDENT);
		comboBoxVariableType.addItem(VariableType.DEPENDENT);
		comboBoxVariableType.addItem(VariableType.INTERMEDIATE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 22, 630, 143);
		add(scrollPane);
		
		table = new JTable();
		
		
		table.setModel(new WorkProductTableModelExperimentation(createWorkProducts()));
		configuraColunas();
//		List<WorkProduct> list = createWorkProducts();
//		for (int i =0; i< list.size(); i++) {
//		    WorkProduct wp = list.get(i);
//			table.setValueAt(wp.getName(), i, 0);
//		}
		scrollPane.setViewportView(table);

	}
	
	public List<WorkProduct> createWorkProducts() {
		List<WorkProduct> list = new ArrayList<>();
		WorkProduct wp1 = new WorkProduct();
		wp1.setName("User story");
		list.add(wp1);
		
		WorkProduct wp2 = new WorkProduct();
		wp2.setName("Methods");
		list.add(wp2);
		
		WorkProduct wp3 = new WorkProduct();
		wp3.setName("Lines of code");
		list.add(wp3);
		
		WorkProduct wp4 = new WorkProduct();
		wp4.setName("Classes");
		list.add(wp4);
		
		WorkProduct wp5 = new WorkProduct();
		wp5.setName("Bug");
		list.add(wp5);
		return list;
		
	}
	
	
public void configuraColunas() { 
		
		modeloColuna = table.getColumnModel();

		TableColumn colunaQueueType = modeloColuna.getColumn(1);
		colunaQueueType.setCellEditor(new DefaultCellEditor(comboBoxVariableType));
	
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
 		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		 
		 


		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(JLabel.CENTER);

	 
		 
	}

}
