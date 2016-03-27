package simulator.simple;

import java.util.HashMap;
import java.util.List;

import adaptme.util.SimulationSample;
import executive.Executive;
import executive.entity.Entity;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.config.ContainerConfig;
import model.spem.config.TaskConfig;
import model.spem.config.WorkProductConfig;
import model.spem.derived.Parameters;
import model.spem.util.FinishType;
import model.spem.util.ProcessContentType;
import simulator.base.WorkProductXACDML;
import simulator.simple.entity.Developer;
import simulator.simple.entity.SimpleProject;
import simulator.simple.task.SessionEnd;
import simulator.simple.task.SessionStart;
import simulator.utils.ApplicationContext;
import simulator.utils.log.Logger;

public class Simple implements Logger {

    private ProcessRepository processRepository;
    private Executive executive;
    private SimpleProject project;
    private ApplicationContext applicationContext;

    private HashMap<String, Entity> conteinerEntityHash;

    private HashMap<String, WorkProductConfig> workProductMeasurementConfigHash;
    private HashMap<String, ContainerConfig> containerMeasurementConfigHash;
    private HashMap<String, TaskConfig> taskMeasurementConfigHash;

    private HashMap<String, List<WorkProductXACDML>> workProductHash;
    private HashMap<String, List<Developer>> developersHash;

    // public static void main(String[] args) throws IOException {
    // PersistProcess persistProcess = new PersistProcess();
    // MethodLibraryWrapper methodLibraryWrapper = new MethodLibraryWrapper();
    // methodLibraryWrapper.load(new
    // File("input/process_alternatives_epf/thesis.xml"));
    // MethodPackage methodPackage =
    // methodLibraryWrapper.getMethodLibrary().getMethodPlugin().get(0)
    // .getMethodPackage().get(2);
    // Process process = ((ProcessComponent) methodPackage).getProcess();
    // ProcessRepository processRepository =
    // persistProcess.buildProcess(process);
    // persistProcess.persist(processRepository, "teste.xml");
    //
    // //HashMap<String, RoleConfig> roleMeasurementConfigHash = new
    // HashMap<>();
    // HashMap<String, WorkProductConfig> workProductMeasurementConfigHash = new
    // HashMap<>();
    // HashMap<String, ContainerConfig> containerMeasurementConfigHash = new
    // HashMap<>();
    // HashMap<String, TaskConfig> taskMeasurementConfigHash = new HashMap<>();
    // HashMap<String, List<WorkProduct>> workProductHash = new HashMap<>();
    // HashMap<String, List<Developer>> developersHash = new HashMap<>();
    //
    // List<Developer> developersList = new ArrayList<>();
    // for (int i = 0; i < 4; i++) {
    // Developer developer = new Developer("Developer " + i);
    // developersList.add(developer);
    // }
    // developersHash.put("Developer", developersList);
    //
    // developersList = new ArrayList<>();
    // for (int i = 0; i < 1; i++) {
    // Developer developer = new Developer("Customer " + i);
    // developersList.add(developer);
    // }
    // developersHash.put("Customer", developersList);
    //
    // ContainerConfig sprintPlanning = new ContainerConfig();
    // sprintPlanning.setFinishStatus("Split Macro User Stories");
    // containerMeasurementConfigHash.put(sprintPlanning.getName(),
    // sprintPlanning);
    //
    // TaskConfig prioritizeUserStories = new TaskConfig();
    // List<String> list = new ArrayList<>();
    // list.add("");
    // prioritizeUserStories.setWorkProductDependencies(list);
    // prioritizeUserStories.setFinishType(FinishType.STATUS);
    // prioritizeUserStories.setPredecessorType(PredecessorType.STATUS);
    // taskMeasurementConfigHash.put(prioritizeUserStories.getName(),
    // prioritizeUserStories);
    //
    // TaskConfig estimateUserStories = new TaskConfig();
    // list = new ArrayList<>();
    // list.add("Prioritize User Stories");
    // estimateUserStories.setWorkProductDependencies(list);
    // estimateUserStories.setFinishType(FinishType.STATUS);
    // estimateUserStories.setPredecessorType(PredecessorType.STATUS);
    // taskMeasurementConfigHash.put(estimateUserStories.getName(),
    // estimateUserStories);
    //
    // TaskConfig splitMacroUserStories = new TaskConfig();
    // list = new ArrayList<>();
    // list.add("Estimate User Stories");
    // splitMacroUserStories.setWorkProductDependencies(list);
    // splitMacroUserStories.setFinishType(FinishType.STATUS);
    // splitMacroUserStories.setPredecessorType(PredecessorType.STATUS);
    // taskMeasurementConfigHash.put(splitMacroUserStories.getName(),
    // splitMacroUserStories);
    //
    // TaskConfig soloTestAfter = new TaskConfig();
    // list = new ArrayList<>();
    // list.add("Split Macro User Stories");
    // soloTestAfter.setWorkProductDependencies(list);
    // soloTestAfter.setFinishType(FinishType.SIZE);
    // soloTestAfter.setPredecessorType(PredecessorType.SIZE);
    // taskMeasurementConfigHash.put(soloTestAfter.getName(), soloTestAfter);
    //
    // List<WorkProduct> listWorkProduct = new ArrayList<WorkProduct>();
    // for (int i = 0; i < 10; i++) {
    // WorkProduct workProduct = new WorkProduct();
    // workProduct.setName("User Story " + i);
    // workProduct.setSize((int)
    // SimulationSample.getSampleFromLogNormalDistribution(60, 5));
    // workProduct.setStatus("");
    // listWorkProduct.add(workProduct);
    // }
    // workProductHash.put(prioritizeUserStories.getName(), listWorkProduct);
    // workProductHash.put(estimateUserStories.getName(), listWorkProduct);
    // workProductHash.put(splitMacroUserStories.getName(), listWorkProduct);
    // workProductHash.put(soloTestAfter.getName(), listWorkProduct);
    //
    // Simple simple = new Simple(processRepository);
    // simple.setContainerMeasurementConfigHash(containerMeasurementConfigHash);
    // simple.setWorkProductMeasurementConfigHash(workProductMeasurementConfigHash);
    // simple.setTaskMeasurementConfigHash(taskMeasurementConfigHash);
    // simple.setWorkProductHash(workProductHash);
    // simple.setDevelopersHash(developersHash);
    //
    // simple.goForIt();
    // }

