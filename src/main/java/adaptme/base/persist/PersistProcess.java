package adaptme.base.persist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.eclipse.epf.uma.WorkOrder;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.util.MethodContentType;
import model.spem.util.ProcessContentType;

public class PersistProcess {

	private HashMap<String, ProcessContentRepository> processContentRepositoryHashMap;
	private HashMap<String, MethodContentRepository> methodContentRepositoryHashMap;

	// futuramente precisando de novos dados do processo exportado do EPF, alterar o tipo String para Role, WorkProduct and TaskDescriptor
	private Set<String> rolesList;
//	private Set<String> wordProductList;
	private List<String> wordProductList;
	private Set<String> taskList;

	private ProcessRepository root;
	 
	private MethodLibraryHash methodLibraryHash;

	public PersistProcess() {
		processContentRepositoryHashMap = new HashMap<>();
		methodContentRepositoryHashMap = new HashMap<>();
		rolesList = new HashSet<>();
//		wordProductList = new HashSet<>();
		wordProductList = new ArrayList<>();
		taskList = new HashSet<>();
	}

	public ProcessRepository buildProcess(Process process, MethodLibraryHash methodLibraryHash) {
		this.methodLibraryHash = methodLibraryHash;
		root = new ProcessRepository();
		root.setName(process.getPresentationName());
		Map<String, MethodElement> hash = new HashMap<>();
		buildHash(process, hash);
		
		buildChildren(process, root, root, null, hash);
		return root;
	}

	public ProcessRepository buildProcessWithDeliveryProcessAsRoot(Process process, MethodLibraryHash methodLibraryHash) {
		this.methodLibraryHash = methodLibraryHash;
		root = new ProcessRepository();
		
		root.setName(process.getPresentationName());
		
		Map<String, MethodElement> hash = new HashMap<>();
		buildHash(process, hash);
		
		ProcessContentRepository deliveryProcessAsRoot = createProcessContentRepository(process, ProcessContentType.DELIVERY_PROCESS, hash);
		root.addProcessElement(deliveryProcessAsRoot);
		buildChildren(process, null, root, deliveryProcessAsRoot, hash);
		return root;
	}

