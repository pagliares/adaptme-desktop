package model.spem.derived.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.text.WordUtils;

import model.spem.derived.Parameters;

public class ParametersPanel {
    private JPanel panel;

    public ParametersPanel(Parameters parameters) {
	Field[] fields = parameters.getClass().getDeclaredFields();

	panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	int gridy = 0;
	double weightx = 1;
	double weighty = 1;
	for (Field field : fields) {
	    String name = field.getName();
	    StringBuilder labelText = new StringBuilder();
	    for (String w : name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
		labelText.append(w.toLowerCase()).append(" ");
	    }
	    GridBagConstraints constraints = new GridBagConstraints();
	    // constraints.insets = new Insets(2, 0, 0, 0);
	    constraints.anchor = GridBagConstraints.FIRST_LINE_START;
	    // constraints.anchor = GridBagConstraints.PAGE_START;
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weighty = weighty;
	    constraints.weightx = weightx;
	    constraints.gridx = 0;
	    constraints.gridy = gridy;
	    // constraints.gridwidth = 5;
	    JLabel label = new JLabel(WordUtils.capitalizeFully(labelText.toString()));
	    label.setPreferredSize(new Dimension(115, 24));
	    panel.add(label, constraints);
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.gridx = 5;
	    JTextField titleText = new JTextField("0");
	    titleText.setPreferredSize(new Dimension(58, 28));
	    panel.add(titleText, constraints);
	    gridy += 1;
	    weightx = 0.8;
	    weighty = 0.8;
	}
    }

    public JPanel getPanel() {
	return panel;
    }
}
