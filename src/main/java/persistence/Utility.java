package persistence;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.SimulationFacade;
import model.spem.util.MethodContentType;
import model.spem.util.ProcessContentType;

public class Utility {

    private ProcessRepository processAlternative;
    // private WorkBreakDownStructureRepository wbs;
    private ProcessContentRepository release;
    private ProcessContentRepository iteration;
    private ProcessContentRepository sprintPlanning;
    private ProcessContentRepository development;

    private ProcessContentRepository taskDescriptorPrioritize;
    private ProcessContentRepository taskDescriptorSplitting;
    private ProcessContentRepository taskDescriptorEstimating;

    private ProcessContentRepository taskDescriptorSoloProgrammingTestFirst;
    private ProcessContentRepository taskDescriptorSoloProgrammingTestAfter;

    private ProcessContentRepository taskDescriptorPairProgrammingTestFirst;
    private ProcessContentRepository taskDescriptorPairProgrammingTestAfter;

    private ProcessContentRepository taskDescriptorDebugging;
    private ProcessContentRepository taskDescriptorRefactoring;

    private MethodContentRepository epic;
    private MethodContentRepository userStory;
    private MethodContentRepository customer;
    private MethodContentRepository bug;
    private MethodContentRepository developer;
    private MethodContentRepository code;

    public Utility() {
	epic = new MethodContentRepository();
	epic.setName("Epic");
	epic.setType(MethodContentType.ARTIFACT);

	userStory = new MethodContentRepository();
	userStory.setName("User Story");
	userStory.setType(MethodContentType.ARTIFACT);

	customer = new MethodContentRepository();
	customer.setName("Customer");
	customer.setType(MethodContentType.ROLE);

	bug = new MethodContentRepository();
	bug.setName("Bug");
	bug.setType(MethodContentType.ARTIFACT);

	developer = new MethodContentRepository();
	developer.setName("Developer");
	developer.setType(MethodContentType.ROLE);

	code = new MethodContentRepository();
	code.setName("Code");
	code.setType(MethodContentType.ARTIFACT);
    }

    private ProcessContentRepository createProcessContentPrioritizeUserStories() {

	ProcessContentRepository taskDescriptorPrioritize = new ProcessContentRepository();
	taskDescriptorPrioritize.setName("Prioritizing user stories");
	taskDescriptorPrioritize.setType(ProcessContentType.TASK);

	taskDescriptorPrioritize.addInputMethodContent(epic);

	// epic.setProcessContentRepository(taskDescriptorPrioritize);

	taskDescriptorPrioritize.addOutputMethodContent(epic);
	epic.setProcessContentRepository(taskDescriptorPrioritize);

	taskDescriptorPrioritize.setMainRole(customer);
	taskDescriptorPrioritize.setAdditionalRoles(null);
	customer.setProcessContentRepository(taskDescriptorPrioritize);

	return taskDescriptorPrioritize;
    }

    private ProcessContentRepository createProcessElementSplitUserStories() {

	taskDescriptorSplitting = new ProcessContentRepository();
	taskDescriptorSplitting.setName("Spliting user stories");
	taskDescriptorSplitting.setType(ProcessContentType.TASK);

	taskDescriptorSplitting.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorSplitting);

	taskDescriptorSplitting.addInputMethodContent(epic);
	epic.setProcessContentRepository(taskDescriptorSplitting);

	taskDescriptorSplitting.setMainRole(customer);
	customer.setProcessContentRepository(taskDescriptorSplitting);

