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
import adaptme.dynamic.gui.RepositoryViewPanel;
import adaptme.dynamic.gui.UpdatePanel;
import adaptme.dynamic.gui.meeting.MeetingPanel;
import adaptme.dynamic.gui.task.InputWorkProductPanel;
import adaptme.dynamic.gui.task.OutputWorkProductPanel;
import adaptme.dynamic.gui.task.RolePanel;
import adaptme.dynamic.gui.task.SessionPanel;
import adaptme.dynamic.gui.task.TaskPanel;
import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.derived.BestFitDistribution;
import model.spem.util.MethodContentType;
import model.spem.util.ProcessContentType;
import model.spem.util.TimeEnum;

public class PersistProcess {

	private HashMap<String, ProcessContentRepository> processContentRepositoryHashMap;
	private HashMap<String, MethodContentRepository> methodContentRepositoryHashMap;

	private Set<String> rolesList;
	private Set<String> wordProductList;

	private ProcessRepository root;
	private MethodLibraryHash methodLibraryHash;

	public PersistProcess() {
		processContentRepositoryHashMap = new HashMap<>();
		methodContentRepositoryHashMap = new HashMap<>();
		rolesList = new HashSet<>();
		wordProductList = new HashSet<>();
	}

	public ProcessRepository buildProcess(Process process, MethodLibraryHash methodLibraryHash) {
		this.methodLibraryHash = methodLibraryHash;
		root = new ProcessRepository();
		root.setName(process.getPresentationName());

		buildChildren(process, root, root, null);

		return root;
	}

	private void buildChildren(Activity process, ProcessRepository root, ProcessRepository processRepository,
			ProcessContentRepository father) {
		Map<String, MethodElement> hash = new HashMap<>();
		buildHash(process, hash);
		Boolean isNew = new Boolean(false);
		for (Object object : process.getBreakdownElementOrRoadmap()) {
			if (object instanceof Milestone) {
				Milestone milestone = (Milestone) object;
				ProcessContentRepository content = createProcessContentRepository(milestone,
						ProcessContentType.MILESTONE, isNew, hash);
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
				ProcessContentRepository content = createProcessContentRepository(iteration,
						ProcessContentType.ITERATION, isNew, hash);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				buildChildren(iteration, null, processRepository, content);
			} else if (object instanceof Phase) {
				Phase phase = (Phase) object;
				ProcessContentRepository content = createProcessContentRepository(phase, ProcessContentType.PHASE,
						isNew, hash);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				buildChildren(phase, null, processRepository, content);
			} else if (object instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) object;
				ProcessContentRepository content = createTask(taskDescriptor, hash);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
			} else if (object instanceof Activity) {
				Activity activity = (Activity) object;
				ProcessContentRepository content = createProcessContentRepository(activity, ProcessContentType.ACTIVITY,
						isNew, hash);
				content.setFather(father);
				if (father != null) {
					father.addChild(content);
				}
				if (root != null) {
					processRepository.addProcessElement(content);
				}
				content.setProcessRepository(processRepository);
				if (activity.getVariabilityType() == VariabilityType.NA) {
					buildChildren(activity, null, processRepository, content);
				} else {
					Activity superActivity = (Activity) methodLibraryHash.getHashMap()
							.get(activity.getVariabilityBasedOnElement());
					buildChildren(superActivity, null, processRepository, content);
				}
			}
		}
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

