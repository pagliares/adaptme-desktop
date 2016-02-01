package simulator.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DeveloperFrame extends JFrame {

    private static final long serialVersionUID = -5090025285311654730L;
    private JPanel contentPane;

    public DeveloperFrame(PanelDeveloperEditor panelDeveloperEditor) {
	contentPane = panelDeveloperEditor;
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 574, 298);
	setContentPane(contentPane);
    }

}
