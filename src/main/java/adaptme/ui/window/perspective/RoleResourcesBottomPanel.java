package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import model.spem.ProcessContentRepository;
import simulator.base.ActiveObserverType;
import xacdml.model.generated.ActObserver;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class RoleResourcesBottomPanel extends JPanel {
	
	private JLabel activityLabel;
	private JTextField activityTextField;
	private JTable tableObservers;
	private JComboBox<ActiveObserverType> observerTypeJComboBox;
	private RoleResourcesBottomPanelTableModel observersTableModel ;
 	private TableColumnModel modeloColuna;
 	private int counter;
 	
 	private ProcessContentRepository processContentRepository;

	private JButton addObserverButton;
	private JButton removeObserverButton;
	private HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMapLocalView;

	 
	public RoleResourcesBottomPanel(HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMapLocalView) {
		this.hashMapLocalView = hashMapLocalView;
		this.processContentRepository = processContentRepository;
		
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
		
		activityLabel = new JLabel("Activity name");
		
		activityTextField = new JTextField();
//		activityTextField.setText(processContentRepository.getName());
		activityTextField.setColumns(10);
		
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
				int selectedRow = tableObservers.getSelectedRow();
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
							.addComponent(activityLabel)
							.addGap(5)
							.addComponent(activityTextField, GroupLayout.PREFERRED_SIZE, 539, GroupLayout.PREFERRED_SIZE))
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
							.addComponent(activityLabel))
						.addGroup(gl_activeObserverTopPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(activityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
}
