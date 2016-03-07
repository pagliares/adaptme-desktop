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
	
	private Parameters parameters;
//	private ProcessRepository processRepository;
//	
//	public ProbabilityDistributionPanelListener(ProcessRepository processRepository) {
//		this.processRepository = processRepository;
//		
//	}
//	
	 @Override
	    public void focusLost(FocusEvent e) { 	    
//		        ProcessRepository pr = SPEMDrivenPerspectivePanel.processRepository;  // teste breakpoint
//	    		String s = (String) comboBoxDistribution.getSelectedItem();
//	    	    ProcessRepository p = SPEMDrivenPerspectivePanel.processRepository;
	    	    JTextField textField = (JTextField) e.getSource();
	    		if (parameters instanceof ConstantParameters) {
					
					ConstantParameters constantParameters = (ConstantParameters)parameters;
					constantParameters.setValue(Double.parseDouble(textField.getText()));
		 			 
				} else if (parameters instanceof UniformParameters) {
					
					UniformParameters UniformParameters = (UniformParameters)parameters;
					if (textField.getName().equals("high")) {
						UniformParameters.setHigh(Double.parseDouble(textField.getText()));
					} else {
						UniformParameters.setLow(Double.parseDouble(textField.getText()));
					}
					
				

				} else if (parameters instanceof NegativeExponential) {
					
					NegativeExponential negativeExponential = (NegativeExponential)parameters;
					negativeExponential.setAverage(Double.parseDouble(textField.getText()));
					 
	 	 			
				} else if (parameters instanceof NormalParameters) {
					
					NormalParameters normalParameters = (NormalParameters)parameters;
					
					if (textField.getName().equals("average")) {
						normalParameters.setMean(Double.parseDouble(textField.getText()));
					} else {
						normalParameters.setStandardDeviation(Double.parseDouble(textField.getText()));
					}


				} else if (parameters instanceof PoissonParameters) {
					
					PoissonParameters poissonParameters = (PoissonParameters)parameters;
					poissonParameters.setMean(Double.parseDouble(textField.getText()));
	 			}
	    	
//		repositoryViewPanel.setMessagem("");
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	    	 // verificar depois a necessidade
	    }

		public void setParameters(Parameters parameters) {
			this.parameters = parameters;
		}
	

}
