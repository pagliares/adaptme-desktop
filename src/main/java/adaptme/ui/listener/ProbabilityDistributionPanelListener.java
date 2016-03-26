package adaptme.ui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import model.spem.ProcessRepository;
import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;

public class ProbabilityDistributionPanelListener implements FocusListener {
	
	private Parameters distributionParameters;  
 
	 @Override
	    public void focusLost(FocusEvent e) { 	    
 
	    	    JTextField textField = (JTextField) e.getSource();
	    		if (distributionParameters instanceof ConstantParameters) {
					
					ConstantParameters constantParameters = (ConstantParameters)distributionParameters;
					constantParameters.setValue(Double.parseDouble(textField.getText()));
		 			 
				} else if (distributionParameters instanceof UniformParameters) {
					
					UniformParameters UniformParameters = (UniformParameters)distributionParameters;
					if (textField.getName().equals("high")) {
						UniformParameters.setHigh(Double.parseDouble(textField.getText()));
					} else {
						UniformParameters.setLow(Double.parseDouble(textField.getText()));
					}
					
				

				} else if (distributionParameters instanceof NegativeExponential) {
					
					NegativeExponential negativeExponential = (NegativeExponential)distributionParameters;
					negativeExponential.setAverage(Double.parseDouble(textField.getText()));
					 
	 	 			
				} else if (distributionParameters instanceof NormalParameters) {
					
					NormalParameters normalParameters = (NormalParameters)distributionParameters;
					
					if (textField.getName().equals("average")) {
						normalParameters.setMean(Double.parseDouble(textField.getText()));
					} else {
						normalParameters.setStandardDeviation(Double.parseDouble(textField.getText()));
					}


				} else if (distributionParameters instanceof PoissonParameters) {
					
					PoissonParameters poissonParameters = (PoissonParameters)distributionParameters;
					poissonParameters.setMean(Double.parseDouble(textField.getText()));
	 			}
	    	
//		repositoryViewPanel.setMessagem("");
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	    	 // verificar depois a necessidade
	    }

		public void setParameters(Parameters parameters) {
			this.distributionParameters = parameters;
		}
	

}
