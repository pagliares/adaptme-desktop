package adaptme.repository.web.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.Sample;
import model.spem.measurement.DurationMeasurement;
import model.spem.measurement.Measurement;
import model.spem.util.ProcessContentType;

import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.ProcessComponent;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import adaptme.base.MethodLibraryHash;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.persist.PersistProcess;

public class RestClientProxy {
	
	
	public void getHelloWorldString() {
		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getHelloWorldString");
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.TEXT_PLAIN);
		 String response =  invocationBuilder.get(String.class);
		 System.out.println(response);
	}
	
	public void getHelloWorldXML() {
		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getHelloWorldXML");
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.TEXT_XML);
		 String response =  invocationBuilder.get(String.class);
		 System.out.println(response);
	}
	
	public void getHelloWorldHTML() {
		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getHelloWorldHTML");
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.TEXT_HTML);
		 String response =  invocationBuilder.get(String.class);
		 System.out.println(response);
	}
	
	public void getProcessXML() throws IOException {   
		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getProcessXML");
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);
		 ProcessRepository process =  invocationBuilder.get(ProcessRepository.class);
		
		 PersistProcess persistProcess = new PersistProcess();
		 persistProcess.persist(process, "HBC");
		 System.out.println("Success ! HBC process downloaded from web services and stored on xml file in folder output");
 	}
	
	 // For JSON response also add the Jackson libraries to your webapplication. In this case you would also change the client registration to
    // ClientConfig config = new ClientConfig().register(JacksonFeature.class);
    // Get JSON for application
    // System.out.println(target.path("rest").path("todo").request()
    // .accept(MediaType.APPLICATION_JSON).get(String.class));
	public void getProcessJSON() throws IOException {   
		 ClientConfig config = new ClientConfig().register(JacksonFeature.class);
 		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getProcessXML");
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_JSON);
		 ProcessRepository process =  invocationBuilder.get(ProcessRepository.class);