	private void buildChildren(Activity process, ProcessRepository root, ProcessRepository processRepository,
			ProcessContentRepository father, Map<String, MethodElement> hash) {

		for (Object object : process.getBreakdownElementOrRoadmap()) {
			if (object instanceof Milestone) {
				Milestone milestone = (Milestone) object;
				ProcessContentRepository content = createProcessContentRepository(milestone,
						ProcessContentType.MILESTONE, hash);
				setBreadownElementsAttributes(milestone, content);
				
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
			} else if (object instanceof Iteration) {
				Iteration iteration = (Iteration) object;
				ProcessContentRepository content = createProcessContentRepository(iteration, ProcessContentType.ITERATION, hash);
				setBreadownElementsAttributes(iteration, content);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				buildChildren(iteration, null, processRepository, content, hash);
			} else if (object instanceof Phase) {
				Phase phase = (Phase) object;
				ProcessContentRepository content = createProcessContentRepository(phase, ProcessContentType.PHASE, hash);
				setBreadownElementsAttributes(phase, content);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				buildChildren(phase, null, processRepository, content, hash);
				
			} else if (object instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) object;
				ProcessContentRepository content = createTask(taskDescriptor, hash);
				setBreadownElementsAttributes(taskDescriptor, content);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				taskList.add(taskDescriptor.getName());  // importante para pegar o taskName se fosse precisar de outros dados, precisaria do objeto content
														 // e o private Set<String> taskList seria private Set<ProcessContentRepository>
				
				content.setProcessRepository(processRepository);
			} else if (object instanceof Activity) {
				Activity activity = (Activity) object;
				ProcessContentRepository content = createProcessContentRepository(activity, ProcessContentType.ACTIVITY, hash);
				setBreadownElementsAttributes(activity, content);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				if (activity.getVariabilityType() == VariabilityType.NA) {
					buildChildren(activity, null, processRepository, content, hash);
				} else {
					Activity superActivity = (Activity) methodLibraryHash.getHashMap()
							.get(activity.getVariabilityBasedOnElement());
					buildChildren(superActivity, null, processRepository, content, hash);
				}
			}
		}
	}
	
	public void setBreadownElementsAttributes(WorkBreakdownElement source, ProcessContentRepository target) {
		target.setEventDriven(source.isIsEventDriven());
		target.setHasMultipleOccurrences(source.isHasMultipleOccurrences());
		target.setOngoing(source.isIsOngoing());
		target.setOptional(source.isIsOptional());
		target.setPlanned(source.isIsPlanned());
		target.setRepeatable(source.isIsRepeatable());
		
	}
	
	private void buildHash(Activity process, Map<String, MethodElement> hash) {
		for (Object object : process.getBreakdownElementOrRoadmap()) {
			MethodElement element = (MethodElement) object;
			hash.put(element.getId(), element);
			if (object instanceof Activity) {
				buildHash((Activity) object, hash);
			}
		}
	}

	public void persist(ProcessRepository processRepository, String fileName) throws IOException {

		try {
			JAXBContext context = JAXBContext.newInstance(ProcessRepository.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File f = new File("output/spem_uma_simplified/" + fileName);
			marshaller.marshal(processRepository, f);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private ProcessContentRepository createTask(TaskDescriptor element, Map<String, MethodElement> hash) {

		ProcessContentRepository processContentRepository = createProcessContentRepository(element, ProcessContentType.TASK, hash);		
		
		setWorkProducts(processContentRepository, hash, element);

		List<String> performedPrimarilyBy = new ArrayList<>();
		List<String> additionallyPerformedBy = new ArrayList<>();
		
		List<JAXBElement<String>> list = element.getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
		
		for (JAXBElement<String> jaxbElement : list) {
			QName qName = jaxbElement.getName();
			String localPart = qName.getLocalPart();
			MethodElement methodElement = hash.get(jaxbElement.getValue());
			if(methodElement == null){
				methodElement = (MethodElement) methodLibraryHash.getHashMap().get(jaxbElement.getValue());
				hash.put(methodElement.getId(), methodElement);
			}
			if (localPart == "PerformedPrimarilyBy") {
				performedPrimarilyBy.add(methodElement.getPresentationName());
			} else if (localPart == "AdditionallyPerformedBy") {
				additionallyPerformedBy.add(methodElement.getPresentationName());
			}
		}	
		

		for (String name : performedPrimarilyBy) {
			MethodContentRepository role = createMethodContentRepository(name, MethodContentType.ROLE);
			role.setProcessContentRepository(processContentRepository);
			processContentRepository.setMainRole(role);
			rolesList.add(role.getName());
		}
		for (String name : additionallyPerformedBy) {
			MethodContentRepository role = createMethodContentRepository(name, MethodContentType.ROLE);
			role.setProcessContentRepository(processContentRepository);
			processContentRepository.addAdditionalRoles(role);
			rolesList.add(role.getName());
		}
		
		setPredecessors(element, hash, processContentRepository);
		
		return processContentRepository;
	}

	private ProcessContentRepository createProcessContentRepository(WorkBreakdownElement element,
			ProcessContentType type, Map<String, MethodElement> hash) {
		if (processContentRepositoryHashMap.containsKey(element.getName())) {
			return processContentRepositoryHashMap.get(element.getName());
		}
		
		ProcessContentRepository processContentRepository = new ProcessContentRepository();
		processContentRepository.setName(element.getPresentationName());
		processContentRepository.setType(type);		

		setWorkProducts(processContentRepository, hash, element);

		
		
		List<MethodContentRepository> allRoles = new ArrayList<>();
		if(!(element instanceof TaskDescriptor) && !(element instanceof Milestone)){
			allRoles.addAll(getAllRoles((Activity) element, hash));
		}
		processContentRepository.setAllRoles(allRoles);
		
		
		processContentRepositoryHashMap.put(element.getPresentationName(), processContentRepository);
		
		setPredecessors(element, hash, processContentRepository);
		return processContentRepository;
	}


	private void setWorkProducts(ProcessContentRepository processContentRepository, Map<String, MethodElement> hash, WorkBreakdownElement element) {

		if(element instanceof Activity){
			Activity process = (Activity) element;
			for (Object object : process.getBreakdownElementOrRoadmap()) {
				if(object instanceof Activity){
					setWorkProducts(processContentRepository, hash, (WorkBreakdownElement) object);
				} else if(object instanceof TaskDescriptor ||  object instanceof Milestone){
					setWorkProducts(processContentRepository, hash, (WorkBreakdownElement) object);
				}
			}			
		}else if(element instanceof TaskDescriptor) {
			
			List<JAXBElement<String>> list = ((TaskDescriptor) element).getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
			
			List<String> inputNames = new ArrayList<>();
			List<String> outputNames = new ArrayList<>();		
			
			for (JAXBElement<String> jaxbElement : list) {
				QName qName = jaxbElement.getName();
				String localPart = qName.getLocalPart();
				MethodElement methodElement = hash.get(jaxbElement.getValue());
				if(methodElement == null){
					methodElement = (MethodElement) methodLibraryHash.getHashMap().get(jaxbElement.getValue());
					hash.put(methodElement.getId(), methodElement);
				}
				if (localPart == "MandatoryInput") {
					inputNames.add(methodElement.getPresentationName());
				} else if (localPart == "Output") {
					outputNames.add(methodElement.getPresentationName());
				}
			}		
	
			for (String name : inputNames) {
				MethodContentRepository input = createMethodContentRepository(name, MethodContentType.ARTIFACT);
				input.setProcessContentRepository(processContentRepository);
				processContentRepository.addInputMethodContent(input);
				wordProductList.add(input.getName());
			}
			for (String name : outputNames) {
				MethodContentRepository output = createMethodContentRepository(name, MethodContentType.ARTIFACT);
				output.setProcessContentRepository(processContentRepository);
				processContentRepository.addOutputMethodContent(output);
				wordProductList.add(output.getName());
			}
		}else if(element instanceof Milestone){
			List<String> list = ((Milestone) element).getRequiredResult();
			for (String key : list) {
				MethodElement methodElement = hash.get(key);
				if(methodElement == null){
					methodElement = (MethodElement) methodLibraryHash.getHashMap().get(key);
					hash.put(methodElement.getId(), methodElement);
				}				
				MethodContentRepository result = createMethodContentRepository(methodElement.getName(), MethodContentType.ARTIFACT);
				result.setProcessContentRepository(processContentRepository);
				processContentRepository.addInputMethodContent(result);
				processContentRepository.addOutputMethodContent(result);
			}
		}
	}
	private void setPredecessors(WorkBreakdownElement element, Map<String, MethodElement> hash, ProcessContentRepository processContentRepository) {

		List<ProcessContentRepository> predecessorsList = new ArrayList<>();
		
		for (WorkOrder workOrder : element.getPredecessor()) {
			WorkBreakdownElement predecessor = (WorkBreakdownElement) hash.get(workOrder.getValue());
			if(predecessor == null){
				predecessor = (WorkBreakdownElement) methodLibraryHash.getHashMap().get(workOrder.getValue());
				hash.put(predecessor.getId(), predecessor);
			}
			if (predecessor instanceof Iteration) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.ITERATION, hash);
				predecessorsList.add(predecessorRepository);
			}else if (predecessor instanceof Activity) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.ACTIVITY, hash);
				predecessorsList.add(predecessorRepository);
			}else if (predecessor instanceof Phase) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.PHASE, hash);
				predecessorsList.add(predecessorRepository);
			}else if (predecessor instanceof DeliveryProcess) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.DELIVERY_PROCESS, hash);
				predecessorsList.add(predecessorRepository);
			}else if (predecessor instanceof TaskDescriptor) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.TASK, hash);
				predecessorsList.add(predecessorRepository);
			}
		}
		processContentRepository.setPredecessors(predecessorsList);
	}

	private List<MethodContentRepository> getAllRoles(Activity activity, Map<String, MethodElement> hash) {
		List<MethodContentRepository> roles = new ArrayList<>();
		List<Object> children = activity.getBreakdownElementOrRoadmap();

		for (Object object : children) {			
			if (object instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) object;
				List<JAXBElement<String>> list = taskDescriptor.getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
				for (JAXBElement<String> jaxbElement : list) {
					QName qName = jaxbElement.getName();
					String localPart = qName.getLocalPart();
					MethodElement element = hash.get(jaxbElement.getValue());
					if(element == null){
						element = (MethodElement) methodLibraryHash.getHashMap().get(jaxbElement.getValue());
						hash.put(element.getId(), element);
					}
					if (localPart == "PerformedPrimarilyBy" || localPart == "AdditionallyPerformedBy") {
						roles.add(createMethodContentRepository(element.getPresentationName(), MethodContentType.ROLE));
					}
				}
			}else if(object instanceof Activity || object instanceof Iteration || object instanceof Milestone || object instanceof Phase || object instanceof DeliveryProcess){
				roles.addAll(getAllRoles((Activity) object, hash));
			}			
		}
		return roles;
	}

	private MethodContentRepository createMethodContentRepository(String name, MethodContentType type) {
		if (methodContentRepositoryHashMap.containsKey(name)) {
			return methodContentRepositoryHashMap.get(name);
		}
		MethodContentRepository methodContentRepository = new MethodContentRepository();
		methodContentRepository.setName(name);
		methodContentRepository.setType(type);
		return methodContentRepository;
	}

	public HashMap<String, IntegratedLocalAndRepositoryViewPanel> buildGUI(ProcessRepository processRepository,List<String> keySet) {
		
		HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMap = new HashMap<>();

		for (ProcessContentRepository content : processRepository.getProcessContents()) { // ainda nao sei se vou precisar das activities
			IntegratedLocalAndRepositoryViewPanel integratedLocalAndRepositoryViewPanel = new IntegratedLocalAndRepositoryViewPanel(content.getName(), content);
			hashMap.put(content.getName(), integratedLocalAndRepositoryViewPanel);
			buildGUISession(content, hashMap, keySet);
		}
		
		return hashMap;
	}
	
	private void buildGUISession(ProcessContentRepository content, HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMap, List<String> keySet) {
		IntegratedLocalAndRepositoryViewPanel sessionPanel = new IntegratedLocalAndRepositoryViewPanel(content.getName(),content);
		hashMap.put(content.getName(), sessionPanel);
		keySet.add(content.getName());
 		for (ProcessContentRepository processContentRepository : content.getChildren()) {
			buildGUISession(processContentRepository, hashMap,keySet);
		}
	}

	public Set<String> getRolesList() {
		return rolesList;
	}

//	public Set<String> getWordProductList() {
//		return wordProductList;
//	}
	

	public List<String> getWordProductList() {
		return wordProductList;
	}
	
	public Set<String> getTaskList() {
		return taskList;
	}

	
	
	 
	
	
	 
}