	private ProcessContentRepository createTask(TaskDescriptor taskDescriptor, Map<String, MethodElement> hash) {
		List<JAXBElement<String>> list = taskDescriptor.getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
		List<String> inputNames = new ArrayList<>();
		List<String> outputNames = new ArrayList<>();
		List<String> performedPrimarilyBy = new ArrayList<>();
		List<String> additionallyPerformedBy = new ArrayList<>();
		// "AssistedBy"
		// AdditionallyPerformedBy"
		// "OptionalInput"
		// "MandatoryInput"
		// "Output"
		// "ExternalInput"
		// "PerformedPrimarilyBy"
		for (JAXBElement<String> jaxbElement : list) {
			QName qName = jaxbElement.getName();
			String localPart = qName.getLocalPart();
			if (localPart == "MandatoryInput") {
				inputNames.add(hash.get(jaxbElement.getValue()).getPresentationName());
			} else if (localPart == "Output") {
				outputNames.add(hash.get(jaxbElement.getValue()).getPresentationName());
			} else if (localPart == "PerformedPrimarilyBy") {
				performedPrimarilyBy.add(hash.get(jaxbElement.getValue()).getPresentationName());
			} else if (localPart == "AdditionallyPerformedBy") {
				additionallyPerformedBy.add(hash.get(jaxbElement.getValue()).getPresentationName());
			}
		}
		Boolean isNew = new Boolean(false);
		ProcessContentRepository task = createProcessContentRepository(taskDescriptor, ProcessContentType.TASK, isNew,
				hash);

		for (String name : inputNames) {
			MethodContentRepository input = createMethodContentRepository(name, MethodContentType.ARTIFACT, isNew);
			task.addInputMethodContent(input);
			input.setProcessContentRepository(task);
			wordProductList.add(input.getName());
		}

		for (String name : outputNames) {
			MethodContentRepository output = createMethodContentRepository(name, MethodContentType.ARTIFACT, isNew);
			task.addOutputMethodContent(output);
			output.setProcessContentRepository(task);
			wordProductList.add(output.getName());
		}

		for (String name : performedPrimarilyBy) {
			MethodContentRepository role = createMethodContentRepository(name, MethodContentType.ROLE, isNew);
			task.setMainRole(role);
			rolesList.add(role.getName());
			role.setProcessContentRepository(task);
		}
		List<MethodContentRepository> additionallyPerformedByList = new ArrayList<>();
		for (String name : additionallyPerformedBy) {
			MethodContentRepository role = createMethodContentRepository(name, MethodContentType.ROLE, isNew);
			additionallyPerformedByList.add(role);
			rolesList.add(role.getName());
			role.setProcessContentRepository(task);
		}
		task.setAdditionalRoles(additionallyPerformedByList);
		List<ProcessContentRepository> predecessorsList = new ArrayList<>();
		for (WorkOrder workOrder : taskDescriptor.getPredecessor()) {
			WorkBreakdownElement element = (WorkBreakdownElement) hash.get(workOrder.getValue());
			if (element instanceof TaskDescriptor) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(element,
						ProcessContentType.TASK, isNew, hash);
				predecessorsList.add(predecessorRepository);
			} else if (element instanceof Activity) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(element,
						ProcessContentType.ACTIVITY, isNew, hash);
				predecessorsList.add(predecessorRepository);
			} else if (element instanceof Iteration) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(element,
						ProcessContentType.ITERATION, isNew, hash);
				predecessorsList.add(predecessorRepository);
			} else if (element instanceof Milestone) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(element,
						ProcessContentType.MILESTONE, isNew, hash);
				predecessorsList.add(predecessorRepository);
			} else if (element instanceof Phase) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(element,
						ProcessContentType.PHASE, isNew, hash);
				predecessorsList.add(predecessorRepository);
			}
		}
		task.setPredecessors(predecessorsList);
		return task;
	}

	private ProcessContentRepository createProcessContentRepository(WorkBreakdownElement element,
			ProcessContentType type, Boolean isNew, Map<String, MethodElement> hash) {
		if (processContentRepositoryHashMap.containsKey(element.getName())) {
			isNew = false;
			return processContentRepositoryHashMap.get(element.getName());
		}
		ProcessContentRepository processContentRepository = new ProcessContentRepository();
		processContentRepository.setName(element.getPresentationName());
		processContentRepository.setType(type);
		processContentRepositoryHashMap.put(element.getPresentationName(), processContentRepository);
		isNew = true;
		List<ProcessContentRepository> predecessorsList = new ArrayList<>();
		for (WorkOrder workOrder : element.getPredecessor()) {
			WorkBreakdownElement predecessor = (WorkBreakdownElement) hash.get(workOrder.getValue());
			if (predecessor instanceof Iteration) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.ITERATION, isNew, hash);
				predecessorsList.add(predecessorRepository);
			}
			if (predecessor instanceof Activity) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.ACTIVITY, isNew, hash);
				predecessorsList.add(predecessorRepository);
			}
			if (predecessor instanceof Phase) {
				ProcessContentRepository predecessorRepository = createProcessContentRepository(predecessor,
						ProcessContentType.PHASE, isNew, hash);
				predecessorsList.add(predecessorRepository);
			}
		}
		processContentRepository.setPredecessors(predecessorsList);
		return processContentRepository;
	}

	private MethodContentRepository createMethodContentRepository(String name, MethodContentType type, Boolean isNew) {
		if (methodContentRepositoryHashMap.containsKey(name)) {
			isNew = false;
			return methodContentRepositoryHashMap.get(name);
		}
		MethodContentRepository methodContentRepository = new MethodContentRepository();
		methodContentRepository.setName(name);
		methodContentRepository.setType(type);
		isNew = true;
		return methodContentRepository;
	}

	// public static void main(String[] args) throws IOException {
	// PersistProcess persistProcess = new PersistProcess();
	// MethodLibraryWrapper methodLibraryWrapper = new MethodLibraryWrapper();
	// methodLibraryWrapper.load(new
	// File("input/process_alternatives_epf/xp_base.xml"));
	// MethodPackage methodPackage =
	// methodLibraryWrapper.getMethodLibrary().getMethodPlugin().get(0)
	// .getMethodPackage().get(1);
	// Process process = ((ProcessComponent) methodPackage).getProcess();
	// ProcessRepository processRepository =
	// persistProcess.buildProcess(process);
	// persistProcess.persist(processRepository, "output.xml");
	// }

	public HashMap<String, UpdatePanel> buildGUI(ProcessRepository processRepository,
			RepositoryViewPanel repositoryViewPanel, List<String> keySet) {
		HashMap<String, UpdatePanel> hashMap = new HashMap<>();

		for (ProcessContentRepository content : processRepository.getProcessContents()) {
			if (content.getType() == ProcessContentType.ACTIVITY) {
				buildGUISession(content, hashMap, repositoryViewPanel, keySet);
			} else if (content.getType() == ProcessContentType.ITERATION) {
				buildGUISession(content, hashMap, repositoryViewPanel, keySet);
			} else if (content.getType() == ProcessContentType.PHASE) {
				buildGUISession(content, hashMap, repositoryViewPanel, keySet);
			} else if (content.getType() == ProcessContentType.TASK) {
				buildTaskPanel(content, hashMap, repositoryViewPanel, keySet);
			} else if (content.getType() == ProcessContentType.MILESTONE) {
				buildGUISession(content, hashMap, repositoryViewPanel, keySet);
			}
		}
		return hashMap;
	}

	private void buildGUITask(ProcessContentRepository content, TaskPanel taskPanel,
			RepositoryViewPanel repositoryViewPanel) {
		for (MethodContentRepository methodContentRepository : content.getInputMethodContentsRepository()) {
			InputWorkProductPanel inputWorkProductPanel = new InputWorkProductPanel(repositoryViewPanel,
					methodContentRepository);
			inputWorkProductPanel.setInputWorkProductLabel(methodContentRepository.getName());
			inputWorkProductPanel.setSizeDistribuiton(BestFitDistribution.getList());
			taskPanel.addPanel(inputWorkProductPanel);
		}
		for (MethodContentRepository methodContentRepository : content.getOutputMethodContentsRepository()) {
			OutputWorkProductPanel outputWorkProductPanel = new OutputWorkProductPanel(repositoryViewPanel,
					methodContentRepository);
			outputWorkProductPanel.setOutputWorkProductLabel(methodContentRepository.getName());

			outputWorkProductPanel.setSizeDistribuiton(BestFitDistribution.getList());
			taskPanel.addPanel(outputWorkProductPanel);
		}
		MethodContentRepository role = content.getMainRole();
		RolePanel rolePanel = new RolePanel(repositoryViewPanel, role);
		rolePanel.setRoleLabel(role.getName());
		rolePanel.setDistribution(BestFitDistribution.getList());
		taskPanel.addPanel(rolePanel);
		for (MethodContentRepository methodContentRepository : content.getAdditionalRoles()) {
			RolePanel panel = new RolePanel(repositoryViewPanel, methodContentRepository);
			panel.setRoleLabel(methodContentRepository.getName());
			panel.setDistribution(BestFitDistribution.getList());
			taskPanel.addPanel(panel);
		}

	}

	private void buildGUISession(ProcessContentRepository content, HashMap<String, UpdatePanel> hashMap,
			RepositoryViewPanel repositoryViewPanel, List<String> keySet) {
		MeetingPanel sessionPanel = new MeetingPanel(repositoryViewPanel, content);
		sessionPanel.setSessionTitle(content.getName());
		hashMap.put(content.getName(), sessionPanel);
		keySet.add(content.getName());
		sessionPanel.setDistribution(BestFitDistribution.getList());
		for (ProcessContentRepository processContentRepository : content.getChildren()) {
			buildChildGUI(processContentRepository, hashMap, repositoryViewPanel, keySet);
		}
	}

	private void buildChildGUI(ProcessContentRepository content, HashMap<String, UpdatePanel> hashMap,
			RepositoryViewPanel repositoryViewPanel, List<String> keySet) {
		if (content.getType() == ProcessContentType.ACTIVITY) {
			buildGUISession(content, hashMap, repositoryViewPanel, keySet);
		} else if (content.getType() == ProcessContentType.ITERATION) {
			buildGUISession(content, hashMap, repositoryViewPanel, keySet);
		} else if (content.getType() == ProcessContentType.PHASE) {
			buildGUISession(content, hashMap, repositoryViewPanel, keySet);
		} else if (content.getType() == ProcessContentType.TASK) {
			buildTaskPanel(content, hashMap, repositoryViewPanel, keySet);
		}
	}

	private void buildTaskPanel(ProcessContentRepository content, HashMap<String, UpdatePanel> hashMap,
			RepositoryViewPanel repositoryViewPanel, List<String> keySet) {
		TaskPanel taskPanel = new TaskPanel();
		SessionPanel sessionPanel = new SessionPanel(repositoryViewPanel, content);
		sessionPanel.setDistribution(BestFitDistribution.getList());
		sessionPanel.setMeasurementUnity(TimeEnum.getList());
		sessionPanel.setSessionTitle(content.getName());
		taskPanel.addPanel(sessionPanel);
		//buildGUITask(content, taskPanel, repositoryViewPanel);
		hashMap.put(content.getName(), taskPanel);
		keySet.add(content.getName());
	}

	public Set<String> getRolesList() {
		return rolesList;
	}

	public Set<String> getWordProductList() {
		return wordProductList;
	}
}