//		 Utility utility = new Utility();
//		 utility.persistProcess(process);
 	}
	
	public void listAllProcesses() {
		WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("listAllProcesses");
		Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);

		List<ProcessRepository> result = invocationBuilder.get(new GenericType<List<ProcessRepository>>() {});
       // printProcesses(result);
		
		// codigo a ser inserido no metod getHistogram
		
		for (ProcessRepository p:result) {

//			List<ProcessContentRepository> resultado = p.getListProcessContentRepositoryWithTasksOnly(p.getProcessContents());
			List<ProcessContentRepository> resultado = p.getProcessContents();
			for (ProcessContentRepository p1: resultado) {
//				 if (p1.getName().equalsIgnoreCase("Manage Iteration")) {
					 Sample sample = p1.getSample();
					 if (sample != null) {
						 System.out.println("Sample name " + sample.getName());
						
						 List<Measurement> measurements = sample.getMeasurements();
						 System.out.println("Sample size " + measurements.size());					
						 }	  
 
//				 }
			}
		}
	}
	

	public void getTask(String taskName) {
 		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("getTask").queryParam("taskName", taskName);
		 Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);

		List<ProcessRepository> result = invocationBuilder.get(new GenericType<List<ProcessRepository>>() {});
       
		printProcesses(result);
 
	}
	
	public void listAllMeasurements() {
		WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("listAllMeasurements");
		Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);

		List<Measurement> measurements = invocationBuilder.get(new GenericType<List<Measurement>>() {});
        for (Measurement measurement: measurements) {
        	DurationMeasurement durationMeasurement = (DurationMeasurement)measurement;
        	System.out.println(durationMeasurement.getName());
        	System.out.println(durationMeasurement.getValue());
        	System.out.println(durationMeasurement.getScale().toString());
        }
	}
	
	public void listAllTasks() {
		WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("listAllTasks");
		Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);

		List<ProcessRepository> result = invocationBuilder.get(new GenericType<List<ProcessRepository>>() {});
        printProcesses(result);
	}
	
	public List<Measurement> listAllMeasurementsForSpecificContent(String content) {
		
		WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("listAllMeasurementsForSpecificContent").queryParam("content", content);
		Invocation.Builder invocationBuilder = applicationDomainRestFacadeGetResource.request(MediaType.APPLICATION_XML);

		List<Measurement> measurements = invocationBuilder.get(new GenericType<List<Measurement>>() {});
		System.out.println("Quantity of measurements..: " +  measurements.size());
		for (Measurement measurement: measurements) {
        	DurationMeasurement durationMeasurement = (DurationMeasurement)measurement;
        	System.out.println(durationMeasurement.getName());
        	System.out.println(durationMeasurement.getValue());
        	System.out.println(durationMeasurement.getScale().toString());
        }
		return measurements;
	}
	
	
	public void sendProcessToRepository() throws IOException {   
		PersistProcess persistProcess = new PersistProcess();
		ProcessRepository processRepository = null;

		MethodLibraryWrapper methodLibraryWrapper = new MethodLibraryWrapper();
		methodLibraryWrapper.load(new File("input/process_alternatives_epf/risoto.xml"));
		for (MethodPackage methodPackage : methodLibraryWrapper.getMethodLibrary().getMethodPlugin().get(0)
				.getMethodPackage()) {
			if (methodPackage instanceof ProcessComponent) {
				Process process = ((ProcessComponent) methodPackage).getProcess();
				MethodLibraryHash methodLibraryHash = methodLibraryWrapper.getMethodLibraryHash();
				processRepository = persistProcess.buildProcess(process, methodLibraryHash);
				persistProcess.persist(processRepository, process.getName() + ".xml");	
 			}
		}
		 
		 WebTarget applicationDomainRestDirectoryRestFacade = buildRestURI();
		 WebTarget applicationDomainRestFacadeGetResource = applicationDomainRestDirectoryRestFacade.path("sendProcessToRepository");
		 applicationDomainRestFacadeGetResource.request().post(Entity.entity(processRepository, MediaType.APPLICATION_XML));
 	}

	 private static URI getBaseURI() {
		   return UriBuilder.fromUri("http://localhost:8080/AdaptMeWebMaven/rest").build();
		  //return UriBuilder.fromUri("http://52.20.198.69:8080/AdaptmeRepositoryWeb/rest").build();
	}
	
	private WebTarget buildRestURI(){
		ClientConfig config = new ClientConfig();
		 Client client = ClientBuilder.newClient(config);
		 WebTarget applicationDomainResource = client.target(getBaseURI());
		 WebTarget applicationDomainRestFacadeResource = applicationDomainResource.path("rest_facade");
		 return applicationDomainRestFacadeResource;
	}
	
	private void printProcesses(List<ProcessRepository> listProcessRepository) {
		
		for (ProcessRepository processRepository : listProcessRepository) {

 			List<ProcessContentRepository> listProcessContentRepository = processRepository.getProcessContents();

			for (ProcessContentRepository processContentRepository: listProcessContentRepository) {
				
				if (!processContentRepository.getType().equals(ProcessContentType.TASK)) {
					System.out.println("\n"+ processContentRepository.getType() + "                 :  " + processContentRepository.getName());
				}
				
				if (processContentRepository.getType().equals(ProcessContentType.TASK)) {
					System.out.println("\n\n"+ processContentRepository.getType().toString() + "                 :  " + processContentRepository.getName());
					
					System.out.print("Role  : " );
					MethodContentRepository role = processContentRepository.getMainRole();
					System.out.print("Main Role            :  " + role.getName()+"\n");

					List<MethodContentRepository> additionalRoles = processContentRepository.getAdditionalRoles();
					for (MethodContentRepository additionalRole: additionalRoles) {
						System.out.println("Additional role      :  " + additionalRole.getName());
					}

					System.out.print("Input work products  : " );
					Set<MethodContentRepository> inputWorkProducts = processContentRepository.getInputMethodContentsRepository();
					for (MethodContentRepository m: inputWorkProducts) {
						System.out.print("\t" + m.getName());
					}
					System.out.print("\nOutput work products : ");
					Set<MethodContentRepository> outputWorkProducts = processContentRepository.getOutputMethodContentsRepository();
					for (MethodContentRepository m: outputWorkProducts) {
						System.out.print("\t" + m.getName());
					}
					
					Sample sampleTask = processContentRepository.getSample();
					if (sampleTask != null) {
						System.out.println("Sample size  : "  + sampleTask.getSize());
						List<Measurement> measurements = sampleTask.getMeasurements();
						System.out.println("Quantity of measurements : " + measurements.size());
						System.out.println("measurements  : " );
						for (Measurement m: measurements) {
							if (m instanceof DurationMeasurement) {
								DurationMeasurement d = (DurationMeasurement)m;
								System.out.print(d.getValue() + " ");
							}
					    }
					}
			}
			}
			}
	}
	 
	 public static void main(String [] args) throws IOException{
//		 new RestClientProxy().getHelloWorldString();
// 		 new RestClientProxy().getHelloWorldXML();
// 		 new RestClientProxy().getHelloWorldHTML();
// 		 new RestClientProxy().getProcessXML();  
// 		 new RestClientProxy().listAllProcesses(); BUG
//		 new RestClientProxy().listAllTasks();
//		 new RestClientProxy().listAllMeasurements();
		 new RestClientProxy().listAllMeasurementsForSpecificContent("Iteration [1..n]");  
//		 new RestClientProxy().getTask("Write acceptance tests"); BUG
// 		 new RestClientProxy().sendProcessToRepository();  
		 System.out.println("Success !\n");
	 }
}


 
