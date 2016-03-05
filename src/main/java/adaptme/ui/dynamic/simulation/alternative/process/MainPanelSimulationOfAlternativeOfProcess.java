package adaptme.ui.dynamic.simulation.alternative.process;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import adaptme.ui.dynamic.NumberButton;
import adaptme.ui.dynamic.NumberCompontent;
import adaptme.ui.dynamic.TreePanel;
import adaptme.ui.dynamic.UpdatePanel;

// neste panel, inserimos o panel integratedLocalAndRepositoryViewPanel
public class MainPanelSimulationOfAlternativeOfProcess implements UpdatePanel {
	private JPanel panel;
 	private JSplitPane splitPane;
	private JPanel panelLocal;
 	private JPanel panelMainContent;
	private JPanel panelNavigationControls;
	private ActionListener actionListener;
	private CardLayout cardLayout;
	private int count = 1;
	private JButton btnPreviews;
	private JButton btnNext;
	private int firstButtonNumber = 0;
	private int lastButtonNumber = 9;
	private int quantityOfButtons = 0;
	private List<NumberButton> numberButtons;
	private List<IntegratedLocalAndRepositoryViewPanel> integratedLocalAndRepositoryViewPanel;
	

	public MainPanelSimulationOfAlternativeOfProcess(TreePanel treePanel) {
		integratedLocalAndRepositoryViewPanel = new ArrayList<>();
		numberButtons = new ArrayList<>();
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
	 
		splitPane = new JSplitPane() 
{
			private final int location = 350;

			{
				setDividerLocation(location);
			}

			@Override
			public int getDividerLocation() {
				return location;
			}

			@Override
			public int getLastDividerLocation() {
				return location;
			}
		};
		 
		panel.add(splitPane);
		// splitPane.setDividerLocation(300);

		panelLocal = new JPanel();
		splitPane.setLeftComponent(treePanel);
		panelLocal.setLayout(new BorderLayout(0, 0));
		
		panelMainContent = new JPanel();
		splitPane.setRightComponent(panelLocal);
		
		panelLocal.add(panelMainContent, BorderLayout.CENTER);
		cardLayout = new CardLayout(0, 0);
		panelMainContent.setLayout(cardLayout);

		panelNavigationControls = new JPanel();
		panelNavigationControls.setVisible(false);
		panelLocal.add(panelNavigationControls, BorderLayout.SOUTH);
		
		actionListener = ae -> {
			Object object = ae.getSource();
			if (object instanceof NumberButton) {
				NumberCompontent numberButton = (NumberCompontent) ae.getSource();
				changePanel(numberButton);

			} else {
				if (ae.getSource().equals(btnNext)) {
					cardLayout.next(panelMainContent);
				} else if (ae.getSource().equals(btnPreviews)) {
					cardLayout.previous(panelMainContent);
				}
			}

		};
		// btnNext.addActionListener(actionListener);
		// btnPreviews.addActionListener(actionListener);
	}

	public void changePanel(NumberCompontent numberButton) {
		cardLayout.show(panelMainContent, numberButton.getKey());
		if (numberButton.getNumber() >= lastButtonNumber && numberButton.getNumber() != numberButtons.size()) {
			lastButtonNumber += 5;
			if (lastButtonNumber > numberButtons.size()) {
				lastButtonNumber = numberButtons.size();
			}
			firstButtonNumber += 5;
			int diff = lastButtonNumber - firstButtonNumber;
			if (diff < 10) {
				firstButtonNumber -= 10 - diff;
			}
			panelNavigationControls.removeAll();
			for (int i = firstButtonNumber; i < lastButtonNumber; i++) {
				panelNavigationControls.add(numberButtons.get(i));
			}
			panelNavigationControls.validate();
			panelNavigationControls.repaint();
		}
		if (numberButton.getNumber() <= firstButtonNumber && numberButton.getNumber() != 0) {
			lastButtonNumber -= 5;
			if (lastButtonNumber < 9) {
				lastButtonNumber = 9;
			}
			firstButtonNumber -= 5;
			if (firstButtonNumber < 0) {
				firstButtonNumber = 0;
			}
			panelNavigationControls.removeAll();
			for (int i = firstButtonNumber; i <= lastButtonNumber; i++) {
				panelNavigationControls.add(numberButtons.get(i));
			}
			panelNavigationControls.validate();
			panelNavigationControls.repaint();
		}
		// ((JComponent) numberButton).grabFocus();
	}

	 

	public JPanel getPanelMainContent() {
		return panelMainContent;
	}

	public void addLayoutComponent(IntegratedLocalAndRepositoryViewPanel comp, Object constraints) {
		cardLayout.addLayoutComponent(comp, constraints);
		integratedLocalAndRepositoryViewPanel.add(comp);
		NumberButton numberButton = new NumberButton();
		numberButton.setText("" + count);
		numberButton.setKey((String) constraints);
		numberButton.addActionListener(actionListener);
		numberButton.setNumber(quantityOfButtons);
		if (quantityOfButtons <= lastButtonNumber) {
			panelNavigationControls.add(numberButton);
		}
		quantityOfButtons++;
		numberButtons.add(numberButton);
		// panelNavigationControls.remove(btnNext);
		// panelNavigationControls.add(btnNext);
		count++;
	}

	 

	@Override
	public JPanel getPanel() {
		return panel;
	}

	public List<IntegratedLocalAndRepositoryViewPanel> getListIntegratedLocalAndRepositoryViewPanel() {
		return integratedLocalAndRepositoryViewPanel;
	}


	@Override
	public void updateContent() {
		 
	}

	public ActionListener getActionListener() {
		return actionListener;
	}
}