    public Simple(ProcessRepository processRepository) {
	this.processRepository = processRepository;
	executive = new Executive();

	project = new SimpleProject(processRepository.getName());
	applicationContext = new ApplicationContext(executive, project, this);

	conteinerEntityHash = new HashMap<>();
	project.setConteinerEntityHash(conteinerEntityHash);
	project.setDuration(7600);
    }

    private void build(ProcessRepository processRepository) {
	for (ProcessContentRepository processContentRepository : processRepository.getProcessContents()) {
	    if (isContatiner(processContentRepository)) {
		buildContainer(processContentRepository);
	    } else if (processContentRepository.getType() == ProcessContentType.TASK) {
		buildTask(processContentRepository);
	    }
	}
    }

    public void build(ProcessContentRepository processContentRepository) {
	for (ProcessContentRepository child : processContentRepository.getChildren()) {
	    if (isContatiner(child)) {
		buildContainer(child);
	    } else if (child.getType() == ProcessContentType.TASK) {
		buildTask(child);
	    }
	}
    }

    private void buildTask(ProcessContentRepository processContentRepository) {

	if (!hasPredecessors(processContentRepository)) {
	    TaskConfig config = taskMeasurementConfigHash.get(processContentRepository.getName());
	    if (config == null) {
		return;
	    }
	    if (canTaskStart(processContentRepository)) {
		Parameters parameters = processContentRepository.getInputMethodContentsRepository().iterator().next()
			.getSample().getParameters();
		long duration = (long) Math.ceil(SimulationSample.getSampleFromLogNormalDistribution(20, 5));
		long finishAt = executive.getCurrentClockTime() + duration;
		SessionEnd sessionEnd = new SessionEnd(applicationContext, processContentRepository, duration,
			finishAt);
		Entity entity = new Entity(processContentRepository.getMainRole().getName()) {
		};
		String indentation = getIndentation(processContentRepository);
		println(indentation + processContentRepository.getName() + " with " + entity.getName()
			+ " will start at " + executive.getCurrentClockTime() + " and finish at " + finishAt);
		executive.schedule(entity, sessionEnd, duration);
		executive.addNewEntity(entity);
		conteinerEntityHash.put(processContentRepository.getName(), entity);
	    }
	    SessionStart sessionStart = new SessionStart(applicationContext, processContentRepository,
		    processContentRepository.getName());
	    executive.addC(sessionStart);
	} else {
	    SessionStart sessionStart = new SessionStart(applicationContext, processContentRepository,
		    processContentRepository.getName());
	    executive.addC(sessionStart);
	}
    }

