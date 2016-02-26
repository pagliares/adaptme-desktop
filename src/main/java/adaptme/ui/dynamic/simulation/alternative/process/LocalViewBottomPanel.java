package adaptme.ui.dynamic.simulation.alternative.process;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
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

import model.spem.ProcessContentRepository;
import simulator.base.ActiveObserverType;
import xacdml.model.generated.ActObserver;

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
		gridBagLayout.rowHeights = new int[]{86, 206, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel activeObserverTopPanel = new JPanel();
		GridBagConstraints gbc_activeObserverTopPanel = new GridBagConstraints();
		gbc_activeObserverTopPanel.fill = GridBagConstraints.BOTH;
		gbc_activeObserverTopPanel.insets = new Insets(0, 0, 5, 0);
		gbc_activeObserverTopPanel.gridx = 0;
		gbc_activeObserverTopPanel.gridy = 0;
		add(activeObserverTopPanel, gbc_activeObserverTopPanel);
		GridBagLayout gbl_activeObserverTopPanel = new GridBagLayout();
		gbl_activeObserverTopPanel.columnWidths = new int[]{86, 155, 39, 225, 0};
		gbl_activeObserverTopPanel.rowHeights = new int[]{28, 29, 0};
		gbl_activeObserverTopPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_activeObserverTopPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		activeObserverTopPanel.setLayout(gbl_activeObserverTopPanel);
		
		activityLabel = new JLabel("Activity name");
		GridBagConstraints gbc_activityLabel = new GridBagConstraints();
		gbc_activityLabel.anchor = GridBagConstraints.WEST;
		gbc_activityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_activityLabel.gridx = 0;
		gbc_activityLabel.gridy = 0;
		activeObserverTopPanel.add(activityLabel, gbc_activityLabel);
		
		activityTextField = new JTextField();
		activityTextField.setText(processContentRepository.getName());
		activityTextField.setColumns(10);
		GridBagConstraints gbc_activityTextField = new GridBagConstraints();
		gbc_activityTextField.anchor = GridBagConstraints.NORTH;
		gbc_activityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_activityTextField.insets = new Insets(0, 0, 5, 0);
		gbc_activityTextField.gridwidth = 3;
		gbc_activityTextField.gridx = 1;
		gbc_activityTextField.gridy = 0;
		activeObserverTopPanel.add(activityTextField, gbc_activityTextField);
		
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
		GridBagConstraints gbc_addObserverButton = new GridBagConstraints();
		gbc_addObserverButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_addObserverButton.insets = new Insets(0, 0, 0, 5);
		gbc_addObserverButton.gridx = 1;
		gbc_addObserverButton.gridy = 1;
		activeObserverTopPanel.add(addObserverButton, gbc_addObserverButton);
		GridBagConstraints gbc_removeObserverButton = new GridBagConstraints();
		gbc_removeObserverButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_removeObserverButton.gridx = 3;
		gbc_removeObserverButton.gridy = 1;
		activeObserverTopPanel.add(removeObserverButton, gbc_removeObserverButton);
		
		JPanel activeObserverBottomPanel = new JPanel();
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

		((DefaultTableCellRenderer) tableObservers.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tableObservers.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableObservers.getColumnModel().getColumn(1).setPreferredWidth(23);
	}
}
