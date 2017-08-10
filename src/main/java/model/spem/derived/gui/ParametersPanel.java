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
import model.spem.derived.ExponentialParameters;
import model.spem.derived.GammaParameters;
import model.spem.derived.LogNormalParameters;
import model.spem.derived.NegativeExponentialParameters;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;
import model.spem.derived.WeibullParameters;

public class ParametersPanel {
	private JPanel panel;
	private Parameters bestFitDistribution;
	
	private JTextField titleText;
	private JLabel label;

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
				setLabel(new JLabel(WordUtils.capitalizeFully(labelText.toString())));
				getLabel().setPreferredSize(new Dimension(115, 24));
				panel.add(getLabel(), constraints);
				constraints.fill = GridBagConstraints.NONE;
				constraints.gridx = 5;

				setTitleText(new JTextField("480"));   
				getTitleText().setName(name);
				// start

				if (parameters instanceof ConstantParameters) {
					
					ConstantParameters constantParameters = (ConstantParameters)parameters;
					constantParameters.setValue(Double.parseDouble(getTitleText().getText()));
		 			 
				} else if (parameters instanceof UniformParameters) {
					
					UniformParameters UniformParameters = (UniformParameters)parameters;
					if (getTitleText().getName().equals("high")) {
						UniformParameters.setHigh(Double.parseDouble(getTitleText().getText()));
					} else {
						UniformParameters.setLow(Double.parseDouble(getTitleText().getText()));
					}
					
				} else if (parameters instanceof NegativeExponentialParameters) {
					
					NegativeExponentialParameters negativeExponential = (NegativeExponentialParameters)parameters;
					negativeExponential.setAverage(Double.parseDouble(getTitleText().getText()));
					 
				} else if (parameters instanceof NormalParameters) {
					
					NormalParameters normalParameters = (NormalParameters)parameters;
					
					if (getTitleText().getName().equals("average")) {
						normalParameters.setMean(Double.parseDouble(getTitleText().getText()));
					} else {
						normalParameters.setStandardDeviation(Double.parseDouble(getTitleText().getText()));
					}

				} else if (parameters instanceof PoissonParameters) {
					
					PoissonParameters poissonParameters = (PoissonParameters)parameters;
					poissonParameters.setMean(Double.parseDouble(getTitleText().getText()));
	 			} 
				
				else if (parameters instanceof LogNormalParameters) {
					
					LogNormalParameters logNormalParameters = (LogNormalParameters)parameters;
					if (getTitleText().getName().equals("scale")) {
						logNormalParameters.setScale(Double.parseDouble(getTitleText().getText()));
					} else {
						logNormalParameters.setShape(Double.parseDouble(getTitleText().getText()));
 					}
	 			}	
				
				else if (parameters instanceof WeibullParameters) {	
					WeibullParameters weibullParameters = (WeibullParameters)parameters;
					if (getTitleText().getName().equals("scale")) {
						weibullParameters.setScale(Double.parseDouble(getTitleText().getText()));
					} else {
						weibullParameters.setShape(Double.parseDouble(getTitleText().getText()));
 					}
	 			}
				
				else if (parameters instanceof GammaParameters) {	
					GammaParameters gammaParameters = (GammaParameters)parameters;
					if (getTitleText().getName().equals("scale")) {
						gammaParameters.setScale(Double.parseDouble(getTitleText().getText()));
					} else {
						gammaParameters.setShape(Double.parseDouble(getTitleText().getText()));
 					}
	 			}
				
				else if (parameters instanceof ExponentialParameters) {	
					ExponentialParameters exponentialParameters = (ExponentialParameters)parameters;
						exponentialParameters.setMean(Double.parseDouble(getTitleText().getText()));
	 			}
				
				getTitleText().addFocusListener(focusListener);
				
				getTitleText().setPreferredSize(new Dimension(58, 28));
				panel.add(getTitleText(), constraints);
				
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

	public JTextField getTitleText() {
		return titleText;
	}

	public void setTitleText(JTextField titleText) {
		this.titleText = titleText;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}


}