    private boolean canTaskStart(ProcessContentRepository processContentRepository) {
	TaskConfig config = taskMeasurementConfigHash.get(processContentRepository.getName());
	if (config.getFinishType() == FinishType.STATUS) {
	    String currentStatus = workProductHash.get(processContentRepository.getName()).get(0).getStatus();
	    if (currentStatus.equals(config.getWorkProductDependencies().get(0))) {
		return true;
	    }
	}
	return false;
    }

    public void buildContainer(ProcessContentRepository processContentRepository) {
	if (!hasPredecessors(processContentRepository)) {
	    //Parameters parameters = processContentRepository.getSample().getParameters();
	    long duration = (long) Math.ceil(SimulationSample.getSampleFromLogNormalDistribution(20, 5));
	    long finishAt = executive.getCurrentClockTime() + duration;
	    SessionEnd sessionEnd = new SessionEnd(applicationContext, processContentRepository, duration, finishAt);
	    Entity entity = new Entity(processContentRepository.getName()) {
	    };
	    String indentation = getIndentation(processContentRepository);
	    println(indentation + entity.getName() + " will start at " + executive.getCurrentClockTime()
		    + " and finish at " + finishAt);
	    executive.schedule(entity, sessionEnd, duration);
	    executive.addNewEntity(entity);
	    conteinerEntityHash.put(processContentRepository.getName(), entity);

	    SessionStart sessionStart = new SessionStart(applicationContext, processContentRepository,
		    processContentRepository.getName());
	    executive.addC(sessionStart);
	} else {
	    SessionStart sessionStart = new SessionStart(applicationContext, processContentRepository,
		    processContentRepository.getName());
	    executive.addC(sessionStart);
	}
	build(processContentRepository);
    }

    public static String getIndentation(ProcessContentRepository processContentRepository) {
	ProcessContentRepository father = processContentRepository.getFather();
	String identetion = "";
	while (father != null) {
	    identetion += "\t";
	    father = father.getFather();
	}
	return identetion;
    }

    public static boolean hasPredecessors(ProcessContentRepository processContentRepository) {
	if (processContentRepository.getPredecessors() == null) {
	    return false;
	}
	if (processContentRepository.getPredecessors().isEmpty()) {
	    return false;
	}
	return true;
    }

    public static boolean isContatiner(ProcessContentRepository processContentRepository) {
	if (processContentRepository.getType() == ProcessContentType.ACTIVITY) {
	    return true;
	}
	if (processContentRepository.getType() == ProcessContentType.ITERATION) {
	    return true;
	}
	if (processContentRepository.getType() == ProcessContentType.PHASE) {
	    return true;
	}
	return false;
    }

    public void goForIt() {
	build(processRepository);
	project.setWorkProductHash(workProductHash);
	project.setSessionMeasurementConfigHash(containerMeasurementConfigHash);
	project.setTaskMeasurementConfigHash(taskMeasurementConfigHash);
	project.setDevelopersHash(developersHash);
	project.setSimple(this);
	while (!project.isFinalized()) {
	    executive.simulate();
	}
    }

    public HashMap<String, WorkProductConfig> getWorkProductMeasurementConfigHash() {
	return workProductMeasurementConfigHash;
    }

    public void setWorkProductMeasurementConfigHash(
	    HashMap<String, WorkProductConfig> workProductMeasurementConfigHash) {
	this.workProductMeasurementConfigHash = workProductMeasurementConfigHash;
    }

    public HashMap<String, ContainerConfig> getContainerMeasurementConfigHash() {
	return containerMeasurementConfigHash;
    }

    public void setContainerMeasurementConfigHash(HashMap<String, ContainerConfig> sessionMeasurementConfigHash) {
	containerMeasurementConfigHash = sessionMeasurementConfigHash;
    }

    @Override
    public void println(String message) {
	print(message + "\n");
    }

    @Override
    public void print(String message) {
	System.out.print(message);
    }

    @Override
    public void update(Object object) {
    }

    public HashMap<String, List<WorkProductXACDML>> getWorkProductHash() {
	return workProductHash;
    }

    public void setWorkProductHash(HashMap<String, List<WorkProductXACDML>> workProductHash) {
	this.workProductHash = workProductHash;
    }

    public HashMap<String, TaskConfig> getTaskMeasurementConfigHash() {
	return taskMeasurementConfigHash;
    }

    public void setTaskMeasurementConfigHash(HashMap<String, TaskConfig> taskMeasurementConfigHash) {
	this.taskMeasurementConfigHash = taskMeasurementConfigHash;
    }

    public HashMap<String, List<Developer>> getDevelopersHash() {
	return developersHash;
    }

    public void setDevelopersHash(HashMap<String, List<Developer>> developersHash) {
	this.developersHash = developersHash;
    }
}
