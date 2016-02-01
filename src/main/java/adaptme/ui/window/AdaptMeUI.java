package adaptme.ui.window;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.tree.DefaultTreeModel;

import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;

public class AdaptMeUI {

	private JFrame frame;

	private SPEMDrivenPerspectivePanel spemDrivenPerspectivePanel;
	private JMenuBar menuBar;

	public AdaptMeUI() {
		initComponents();
	}

	private void initComponents() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1270, 720);
		frame.setLocationRelativeTo(null);

		spemDrivenPerspectivePanel = new SPEMDrivenPerspectivePanel(this);
		menuBar = new JMenuBar();
		menuBar.add(spemDrivenPerspectivePanel.getFileMenu());
		frame.setContentPane(spemDrivenPerspectivePanel.getPanel());
		frame.setTitle("AdaptMe - SPEM Driven Perspective");
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	private JMenu getFileMenu() {
		JMenu fileMenu = new JMenu("File");
		return fileMenu;
	}

	public DefaultTreeModel getTreeModel() {
		return spemDrivenPerspectivePanel.getTreeModel();
	}

}
