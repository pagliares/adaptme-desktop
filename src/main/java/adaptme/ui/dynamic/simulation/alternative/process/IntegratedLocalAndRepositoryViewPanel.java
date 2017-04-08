package adaptme.ui.dynamic.simulation.alternative.process;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import model.spem.ProcessContentRepository;

public class IntegratedLocalAndRepositoryViewPanel extends JPanel {

	private String title;
	private ProcessContentRepository processContentRepository;
	private JSplitPane splitPane;
	private JPanel outerPanel;
	private LocalViewPanel localViewPanel;
	
	
	public IntegratedLocalAndRepositoryViewPanel(String title, ProcessContentRepository processContentRepository) {
		this.setName(title);
		this.processContentRepository = processContentRepository;
		splitPane = new JSplitPane()
		{
			private final int location = 520;

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
		splitPane.setEnabled( false );


		localViewPanel = new LocalViewPanel(processContentRepository);
		
		JPanel panel = localViewPanel.getPanel();
//		JPanel panel = new JPanel();
		localViewPanel.setSessionTitle(processContentRepository.getName());
		 
		panel.setBorder(new TitledBorder(null, "Local view", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JComponent panel_1 = new RepositoryViewPanel(processContentRepository).getPanel();
		
 		
//		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Repository view", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(panel_1);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}


	public LocalViewPanel getLocalViewPanel() {
		return localViewPanel;
	}


	 

}
