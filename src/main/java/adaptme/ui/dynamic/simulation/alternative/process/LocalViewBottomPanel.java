package adaptme.ui.dynamic.simulation.alternative.process;

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
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

public class LocalViewBottomPanel extends JPanel {
	
	private JLabel activityLabel;
	private JTextField activityTextField;
	private JTable tableObservers;
	private JComboBox<ActiveObserverType> observerTypeJComboBox;
	private ObserversTableModel observersTableModel ;
 	private TableColumnModel modeloColuna;
 	private int counter;
 	
 	private ProcessContentRepository processContentRepository;

	private JButton addObserverButton;
	private JButton removeObserverButton;

	 
	public LocalViewBottomPanel(ProcessContentRepository processContentRepository) {
		this.processContentRepository = processContentRepository;
		
		observerTypeJComboBox = new JComboBox<>();
 		observerTypeJComboBox.addItem(ActiveObserverType.ACTIVE);
		observerTypeJComboBox.addItem(ActiveObserverType.DELAY);
		observerTypeJComboBox.addItem(ActiveObserverType.PROCESSOR);
		
		observersTableModel = new ObserversTableModel();
		tableObservers = new JTable(observersTableModel);
		tableObservers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		configuraColunas();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{549, 0};
		gridBagLayout.rowHeights = new int[]{57, 150, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel activeObserverTopPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) activeObserverTopPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_activeObserverTopPanel = new GridBagConstraints();
		gbc_activeObserverTopPanel.fill = GridBagConstraints.BOTH;
		gbc_activeObserverTopPanel.insets = new Insets(0, 0, 5, 0);
		gbc_activeObserverTopPanel.gridx = 0;
		gbc_activeObserverTopPanel.gridy = 0;
		add(activeObserverTopPanel, gbc_activeObserverTopPanel);
		
		activityLabel = new JLabel("Activity name");
		activeObserverTopPanel.add(activityLabel);
		
		activityTextField = new JTextField();
		activityTextField.setText(processContentRepository.getName());
		activityTextField.setColumns(10);
		activeObserverTopPanel.add(activityTextField);
		
		addObserverButton = new JButton("Add observer");
		addObserverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActObserver actObserver = new ActObserver();
				actObserver.setName(processContentRepository.getName()+ " observer " + ++counter+"");
				observersTableModel.addActObserver(actObserver);
				tableObservers.changeSelection(observersTableModel.getRowCount() -1, 0, false, false);  // seleciona a primeira linha da tabela por default
				tableObservers.setValueAt(ActiveObserverType.ACTIVE, observersTableModel.getRowCount()-1, 1);
			}
		});
		activeObserverTopPanel.add(addObserverButton);
		
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
		activeObserverTopPanel.add(removeObserverButton);
		
		JPanel activeObserverBottomPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) activeObserverBottomPanel.getLayout();
		GridBagConstraints gbc_activeObserverBottomPanel = new GridBagConstraints();
		gbc_activeObserverBottomPanel.fill = GridBagConstraints.BOTH;
		gbc_activeObserverBottomPanel.gridx = 0;
		gbc_activeObserverBottomPanel.gridy = 1;
		add(activeObserverBottomPanel, gbc_activeObserverBottomPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,145));
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
