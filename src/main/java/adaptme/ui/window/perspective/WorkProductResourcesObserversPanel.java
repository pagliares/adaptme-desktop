package adaptme.ui.window.perspective;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import simulator.base.QueueObserverType;
import xacdml.model.generated.ActObserver;
import xacdml.model.generated.QueueObserver;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

public class WorkProductResourcesObserversPanel extends JPanel {
	
	private JLabel queueNameLabel;
	private JTextField queueNameTextField;
	private JTable tableObservers;
 	private JComboBox<QueueObserverType> observerTypeJComboBox;
 	private WorkProductResourcesBottomRightPanelTableModel observersTableModel ;
 	private TableColumnModel modeloColuna;
 	private int counter;
 	private int workProductResourcesBottomPanelNumber;
 	private int selectedRow;
 	private String workProductName;
 	
 	private ProcessContentRepository processContentRepository;

	private JButton addObserverButton;
	private JButton removeObserverButton;
 
	public WorkProductResourcesObserversPanel(int i, String workProductName, String borderTitle) {
		setBorder(new TitledBorder(null, borderTitle, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		 this.workProductResourcesBottomPanelNumber = i;
		 this.workProductName = workProductName;
		 
 		 
 		observerTypeJComboBox = new JComboBox<>();
 		observerTypeJComboBox.addItem(QueueObserverType.NONE);
		observerTypeJComboBox.addItem(QueueObserverType.LENGTH);
		observerTypeJComboBox.addItem(QueueObserverType.TIME);
		observerTypeJComboBox.addItem(QueueObserverType.STATIONARY);
		
		observersTableModel = new WorkProductResourcesBottomRightPanelTableModel();
		tableObservers = new JTable(observersTableModel);
		
		tableObservers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		configuraColunas();
		
		JPanel activeObserverTopPanel = new JPanel();
		
		queueNameLabel = new JLabel("Queue name");
		
		queueNameTextField = new JTextField();
		queueNameTextField.setEditable(false);
		
		
		// configuring a queuobserver by Default
		QueueObserver queueObserver = new QueueObserver();
 		queueObserver.setName(workProductName+ " observer " + ++counter+"");
		observersTableModel.addQueueObserver(queueObserver);
		tableObservers.changeSelection(observersTableModel.getRowCount() -1, 0, false, false);  // seleciona a primeira linha da tabela por default
		tableObservers.setValueAt(QueueObserverType.STATIONARY, observersTableModel.getRowCount()-1, 1);
				
		addObserverButton = new JButton("Add observer");
		addObserverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueueObserver queueObserver = new QueueObserver();
				queueObserver.setName(queueNameTextField.getText()+ " observer " + ++counter+"");
				observersTableModel.addQueueObserver(queueObserver);
				tableObservers.changeSelection(observersTableModel.getRowCount() -1, 0, false, false);  // seleciona a primeira linha da tabela por default
				tableObservers.setValueAt(QueueObserverType.STATIONARY, observersTableModel.getRowCount()-1, 1);
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
						tableObservers.setValueAt(QueueObserverType.STATIONARY, selectedRow-1, 1);
				}
			}
			}
		});
		GroupLayout gl_activeObserverTopPanel = new GroupLayout(activeObserverTopPanel);
		gl_activeObserverTopPanel.setHorizontalGroup(
			gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(queueNameLabel)
					.addGap(5)
					.addComponent(queueNameTextField, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
					.addGap(59)
					.addComponent(addObserverButton)
					.addGap(41)
					.addComponent(removeObserverButton))
		);
		gl_activeObserverTopPanel.setVerticalGroup(
			gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(queueNameLabel))
						.addComponent(queueNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_activeObserverTopPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(addObserverButton)
						.addComponent(removeObserverButton)))
		);
		activeObserverTopPanel.setLayout(gl_activeObserverTopPanel);
		
		JPanel activeObserverBottomPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600,100));
		scrollPane.setViewportView(tableObservers);
		tableObservers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GroupLayout gl_activeObserverBottomPanel = new GroupLayout(activeObserverBottomPanel);
		gl_activeObserverBottomPanel.setHorizontalGroup(
			gl_activeObserverBottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverBottomPanel.createSequentialGroup()
					.addGap(16)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE))
		);
		gl_activeObserverBottomPanel.setVerticalGroup(
			gl_activeObserverBottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_activeObserverBottomPanel.createSequentialGroup()
					.addGap(22)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		activeObserverBottomPanel.setLayout(gl_activeObserverBottomPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(activeObserverTopPanel, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE))
				.addComponent(activeObserverBottomPanel, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(activeObserverTopPanel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(activeObserverBottomPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);

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
	
	public List<QueueObserver> getObservers() {
		return observersTableModel.getObservers();
	}
}
