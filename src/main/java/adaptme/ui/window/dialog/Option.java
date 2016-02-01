package adaptme.ui.window.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class Option {

    private static JDialog dialog;
    private static OptionType optionType;

    private Option() {
    }

    static {
	dialog = new JDialog();
	dialog.setResizable(false);
	dialog.setSize(180, 71);
	dialog.getContentPane().setLayout(null);
	JButton XPButton = new JButton("XP");
	XPButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		optionType = OptionType.XP;
		dialog.setVisible(false);
	    }
	});
	XPButton.setBounds(10, 11, 70, 23);
	dialog.getContentPane().add(XPButton);
	XPButton.setActionCommand("OK");
	dialog.getRootPane().setDefaultButton(XPButton);
	JButton ScrumButton = new JButton("Scrum");
	ScrumButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		optionType = OptionType.SCRUM;
		dialog.setVisible(false);
	    }
	});
	ScrumButton.setBounds(94, 11, 70, 23);
	dialog.getContentPane().add(ScrumButton);
    }

    public static OptionType show() {
	dialog.setTitle("Select Process Type");
	dialog.setLocationRelativeTo(null);
	dialog.setModal(true);
	dialog.setVisible(true);
	return optionType;
    }

    public enum OptionType {
	XP, SCRUM;
    }
}