	return taskDescriptorSplitting;
    }

    private ProcessContentRepository createProcessElementEstimatingUserStories() {

	taskDescriptorEstimating = new ProcessContentRepository();
	taskDescriptorEstimating.setName("Estimating user stories");
	taskDescriptorEstimating.setType(ProcessContentType.TASK);

	taskDescriptorEstimating.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorEstimating);

	taskDescriptorEstimating.addOutputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorEstimating);

	taskDescriptorEstimating.setMainRole(customer);
	customer.setProcessContentRepository(taskDescriptorEstimating);

	return taskDescriptorEstimating;
    }

    private ProcessContentRepository createProcessElementSoloProgrammingTestFirst() {

	taskDescriptorSoloProgrammingTestFirst = new ProcessContentRepository();
	taskDescriptorSoloProgrammingTestFirst.setName("Solo programming with test first");
	taskDescriptorSoloProgrammingTestFirst.setType(ProcessContentType.TASK);

	taskDescriptorSoloProgrammingTestFirst.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorSoloProgrammingTestFirst);

	taskDescriptorSoloProgrammingTestFirst.addOutputMethodContent(userStory);
	taskDescriptorSoloProgrammingTestFirst.addOutputMethodContent(bug);
	userStory.setProcessContentRepository(taskDescriptorSoloProgrammingTestFirst);

	taskDescriptorSoloProgrammingTestFirst.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorSoloProgrammingTestFirst);

	return taskDescriptorSoloProgrammingTestFirst;
    }

    private ProcessContentRepository createProcessElementSoloProgrammingTestAfter() {

	taskDescriptorSoloProgrammingTestAfter = new ProcessContentRepository();
	taskDescriptorSoloProgrammingTestAfter.setName("Solo programming with test after");
	taskDescriptorSoloProgrammingTestAfter.setType(ProcessContentType.TASK);

	taskDescriptorSoloProgrammingTestAfter.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorSoloProgrammingTestAfter);

	taskDescriptorSoloProgrammingTestAfter.addOutputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorSoloProgrammingTestAfter);

	taskDescriptorSoloProgrammingTestAfter.addOutputMethodContent(bug);
	bug.setProcessContentRepository(taskDescriptorSoloProgrammingTestAfter);

	taskDescriptorSoloProgrammingTestAfter.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorSoloProgrammingTestAfter);

	return taskDescriptorSoloProgrammingTestAfter;
    }

    private ProcessContentRepository createProcessElementPairProgrammingTestFirst() {

	taskDescriptorPairProgrammingTestFirst = new ProcessContentRepository();
	taskDescriptorPairProgrammingTestFirst.setName("Pair programming with test first");
	taskDescriptorPairProgrammingTestFirst.setType(ProcessContentType.TASK);

	taskDescriptorPairProgrammingTestFirst.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorPairProgrammingTestFirst);

	taskDescriptorPairProgrammingTestFirst.addOutputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorPairProgrammingTestFirst);

	taskDescriptorPairProgrammingTestFirst.addOutputMethodContent(bug);
	bug.setProcessContentRepository(taskDescriptorPairProgrammingTestFirst);

	taskDescriptorPairProgrammingTestFirst.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorPairProgrammingTestFirst);

	return taskDescriptorPairProgrammingTestFirst;
    }

    private ProcessContentRepository createProcessElementPairProgrammingTestAfter() {

	taskDescriptorPairProgrammingTestAfter = new ProcessContentRepository();
	taskDescriptorPairProgrammingTestAfter.setName("Pair programming with test after");
	taskDescriptorPairProgrammingTestAfter.setType(ProcessContentType.TASK);

	taskDescriptorPairProgrammingTestAfter.addInputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorPairProgrammingTestAfter);

	taskDescriptorPairProgrammingTestAfter.addOutputMethodContent(userStory);
	userStory.setProcessContentRepository(taskDescriptorPairProgrammingTestAfter);

	taskDescriptorPairProgrammingTestAfter.addOutputMethodContent(bug);
	bug.setProcessContentRepository(taskDescriptorPairProgrammingTestAfter);

	taskDescriptorPairProgrammingTestAfter.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorPairProgrammingTestAfter);

	// taskDescriptorPairProgrammingTestAfter.setMainRole(developer);
	// taskDescriptorPairProgrammingTestAfter.addMandatoryWorkProductDescriptor(inputUserStory);
	// taskDescriptorPairProgrammingTestAfter.addOutputWorkProductDescriptor(outputUserStory);
	// taskDescriptorPairProgrammingTestAfter.addOutputWorkProductDescriptor(outputBug);

	return taskDescriptorPairProgrammingTestAfter;
    }

    private ProcessContentRepository createProcessElementDebugging() {

	taskDescriptorDebugging = new ProcessContentRepository();
	taskDescriptorDebugging.setName("Debugging");
	taskDescriptorDebugging.setType(ProcessContentType.TASK);

	taskDescriptorDebugging.addInputMethodContent(bug);
	bug.setProcessContentRepository(taskDescriptorDebugging);

	taskDescriptorDebugging.addOutputMethodContent(code);
	code.setProcessContentRepository(taskDescriptorDebugging);

	taskDescriptorDebugging.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorDebugging);

	// taskDescriptorDebugging.setMainRole(developer);
	// taskDescriptorDebugging.addMandatoryWorkProductDescriptor(bug);
	// taskDescriptorDebugging.addOutputWorkProductDescriptor(outputCode);

	return taskDescriptorDebugging;
    }

    private ProcessContentRepository createProcessElementRefactoring() {

	taskDescriptorRefactoring = new ProcessContentRepository();
	taskDescriptorRefactoring.setName("Refactoring");
	taskDescriptorRefactoring.setType(ProcessContentType.TASK);

	taskDescriptorRefactoring.addInputMethodContent(bug);
	bug.setProcessContentRepository(taskDescriptorRefactoring);

	taskDescriptorRefactoring.addOutputMethodContent(code);
	code.setProcessContentRepository(taskDescriptorRefactoring);

	taskDescriptorRefactoring.setMainRole(developer);
	developer.setProcessContentRepository(taskDescriptorRefactoring);

	return taskDescriptorRefactoring;
    }

    public ProcessRepository buildProcess(String processAlternativeName) {

	processAlternative = new ProcessRepository();
	processAlternative.setName(processAlternativeName);

	// wbs = new WorkBreakDownStructureRepository();
	// wbs.setName("Workbreakdown structure");

	release = new ProcessContentRepository();
	release.setName("Release [1..n]");
	release.setType(ProcessContentType.ACTIVITY);

	iteration = new ProcessContentRepository();
	iteration.setName("Iteration [1..n]");
	iteration.setType(ProcessContentType.ITERATION);

	sprintPlanning = new ProcessContentRepository();
	sprintPlanning.setName("Sprint planning");
	sprintPlanning.setType(ProcessContentType.ACTIVITY);

	development = new ProcessContentRepository();
	development.setName("Development");
	development.setType(ProcessContentType.ACTIVITY);

	taskDescriptorPrioritize = createProcessContentPrioritizeUserStories();

	taskDescriptorSplitting = createProcessElementSplitUserStories();

	taskDescriptorEstimating = createProcessElementEstimatingUserStories();

	taskDescriptorSoloProgrammingTestFirst = createProcessElementSoloProgrammingTestFirst();

	taskDescriptorSoloProgrammingTestAfter = createProcessElementSoloProgrammingTestAfter();

	taskDescriptorPairProgrammingTestFirst = createProcessElementPairProgrammingTestFirst();

	taskDescriptorPairProgrammingTestAfter = createProcessElementPairProgrammingTestAfter();

	taskDescriptorDebugging = createProcessElementDebugging();

	taskDescriptorRefactoring = createProcessElementRefactoring();

	// Prioritize
	taskDescriptorPrioritize.setFather(sprintPlanning);
	taskDescriptorPrioritize.setProcessRepository(processAlternative);

	// Splitting
	taskDescriptorSplitting.setFather(sprintPlanning);
	taskDescriptorSplitting.setPredecessor(taskDescriptorPrioritize);
	taskDescriptorSplitting.setProcessRepository(processAlternative);

	// Estimating
	taskDescriptorEstimating.setFather(sprintPlanning);
	taskDescriptorEstimating.setPredecessor(taskDescriptorSplitting);
	taskDescriptorEstimating.setProcessRepository(processAlternative);

	// Sprint planning
	sprintPlanning.addChild(taskDescriptorPrioritize);
	sprintPlanning.addChild(taskDescriptorSplitting);
	sprintPlanning.addChild(taskDescriptorEstimating);

	sprintPlanning.setFather(iteration);
	sprintPlanning.setProcessRepository(processAlternative);

	// Solo programming test first
	taskDescriptorSoloProgrammingTestFirst.setFather(development);
	taskDescriptorSoloProgrammingTestFirst.setProcessRepository(processAlternative);

	// Solo programming test after
	taskDescriptorSoloProgrammingTestAfter.setFather(development);
	taskDescriptorSoloProgrammingTestAfter.setProcessRepository(processAlternative);

	// Pair programming test first
	taskDescriptorPairProgrammingTestFirst.setFather(development);
	taskDescriptorPairProgrammingTestFirst.setProcessRepository(processAlternative);

	// Pair programming test after
	taskDescriptorPairProgrammingTestAfter.setFather(development);
	taskDescriptorPairProgrammingTestAfter.setProcessRepository(processAlternative);

	// Debugging
	taskDescriptorDebugging.setFather(development);
	taskDescriptorDebugging.setProcessRepository(processAlternative);

	// Refactoring
	taskDescriptorRefactoring.setFather(development);
	taskDescriptorRefactoring.setProcessRepository(processAlternative);

	// Development
	development.setFather(iteration);
	// development.addPredecessor(sprintPlanning);
	development.setProcessRepository(processAlternative);

	development.setProcessRepository(processAlternative);

	development.addChild(taskDescriptorSoloProgrammingTestFirst);
	development.addChild(taskDescriptorSoloProgrammingTestAfter);

	development.addChild(taskDescriptorPairProgrammingTestFirst);
	development.addChild(taskDescriptorPairProgrammingTestAfter);

	development.addChild(taskDescriptorDebugging);
	development.addChild(taskDescriptorRefactoring);

	// Iteration
	iteration.setFather(release);
	iteration.addChild(sprintPlanning);
	iteration.addChild(development);
	iteration.setProcessRepository(processAlternative);

	// release
	release.addChild(iteration);
	release.setProcessRepository(processAlternative);

	// Parameters (so no xml)
	// Parameters parameters = new Parameters();
	// parameters.setMean(10.0);
	// parameters.setStandardDeviation(3.5);
	//
	// // Measurement
	// DurationMeasurement measurement = new DurationMeasurement();
	// measurement.setValue(5.0);
	// measurement.setScale(TimeEnum.DAYS);
	//
	// DurationMeasurement measurement2 = new DurationMeasurement();
	// measurement2.setValue(8.0);
	// measurement2.setScale(TimeEnum.DAYS);

	// sample
	// Sample sample = new Sample();
	// sample.setName("Release sample");
	//
	// sample.addMeasurement(measurement);
	// sample.addMeasurement(measurement2);
	//
	// sample.computeDistribution();
	// sample.setParameters(parameters);
	// sample.setSize(200);
	// measurement.setSample(sample);
	// measurement2.setSample(sample);

	// Distribution (Para testar o XML gerado. Nao usado no RestFacade por
	// ser atributo derivado e nao ter no banco)

	// release.setSample(sample);
	// sample.setProcessElementRepository(release);

	processAlternative.addProcessElement(release);
	// processAlternative.addProcessElement(iteration);
	// processAlternative.addProcessElement(sprintPlanning);
	// processAlternative.addProcessElement(development);
	// processAlternative.addProcessElement(taskDescriptorPrioritize);
	// processAlternative.addProcessElement(taskDescriptorSplitting);
	// processAlternative.addProcessElement(taskDescriptorEstimating);
	// processAlternative.addProcessElement(taskDescriptorSoloProgrammingTestFirst);
	// processAlternative.addProcessElement(taskDescriptorSoloProgrammingTestAfter);
	// processAlternative.addProcessElement(taskDescriptorPairProgrammingTestFirst);
	// processAlternative.addProcessElement(taskDescriptorPairProgrammingTestAfter);
	// processAlternative.addProcessElement(taskDescriptorDebugging);
	// processAlternative.addProcessElement(taskDescriptorRefactoring);
	// process
	// processAlternative.setWbs(wbs);
	processAlternative.setChosen(false);

	// sample
	// SampleCreation.generateSampleDistribution(taskDescriptorSplitting);

	return processAlternative;
    }

    public void persistProcessInXMLWithJAXB(ProcessRepository processRepository, String fileName) throws IOException {
	try {
	    JAXBContext context = JAXBContext.newInstance(SimulationFacade.class);
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    StringWriter sw = new StringWriter();
	    marshaller.marshal(processRepository, sw);

	    // Only works if deployed outside a Java EE container
	    File f = new File("./output/spem_uma_simplified/" + fileName);
	    marshaller.marshal(processRepository, f);

	    System.out.println(sw.toString());
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws IOException {

	Utility t = new Utility();
	ProcessRepository processRepository = t.buildProcess("Coyote agile process");
	t.persistProcessInXMLWithJAXB(processRepository, "processCreatedUtilityClass.xml");

    }
}
