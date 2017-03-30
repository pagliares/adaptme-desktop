import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.Sample;
import model.spem.measurement.DurationMeasurement;
import model.spem.util.MethodContentType;
import model.spem.util.ProcessContentType;
import model.spem.util.TimeEnum;

public class TestTransformationOfSPEMToXACDMLFromConsole {

	public static void main(String[] args) {
		// Estou fazendo igual estava original. O delivery process (no raiz) e o ProcessRepository.
		// ProcessRepository tem varios ProcessContentRepository (cada um representa um no da arvore, excetuando o raiz)
    	
		ProcessRepository processRepository = new ProcessRepository();
    	processRepository.setName("Problem Report Process");

    	
		ProcessContentRepository approveTask = new ProcessContentRepository();
		approveTask.setId(1);
		approveTask.setName("Approve problem report");
		approveTask.setType(ProcessContentType.TASK);
		approveTask.setProcessRepository(processRepository);
		approveTask.setSample(null);
 		
		MethodContentRepository mainRole = new MethodContentRepository();
		mainRole.setName("Engineer");
		mainRole.setType(MethodContentType.ROLE);
		mainRole.setProcessContentRepository(approveTask);
        
		MethodContentRepository mcr = new MethodContentRepository();
    	mcr.setName("Problem Report");
        mcr.setType(MethodContentType.ARTIFACT);
        mcr.setProcessContentRepository(approveTask);
        
        approveTask.addInputMethodContent(mcr);
        approveTask.addOutputMethodContent(mcr);
        approveTask.setMainRole(mainRole);


//		ProcessContentRepository analyseTask = new ProcessContentRepository();
//		analyseTask.setName("Analyse problem report");
//		analyseTask.setType(ProcessContentType.TASK);
//
//		
//		ProcessContentRepository acceptedProblemReportMilestone = new ProcessContentRepository();
//		acceptedProblemReportMilestone.setName("Accepted problem report");
//		acceptedProblemReportMilestone.setType(ProcessContentType.MILESTONE);
//		
//		ProcessContentRepository implementTask = new ProcessContentRepository();
//		implementTask.setName("Implement problem report");
//		implementTask.setType(ProcessContentType.TASK);
//
//		
//		ProcessContentRepository verifyTask = new ProcessContentRepository();
//		verifyTask.setName("Verify problem report");
//		verifyTask.setType(ProcessContentType.TASK);
//
//		
//		ProcessContentRepository fixedProblemReportMilestone = new ProcessContentRepository();
//		fixedProblemReportMilestone.setName("Fixed problem report");
//		fixedProblemReportMilestone.setType(ProcessContentType.MILESTONE);

		processRepository.addProcessElement(approveTask);
//		processRepository.addProcessElement(analyseTask);
//		processRepository.addProcessElement(acceptedProblemReportMilestone);
//		processRepository.addProcessElement(implementTask);
//		processRepository.addProcessElement(verifyTask);
//		processRepository.addProcessElement(fixedProblemReportMilestone);
		
		List<ProcessContentRepository> lista = processRepository.getProcessContents();
		for (ProcessContentRepository pcr: lista) {
			System.out.println("ProcessContentRepository id..: "+pcr.getId());
			System.out.println("ProcessContentRepository name..: "+ pcr.getName());
			System.out.println("Number of children :" + pcr.getChildren().size());
			ProcessContentRepository parent = pcr.getFather();
			if (parent != null)
				System.out.println("Parent name :" + parent.getName());
			
			Set<MethodContentRepository> inputs = pcr.getInputMethodContentsRepository();
			Set<MethodContentRepository> outputs = pcr.getOutputMethodContentsRepository();
			for (MethodContentRepository input: inputs) {
				System.out.println("Input MethodContentRepository...: " + input);
			}
			for (MethodContentRepository output: outputs) {
				System.out.println("Output MethodContentRepository...: " + output);
			}
			List<ProcessContentRepository> predecessores = pcr.getPredecessors();
			
			for (ProcessContentRepository predecessor: predecessores) {
				System.out.println("Predecessor ProcessContentRepository...: " + predecessor);
			}
			System.out.println("ProcessRepository class acting like devlivery process (root node) :" + pcr.getProcessRepository().getName());
			if (pcr.getSample() != null)
			System.out.println("Sample name :" + pcr.getSample().getName());
			
			System.out.println("Main Role...: " + pcr.getMainRole().getName());
			
			
			List<MethodContentRepository> additionalRoles = pcr.getAdditionalRoles();
			for (MethodContentRepository additionalRole: additionalRoles) {
				System.out.println("Additional Role...: " + additionalRole);
			}
			
		}
		
		
//    	ProcessContentRepository pcrTask2 = new ProcessContentRepository();
//    	pcrTask2.setName("Task #2");
//    	pcrTask2.setType(ProcessContentType.TASK);
//    	
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Role #2");
//    	mcr.setType(MethodContentType.ROLE);
//    	mcr.setProcessContentRepository(pcrTask2);
//    	pcrTask2.setMainRole(mcr);
//    	
//    	
//    	
//    	ProcessContentRepository pcrTask3 = new ProcessContentRepository();
//    	pcrTask3.setName("Task #3");
//    	pcrTask3.setType(ProcessContentType.TASK);
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Role #1");
//    	mcr.setType(MethodContentType.ROLE);
//    	mcr.setProcessContentRepository(pcrTask3);
//    	pcrTask3.setMainRole(mcr);
//    	
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Problem Report");
//        mcr.setType(MethodContentType.ARTIFACT);
//        mcr.setProcessContentRepository(pcrTask3);
//    	pcrTask3.addInputMethodContent(mcr);
//    	
//    	ProcessContentRepository pcrTask4 = new ProcessContentRepository();
//    	pcrTask4.setName("Task #4");
//    	pcrTask4.setType(ProcessContentType.TASK);
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Role #3");
//    	mcr.setType(MethodContentType.ROLE);
//    	mcr.setProcessContentRepository(pcrTask4);
//    	pcrTask4.setMainRole(mcr);
//    	
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Problem Report");
//        mcr.setType(MethodContentType.ARTIFACT);
//        mcr.setProcessContentRepository(pcrTask4);
//    	pcrTask4.addInputMethodContent(mcr);
//    	
//    	ProcessContentRepository pcrTask5 = new ProcessContentRepository();
//    	pcrTask5.setName("Task #5");
//    	pcrTask5.setType(ProcessContentType.TASK);
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Role #1");
//    	mcr.setType(MethodContentType.ROLE);
//    	mcr.setProcessContentRepository(pcrTask5);
//    	pcrTask5.setMainRole(mcr);
//    	
//    	mcr = new MethodContentRepository();
//    	mcr.setName("Problem Report");
//        mcr.setType(MethodContentType.ARTIFACT);
//        mcr.setProcessContentRepository(pcrTask5);
//    	pcrTask5.addInputMethodContent(mcr);
//
//    	
//    	processRepository.setName("Aircraft development process");
//    	processRepository.setSimulationObjective("Embraer Simulation objective populated automatically");
//    	processRepository.setChosen(true);
    	 
//    	processRepository.addProcessElement(pcrTask1);
//    	processRepository.addProcessElement(pcrTask2);
//    	processRepository.addProcessElement(pcrTask3);
//    	processRepository.addProcessElement(pcrTask4);
//    	processRepository.addProcessElement(pcrTask5);
//    	
//    	pcrTask1.setProcessRepository(processRepository);
//    	pcrTask2.setProcessRepository(processRepository);
//    	pcrTask3.setProcessRepository(processRepository);
//    	pcrTask4.setProcessRepository(processRepository);
//    	pcrTask5.setProcessRepository(processRepository);
//    	
//    	Sample sampleTask1 = new Sample();
//    	sampleTask1.setName("Sample for task #1");
//    	Sample sampleTask2 = new Sample();
//    	sampleTask2.setName("Sample for task #2");
//    	Sample sampleTask3 = new Sample();
//    	sampleTask3.setName("Sample for task #3");
//    	Sample sampleTask4 = new Sample();
//    	sampleTask4.setName("Sample for task #4");
//    	Sample sampleTask5 = new Sample();
//    	sampleTask5.setName("Sample for task #5");
//
//    	BufferedReader bufferedReader = null;

//    	try {
//    		String line;
//    		bufferedReader = new BufferedReader(new FileReader("/Users/pagliares/Dropbox/projetosEclipse/workspaceHirata/AdaptMeWebMaven/embraer.csv")); 
// 
//    		while ((line = bufferedReader.readLine()) != null) {
//    			System.out.println("Raw CSV data: " + line);
//    			List<String> list = convertCSVtoArrayList(line);
//    			
//					DurationMeasurement measurement = new DurationMeasurement();
//					measurement.setName("Duration measurement for task #1");
//					measurement.setValue(Double.parseDouble(list.get(2))+1);
//					measurement.setScale(TimeEnum.DAYS);	 
//					sampleTask1.addMeasurement(measurement);
//					pcrTask1.setSample(sampleTask1);
//					sampleTask1.setProcessContentRepository(pcrTask1);
//					measurement.setSample(sampleTask1);
//
//					measurement = new DurationMeasurement();
//					measurement.setName("Duration measurement for task #2");
//					measurement.setValue(Double.parseDouble(list.get(3))+1);
//					measurement.setScale(TimeEnum.DAYS); 
//					sampleTask2.addMeasurement(measurement);
//					pcrTask2.setSample(sampleTask2);
//					pcrTask2.setPredecessor(pcrTask1);
//					sampleTask2.setProcessContentRepository(pcrTask2);
//					measurement.setSample(sampleTask2);
//
//					measurement = new DurationMeasurement();
//					measurement.setName("Duration measurement for task #3");
//					measurement.setValue(Double.parseDouble(list.get(4))+1);
//					measurement.setScale(TimeEnum.DAYS); 
//					sampleTask3.addMeasurement(measurement);
//					pcrTask3.setSample(sampleTask3);
//					pcrTask3.setPredecessor(pcrTask2);
//					sampleTask3.setProcessContentRepository(pcrTask3);
//					measurement.setSample(sampleTask3);
//
//					measurement = new DurationMeasurement();
//					measurement.setName("Duration measurement for task #4");
// 					measurement.setValue(Double.parseDouble(list.get(5))+1);
//					measurement.setScale(TimeEnum.DAYS);
//					sampleTask4.addMeasurement(measurement);
//					pcrTask4.setSample(sampleTask4);
//					pcrTask4.setPredecessor(pcrTask3);
//					sampleTask4.setProcessContentRepository(pcrTask4);
//					measurement.setSample(sampleTask4);
//					
//					measurement = new DurationMeasurement();
//					measurement.setName("Duration measurement for task #5");
//					measurement.setValue(Double.parseDouble(list.get(6))+1);
//					measurement.setScale(TimeEnum.DAYS);
//					sampleTask5.addMeasurement(measurement);
//					pcrTask5.setSample(sampleTask5);
//					pcrTask5.setPredecessor(pcrTask4);
//					sampleTask5.setProcessContentRepository(pcrTask5);
//					measurement.setSample(sampleTask5);
//					
// 			    	em.persist(processRepository);
// 	 
//    		}

//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	} finally {
//    		try {
//    			if (bufferedReader != null) bufferedReader.close();
//    		} catch (IOException crunchifyException) {
//    			crunchifyException.printStackTrace();
//    		}
//    	}


    	
    	//List<ProcessContentRepository> listOfProcessContentRepository = processRepository.getProcessContents();

    	
//    	processRepository.setProcessContents(populateAllProcessContentsIncludingChildren(listOfProcessContentRepository)); 			
//    	em.persist(processRepository);
//    	persistProcess.persist(processRepository, processRepository.getName(), true);
//    	persistProcess = new PersistProcessWeb();


//    	return "Database successfully populated";

		
		
		

	}

}
