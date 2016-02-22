package adaptme.ui.window.perspective;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.spem.ProcessContentRepository;
import simulator.base.ActiveObserverType;
import xacdml.model.generated.ActObserver;

public class RoleResourcesBottomPanel extends JPanel {
	
	private JLabel queueNameLabel;
	private JTextField queueNameTextField;
	private JTable tableObservers;
	private JTable tableRole;
	private JComboBox<ActiveObserverType> observerTypeJComboBox;
	private RoleResourcesBottomPanelTableModel observersTableModel ;
 	private TableColumnModel modeloColuna;
 	private int counter;
 	private int roleResourcesBottomPanelNumber;
 	private int selectedRow;
 	private String roleName;
 	
 	private ProcessContentRepository processContentRepository;

	private JButton addObserverButton;
	private JButton removeObserverButton;
	private RoleResourcesPanel roleResourcesPanel;
	private TableModel roleTableModel;
	 
	public RoleResourcesBottomPanel(int i, String roleName) {
		 this.roleResourcesBottomPanelNumber = i;
		 this.roleName = roleName;
		 
 		 
 		observerTypeJComboBox = new JComboBox<>();
 		observerTypeJComboBox.addItem(ActiveObserverType.ACTIVE);
		observerTypeJComboBox.addItem(ActiveObserverType.DELAY);
		observerTypeJComboBox.addItem(ActiveObserverType.PROCESSOR);
		
		observersTableModel = new RoleResourcesBottomPanelTableModel();
		tableObservers = new JTable(observersTableModel);
		tableObservers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		configuraColunas();
		setLayout(null);
		
		JPanel activeObserverTopPanel = new JPanel();
		activeObserverTopPanel.setBounds(16, 6, 682, 106);
		add(activeObserverTopPanel);
		
		queueNameLabel = new JLabel("Queue name");
		
		queueNameTextField = new JTextField();
		 
 		 
		
		addObserverButton = new JButton("Add observer");
		addObserverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActObserver actObserver = new ActObserver();
//				actObserver.setName(processContentRepository.getName()+ " observer " + ++counter+"");
				observersTableModel.addActObserver(actObserver);
				tableObservers.changeSelection(observersTableModel.getRowCount() -1, 0, false, false);  // seleciona a primeira linha da tabela por default
				tableObservers.setValueAt(ActiveObserverType.ACTIVE, observersTableModel.getRowCount()-1, 1);
			}
		});
		
		removeObserverButton = new JButton("Remove observer");
		removeObserverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRow = tableObservers.getSelectedRow();
				if (observersTableModel.getRowCount() > 0){ 
					observersTableModel.removeObserverAt(selectedRow);
					if (selectedRow != 0) {
						tableObservers.changeSelection(selectedRow-1, 0, false, false);  // seleciona a primeira linha da tabela por default
						tableObservers.setValueAt(ActiveObserverType.ACTIVE, selectedRow-1, 1);
				}
			}
			}
		});
		GroupLayout gl_activeObserverTopPanel = new GroupLayout(activeObserverTopPanel);
		gl_activeObserverTopPanel.setHorizontalGroup(
			gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
					.addGroup(gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(queueNameLabel)
							.addGap(5)
							.addComponent(queueNameTextField, GroupLayout.PREFERRED_SIZE, 539, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(123)
							.addComponent(addObserverButton)
							.addGap(50)
							.addComponent(removeObserverButton)))
					.addGap(47))
		);
		gl_activeObserverTopPanel.setVerticalGroup(
			gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
					.addGroup(gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(queueNameLabel))
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(queueNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(22)
					.addGroup(gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(addObserverButton)
						.addComponent(removeObserverButton)))
		);
		activeObserverTopPanel.setLayout(gl_activeObserverTopPanel);
		
		JPanel activeObserverBottomPanel = new JPanel();
		activeObserverBottomPanel.setBounds(6, 124, 692, 150);
		FlowLayout flowLayout_1 = (FlowLayout) activeObserverBottomPanel.getLayout();
		add(activeObserverBottomPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600,100));
		scrollPane.setViewportView(tableObservers);
		tableObservers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		
		activeObserverBottomPanel.add(scrollPane);

	}
	
	
public void configuraColunas() { 
		
		modeloColuna = tableObservers.getColumnModel(); 
		
		TableColumn colunaObserverType = modeloColuna.getColumn(1);
		colunaObserverType.setCellEditor(new DefaultCellEditor(observerTypeJComboBox));
		 
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tableObservers.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		 
 

		((DefaultTableCellRenderer) tableObservers.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tableObservers.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableObservers.getColumnModel().getColumn(1).setPreferredWidth(23);
	 
		
	}


	public JLabel getQueueNameLabel() {
		return queueNameLabel;
	}

	public void setQueueNameLabel(JLabel queueNameLabel) {
		this.queueNameLabel = queueNameLabel;
	}

	public JTextField getQueueNameTextField() {
		return queueNameTextField;
	}

	public void setQueueNameTextField(String queueNameTextField) {
		this.queueNameTextField.setText(queueNameTextField);
	}
}
