package model.spem.derived.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.text.WordUtils;

import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;

public class ParametersPanel {
	private JPanel panel;
	private Parameters bestFitDistribution;

	/**
	 * @wbp.parser.entryPoint
	 */
	public ParametersPanel(Parameters parameters, FocusListener focusListener) {
		 
		if (parameters == null) { // None foi selecionado no combobox
			panel = new JPanel();
		} else {
			
			Field[] fields = parameters.getClass().getDeclaredFields();

			panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			int gridy = 0;
			double weightx = 1;
			double weighty = 1;

			for (Field field : fields) {
				String name = field.getName();
				StringBuilder labelText = new StringBuilder();
				for (String w : name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {  // pega camel case para nao camel case e colocar espacos
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

				JTextField titleText = new JTextField("480");   
				titleText.setName(name);
				// start

				if (parameters instanceof ConstantParameters) {
					
					ConstantParameters constantParameters = (ConstantParameters)parameters;
					constantParameters.setValue(Double.parseDouble(titleText.getText()));
		 			 
				} else if (parameters instanceof UniformParameters) {
					
					UniformParameters UniformParameters = (UniformParameters)parameters;
					if (titleText.getName().equals("high")) {
						UniformParameters.setHigh(Double.parseDouble(titleText.getText()));
					} else {
						UniformParameters.setLow(Double.parseDouble(titleText.getText()));
					}
					
				

				} else if (parameters instanceof NegativeExponential) {
					
					NegativeExponential negativeExponential = (NegativeExponential)parameters;
					negativeExponential.setAverage(Double.parseDouble(titleText.getText()));
					 
	 	 			
				} else if (parameters instanceof NormalParameters) {
					
					NormalParameters normalParameters = (NormalParameters)parameters;
					
					if (titleText.getName().equals("average")) {
						normalParameters.setMean(Double.parseDouble(titleText.getText()));
					} else {
						normalParameters.setStandardDeviation(Double.parseDouble(titleText.getText()));
					}


				} else if (parameters instanceof PoissonParameters) {
					
					PoissonParameters poissonParameters = (PoissonParameters)parameters;
					poissonParameters.setMean(Double.parseDouble(titleText.getText()));
	 			}
				
				
				// end
				
				
				
				titleText.addFocusListener(focusListener);
				
	

				titleText.setPreferredSize(new Dimension(58, 28));
				panel.add(titleText, constraints);
				
 
				gridy += 1;
				weightx = 0.8;
				weighty = 0.8;
			}

		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel getPanel() {
		return panel;
	}


}
