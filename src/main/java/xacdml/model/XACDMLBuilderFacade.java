package xacdml.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import adaptme.ui.dynamic.simulation.alternative.process.LocalViewBottomPanel;
import adaptme.ui.dynamic.simulation.alternative.process.LocalViewPanel;
import adaptme.ui.dynamic.simulation.alternative.process.MainPanelSimulationOfAlternativeOfProcess;
import adaptme.ui.window.perspective.GenerateActivityProbabilityDistributionPanel;
import adaptme.ui.window.perspective.RoleResourcesBottomPanel;
import adaptme.ui.window.perspective.RoleResourcesPanel;
import adaptme.ui.window.perspective.WorkProductResourcesGenerateActivityObserversPanel;
import adaptme.ui.window.perspective.WorkProductResourcesPanel;
import adaptme.ui.window.perspective.WorkProductResourcesQueueObserversPanel;
import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;
import model.spem.util.ProcessContentType;
import simulator.base.Role;
import simulator.base.WorkProductXACDML;
import xacdml.model.generated.Acd;
import xacdml.model.generated.Act;
import xacdml.model.generated.ActObserver;
import xacdml.model.generated.Class;
import xacdml.model.generated.Dead;
import xacdml.model.generated.Destroy;
import xacdml.model.generated.EntityClass;
import xacdml.model.generated.Generate;
import xacdml.model.generated.Next;
import xacdml.model.generated.ObjectFactory;
import xacdml.model.generated.Prev;
import xacdml.model.generated.QueueObserver;
import xacdml.model.generated.Simtime;
import xacdml.model.generated.Stat;
import xacdml.model.generated.Type;
import xacdml.model.generated.Whenprev;

public class XACDMLBuilderFacade {

	private Acd acd;
	private ObjectFactory factory;
	private Role role;
	private Class permanentEntity;
	private Dead deadPermanentEntity;
    private Type queueTypePermanentEntity;
    private List<QueueObserver> queueObservers;
    private QueueObserver queueObserver;
    
    private ActObserver actObserver;
    private List<ActObserver> listOfActivityObservers;
    
    private WorkProductXACDML workProduct;
	
	private Generate generateActivity;
	private Class temporaryEntity;
	private Stat distribution;
	private Dead deadTemporalEntity;
	private Type queueTypeTemporaryEntity;
	private Next nextDeadTemporaryEntityByGenerateActivity;
	private Next nextDeadTemporaryEntityQueueByRegularActivity;
	private Act regularActivity;
	private EntityClass entityClass;
	private EntityClass ec2;
	private Prev previous;
	private Next next;
	private Destroy destroyActivity;
	
	private ConstantParameters constantParameters;
	private UniformParameters uniformParameters;
	private NegativeExponential negativeExponential;
	private NormalParameters normalParameters;
	private PoissonParameters poissonParameters;
	
	private ProcessRepository calibratedProcessRepository;
	private List<ProcessContentRepository> completeListOfProcessContentRepository;
	private List<ProcessContentRepository> listOfProcessContentRepositoryTasks;
	
	
	private Parameters parametersDistributionRegularActivity;
	
	public XACDMLBuilderFacade(ProcessRepository calibratedProcessRepository) {
		this.calibratedProcessRepository = calibratedProcessRepository;
 	}
	 
	public String buildXACDML(MainPanelSimulationOfAlternativeOfProcess mainPanelSimulationOfAlternativeOfProcess,
			String acdId, String simTime, List<Role> roles, List<WorkProductXACDML> workProducts,
			RoleResourcesPanel roleResourcePanel, WorkProductResourcesPanel workProdutResourcesPanel) {

		createSimulationDuration(acdId, simTime);

		createPermanentEntitiesAndResourceQueues(roles, roleResourcePanel);

		createGenerateActivitiesAndQueuesForTemporaryEntities(workProdutResourcesPanel, workProducts);
		
		createRegularActivities(workProducts, mainPanelSimulationOfAlternativeOfProcess);

		createDestroyActivities(roles, workProducts);

		return generateXACDML();

	}

	private void createDestroyActivities(List<Role> roles, List<WorkProductXACDML> workProducts) {
		
		// Algorithm
		// 1 - Nao pode ser input de nenhuma tarefa SPEM (regular activity)
		// 2 - Nao podemos destruir entidades permanentes (Resource queues)
		// 3 - Nao pode estar em uma fila next de uma generate activity

		// Algorithm : search queues that have entities to be destroyed
		// Only XACDML metamodel
		// Queue can not be input to any regular activity (internal actiivy)
		
		boolean mayBeDestroyed1 = true;
		Object classToBedestroyed = null;
		List<Dead> queues = acd.getDead();
		List<Act> regularActivities = acd.getAct();

		String queueName;
		for (Dead dead : queues) {
			queueName = dead.getId();

			for (Act act : regularActivities) {
				List<EntityClass> entityClasses = act.getEntityClass();
				for (EntityClass entityClass : entityClasses) {
					Object previousQueueName = entityClass.getPrev();
					if (queueName.equals(previousQueueName)) {
						// it is input. cannot be destroyed
						mayBeDestroyed1 = false;
						classToBedestroyed = entityClass;
					}
				}
			}
			if (mayBeDestroyed1) {
				destroyActivity = factory.createDestroy();
				destroyActivity.setClazz(classToBedestroyed);
				acd.getDestroy().add(destroyActivity);
			}
			mayBeDestroyed1 = true;
		}
		
		// ############
//		boolean mayBeDestroyed = true;
//		boolean isPermanenteEntity = false;
//		
//		List<Class> listOfEntities = acd.getClazz();
//		Set<MethodContentRepository> setOfInputMethodContentRepository;
//		
//		for (Class clazz: listOfEntities) {
//			 
//			for (ProcessContentRepository processContentRepository : listOfProcessContentRepositoryTasks) {
//
//				setOfInputMethodContentRepository = processContentRepository.getInputMethodContentsRepository();
//
//				for (MethodContentRepository inputMethodContentRepository : setOfInputMethodContentRepository) {
//
//					if (inputMethodContentRepository.getName().equals(clazz.getId())) {  // 1																 
//						mayBeDestroyed = false;
//					}
//				}
//			}
//			
//			for (Role role : roles) {
//				if (role.getName().equals(clazz.getId()))  // 2
//					isPermanenteEntity = true;	
//					mayBeDestroyed = false;
//			}
//			
//			
//			
//			if ((mayBeDestroyed == true) && (isPermanenteEntity == false)){
//				// posso destruir apenas se nao for role
//				
//				destroyActivity = factory.createDestroy();
//				destroyActivity.setClazz(clazz);
//				System.out.println("destroying ...: " + clazz.getId());
////				previous.setDead(deadTemporalEntity);  // basta pegar o nome da fila do workproduct associado ao table model
////				destroyActivity.getPrev().add(previous);
//				acd.getDestroy().add(destroyActivity);
//			}
//			mayBeDestroyed = true;
//			isPermanenteEntity = false;
//		}
	}

	private void createRegularActivities(List<WorkProductXACDML> workProducts, MainPanelSimulationOfAlternativeOfProcess mainPanelSimulationOfAlternativeOfProcess) {
		 	
		completeListOfProcessContentRepository = calibratedProcessRepository.getProcessContents();
		calibratedProcessRepository.clearListOfTasks(); // Este metodo removeu um erro muito dificil que era a geracao de varias tarefas no xacdml duplicada
		listOfProcessContentRepositoryTasks = calibratedProcessRepository.getListProcessContentRepositoryWithTasksOnly(completeListOfProcessContentRepository);
 

		for (ProcessContentRepository processContentRepository : listOfProcessContentRepositoryTasks) {
			
			configureRegularActivity(processContentRepository);
		
			configureInputQueues(workProducts, processContentRepository);

			configureOutputQueues(processContentRepository);

			configureDistribution(processContentRepository);
			
			configureObservers(mainPanelSimulationOfAlternativeOfProcess, processContentRepository);
			
			acd.getAct().add(regularActivity);	
		}
	}

	private void configureRegularActivity(ProcessContentRepository processContentRepository) {
		regularActivity = factory.createAct();
		regularActivity.setId(processContentRepository.getName());
	}

	private void configureInputQueues(List<WorkProductXACDML> workProducts, ProcessContentRepository processContentRepository) {
		String queueNameTableModel = null;
		previous = factory.createPrev();
		Set<MethodContentRepository> setOfInputMethodContentRepository = processContentRepository.getInputMethodContentsRepository();
		
		// cada input de um processContentRepository é uma fila previa de uma atividade XACDML
		for (MethodContentRepository inputMethodContentRepository : setOfInputMethodContentRepository) {
			
			
			entityClass = factory.createEntityClass();  // preciso dos dois entity classes ou crio dinamicamente
			previous.setId(inputMethodContentRepository.getName() + " input queue");
			
			for (Dead d: acd.getDead()) {
				if (d.getId().equals(inputMethodContentRepository.getName() + " input queue")) {
					previous.setDead(d);  
					entityClass.setPrev(previous);
					regularActivity.getEntityClass().add(entityClass);
					 System.out.println("QUEUE: " + d.getId());
					 previous = factory.createPrev();
					 entityClass = factory.createEntityClass();
				}
			}				
		}
	}
	
private void configureInputQueues(ProcessContentRepository processContentRepository) {
		
		previous = factory.createPrev();
		Set<MethodContentRepository> setOfInputMethodContentRepository = processContentRepository.getInputMethodContentsRepository();
		
		// cada input de um processContentRepository é uma fila previa de uma atividade XACDML
		for (MethodContentRepository inputMethodContentRepository : setOfInputMethodContentRepository) {
			entityClass = factory.createEntityClass();  // preciso dos dois entity classes ou crio dinamicamente
			previous.setId(inputMethodContentRepository.getName() + " input queue");
			
			for (Dead d: acd.getDead()) {
				if (d.getId().equals(inputMethodContentRepository.getName() + " input queue")) {
					previous.setDead(d);  
					entityClass.setPrev(previous);
					regularActivity.getEntityClass().add(entityClass);
					 System.out.println("QUEUE: " + d.getId());
					 previous = factory.createPrev();
					 entityClass = factory.createEntityClass();
				}
			}				
		}
	}

	private void configureOutputQueues(ProcessContentRepository processContentRepository) {
		entityClass = factory.createEntityClass();  // preciso dos dois entity classes ou crio dinamicamente
		nextDeadTemporaryEntityQueueByRegularActivity = factory.createNext();
		
		Set<MethodContentRepository> setOfOutputMethodContentRepository;
		setOfOutputMethodContentRepository = processContentRepository.getOutputMethodContentsRepository();
		 
		// cada output de um processContentRepository é uma fila de saida de uma atividade XACDML
		for (MethodContentRepository mcr : setOfOutputMethodContentRepository) {
			 
			nextDeadTemporaryEntityQueueByRegularActivity.setId(mcr.getName() + " output queue");

			for (Dead d: acd.getDead() ) {
				if (d.getId().equals(mcr.getName() + " output queue")) {
					nextDeadTemporaryEntityQueueByRegularActivity.setDead(d);  
					entityClass.setNext(nextDeadTemporaryEntityQueueByRegularActivity);
					regularActivity.getEntityClass().add(entityClass);
					 System.out.println("QUEUE: " + d.getId());
					 nextDeadTemporaryEntityQueueByRegularActivity = factory.createNext();
					 entityClass = factory.createEntityClass();
				}
			}
		}
	}

	private void configureObservers(MainPanelSimulationOfAlternativeOfProcess mainPanelSimulationOfAlternativeOfProcess,
			ProcessContentRepository processContentRepository) {
		// configura os observer para as regular activities
		// Configruando os observers
		List<IntegratedLocalAndRepositoryViewPanel> listOfIntegratedLocalandRepositoryViewPanels = mainPanelSimulationOfAlternativeOfProcess
				.getListIntegratedLocalAndRepositoryViewPanel();

		for (IntegratedLocalAndRepositoryViewPanel i : listOfIntegratedLocalandRepositoryViewPanels) {
			if (processContentRepository.getName().equals(i.getName())) {
				listOfActivityObservers = null;
				LocalViewPanel localViewPanel = i.getLocalViewPanel();
				LocalViewBottomPanel localViewBottomPanel = localViewPanel.getLocalViewBottomPanel();
				 
				listOfActivityObservers = localViewBottomPanel.getObservers();
				for (ActObserver actObserver : listOfActivityObservers) {
					 
						regularActivity.getActObserver().add(actObserver);
					 
				}
			}
		}
	}

	private void configureDistribution(ProcessContentRepository processContentRepository) {
		parametersDistributionRegularActivity = processContentRepository.getSample().getParameters(); // talvez pegar direto do painel

		distribution = factory.createStat();

		if (parametersDistributionRegularActivity instanceof ConstantParameters) {
			constantParameters = (ConstantParameters) parametersDistributionRegularActivity;
			distribution = factory.createStat();
			distribution.setType("CONST");
			distribution.setParm1(Double.toString(constantParameters.getValue()));

		} else if (parametersDistributionRegularActivity instanceof UniformParameters) {
			uniformParameters = (UniformParameters) parametersDistributionRegularActivity;
			distribution = factory.createStat();
			distribution.setType("UNIFORM");
			distribution.setParm1(Double.toString(uniformParameters.getLow()));
			distribution.setParm2(Double.toString(uniformParameters.getHigh()));

		} else if (parametersDistributionRegularActivity instanceof NegativeExponential) {
			negativeExponential = (NegativeExponential) parametersDistributionRegularActivity;
			distribution = factory.createStat();
			distribution.setType("NEGEXP");
			distribution.setParm1(Double.toString(negativeExponential.getAverage()));

		} else if (parametersDistributionRegularActivity instanceof NormalParameters) {
			normalParameters = (NormalParameters) parametersDistributionRegularActivity;
			distribution = factory.createStat();
			distribution.setType("NORMAL");
			distribution.setParm1(Double.toString(normalParameters.getMean()));
			distribution.setParm2(Double.toString(normalParameters.getStandardDeviation()));

		} else if (parametersDistributionRegularActivity instanceof PoissonParameters) {
			poissonParameters = (PoissonParameters) parametersDistributionRegularActivity;
			distribution = factory.createStat();
			distribution.setType("POISSON");
			distribution.setParm1(Double.toString(poissonParameters.getMean()));
		}
		regularActivity.setStat(distribution);
	}

	// Em segundo lugar, configuro a distribuicao de probabilidade criada acima para a generate activity
	// (panel probability distribution parameters - painel x associado com o workproduct x)
	private void createGenerateActivitiesAndQueuesForTemporaryEntities(
			WorkProductResourcesPanel workProdutResourcesPanel, List<WorkProductXACDML> workProducts) {

		List<JPanel> listProbabilityDistributionPanel = workProdutResourcesPanel
				.getListOfProbabilityDistributionPanels();
		List<JPanel> listOfWorkProductResourcesBottomRightPanel = workProdutResourcesPanel
				.getListOfWorkProductResourcesBottomRightPanels();
		List<JPanel> listOfGenerateActivityObserversPanel = workProdutResourcesPanel
				.getListOfGenerateActivityWorkProductResourcesObserversPanel();

		for (int j = 0; j < workProducts.size(); j++) {

			// Para cada demand work product devo gerar:
			// temporary entity, generate activity (e observers), queue for
			// temporary entity (e observers), regular activities and destroy
			// activities

			workProduct = workProducts.get(j);

			generateActivity = factory.createGenerate();
			temporaryEntity = factory.createClass();
			distribution = factory.createStat();
			deadTemporalEntity = factory.createDead();
			queueTypeTemporaryEntity = factory.createType();
			nextDeadTemporaryEntityByGenerateActivity = factory.createNext();

			createTemporaryEntity();

			JTable workProductTable = workProdutResourcesPanel.getTableWorkProduct();

			if (workProduct.isGenerateActivity()) {
				generateActivity.setId(workProduct.getName());

				generateActivity.setClazz(temporaryEntity);

				GenerateActivityProbabilityDistributionPanel probabilityDistributionInnerPanel = (GenerateActivityProbabilityDistributionPanel) listProbabilityDistributionPanel
						.get(j);
				Parameters parametersDistributionGenerateActivity = probabilityDistributionInnerPanel.getParameters();

				if (parametersDistributionGenerateActivity instanceof ConstantParameters) {
					constantParameters = (ConstantParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("CONST");
					distribution.setParm1(Double.toString(constantParameters.getValue()));

				} else if (parametersDistributionGenerateActivity instanceof UniformParameters) {
					uniformParameters = (UniformParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("UNIFORM");
					distribution.setParm1(Double.toString(uniformParameters.getLow()));
					distribution.setParm2(Double.toString(uniformParameters.getHigh()));

				} else if (parametersDistributionGenerateActivity instanceof NegativeExponential) {
					negativeExponential = (NegativeExponential) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("NEGEXP");
					distribution.setParm1(Double.toString(negativeExponential.getAverage()));

				} else if (parametersDistributionGenerateActivity instanceof NormalParameters) {
					normalParameters = (NormalParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("NORMAL");
					distribution.setParm1(Double.toString(normalParameters.getMean()));
					distribution.setParm2(Double.toString(normalParameters.getStandardDeviation()));

				} else if (parametersDistributionGenerateActivity instanceof PoissonParameters) {
					poissonParameters = (PoissonParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("POISSON");
					distribution.setParm1(Double.toString(poissonParameters.getMean()));
				}

				// configura a generate activity com a distribuicao apropriada

				generateActivity.setStat(distribution);

				// Quarto: configuracao da fila para entidade temporaria
				// incluindo seu tipo (QUEUE, STACK or SET) e observers

				String queueName = workProduct.getQueueName();
				String queueTypeTemporaryEntityString = (workProductTable.getModel().getValueAt(j, 4)).toString();
				String queueCapacityTemporaryEntityString = workProductTable.getModel().getValueAt(j, 5).toString();
				String queueInitialQuantityTemporaryEntityString = workProductTable.getModel().getValueAt(j, 6)
						.toString();

				queueTypeTemporaryEntity.setStruct(queueTypeTemporaryEntityString);
				queueTypeTemporaryEntity.setSize(queueCapacityTemporaryEntityString);
				queueTypeTemporaryEntity.setInit(queueInitialQuantityTemporaryEntityString);

				deadTemporalEntity.setId(queueName);
				deadTemporalEntity.setClazz(temporaryEntity);
				deadTemporalEntity.setType(queueTypeTemporaryEntity);

				WorkProductResourcesQueueObserversPanel wprbrp = (WorkProductResourcesQueueObserversPanel) listOfWorkProductResourcesBottomRightPanel
						.get(j);

				List<QueueObserver> queueObservers = wprbrp.getObservers();
				for (QueueObserver queueObserver : queueObservers)
					deadTemporalEntity.getQueueObserver().add(queueObserver);

				// quinto: adiciona a fila de entidade temporaria no acd

				acd.getDead().add(deadTemporalEntity);

				// sexto: a fila de entidade temporaria deve ser adicionada na
				// generate activity

				nextDeadTemporaryEntityByGenerateActivity.setDead(deadTemporalEntity); // chamando
																						// deadTemporalEntity.getId()
																						// esta
																						// dando
																						// erro
																						// -
																						// estranho

				// 6 e meio - observers for generate activity
				WorkProductResourcesGenerateActivityObserversPanel lgaop = (WorkProductResourcesGenerateActivityObserversPanel) listOfGenerateActivityObserversPanel
						.get(j);
				List<ActObserver> actObservers = lgaop.getObservers();

				for (ActObserver generateActivityObserver : actObservers)
					generateActivity.getActObserver().add(generateActivityObserver);

				// insere o dead state no acd
				acd.getDead().add(deadPermanentEntity);

				// setimo: termino da configuracao de generate activity,
				// inserindo-a no acd

				if (workProduct.getInputOrOutput().equalsIgnoreCase("INPUT")) {
					generateActivity.getNext().add(nextDeadTemporaryEntityByGenerateActivity);
					acd.getGenerate().add(generateActivity);
				}
			}
		}
	}

	private void createTemporaryEntity() {
		// primeiramente, crio e configuro a entidade temporaria criada acima
		temporaryEntity.setId(workProduct.getName());
		boolean found = false;
		for (Class clazz: acd.getClazz()) {
			
			if (clazz.getId().equals(workProduct.getName()))
				found = true;
				
		}
		
		if (!found) {
			acd.getClazz().add(temporaryEntity);
		}
	}

	// Creation of dead resource queues necessary to the creation of regular activities
	    // nao uso for each pois preciso do indice para pegar a quantidade digitada na coluna da JTable roles
	// Este laço engloba todos os outros na criação do XACDML
	
	private void createPermanentEntitiesAndResourceQueues(List<Role> roles, RoleResourcesPanel roleResourcePanel) {
		
		List<RoleResourcesBottomPanel> listOfRoleResourcesBottomPanel = roleResourcePanel
				.getListOfRoleResourcesBottomPanels();
		for (int i = 0; i < roles.size(); i++) {
			role = roles.get(i);

			permanentEntity = factory.createClass();
			deadPermanentEntity = factory.createDead();
			queueTypePermanentEntity = factory.createType();

			// cria a entidade permanente
			permanentEntity.setId(role.getName());
			acd.getClazz().add(permanentEntity);

			// cria a fila para armazenenar a entidade permanente - cada role
			// vira uma entidade permanente
			String resourceQueueName = listOfRoleResourcesBottomPanel.get(i).getQueueNameTextField().getText();
			deadPermanentEntity.setId(resourceQueueName);
			deadPermanentEntity.setClazz(permanentEntity);

			// pega atributos configurados na Jtable role, necessario tambem par
			// o tipo da fila abaixo

			JTable roleTable = roleResourcePanel.getTableRole();
			Integer queueInitialQuantity = (Integer) roleTable.getModel().getValueAt(i, 4);
			role.setIntialQuantity(queueInitialQuantity);

			// define o tipo da fila da entidade permanente (QUEUE, STACK or
			// SET) e associa com o dead state
			String queueTypePermanentEntityString = (roleTable.getModel().getValueAt(i, 2)).toString();
			String queueSizePermanentEntityString = roleTable.getModel().getValueAt(i, 3).toString();

			queueTypePermanentEntity.setStruct(queueTypePermanentEntityString);
			queueTypePermanentEntity.setSize(queueSizePermanentEntityString);
			queueTypePermanentEntity.setInit(Integer.toString(role.getIntialQuantity())); // mesmo que pegar de queueInitialQuantity definido acima?
			deadPermanentEntity.setType(queueTypePermanentEntity);

			// configura os observer para o dead state
			queueObservers = listOfRoleResourcesBottomPanel.get(i).getObservers();
			for (QueueObserver queueObserver : queueObservers)
				deadPermanentEntity.getQueueObserver().add(queueObserver);

			// insere o dead state no acd
			acd.getDead().add(deadPermanentEntity);
			
		}
	}

	private void createSimulationDuration(String acdId, String simTime) {
		factory = new ObjectFactory();
		
		acd = factory.createAcd();
		acd.setId(acdId);
		
		Simtime simulationTime = new Simtime();
		simulationTime.setTime(simTime);
		acd.setSimtime(simulationTime);
	}
	
	private String generateXACDML() {
		String result = null;
		// completeListOfProcessContentRepository = null;
		// listOfProcessContentRepositoryTasks = null;
		try {
			result = persistProcessInXMLWithJAXBOnlyString(acd);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	public Acd getAcd() {
		return acd;
	}
	
	public String persistProcessInXMLWithJAXBOnlyString(Acd acd) throws IOException {

		try {
			JAXBContext context = JAXBContext.newInstance(Acd.class);
			Marshaller marshaller = context.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// a linha abaixo elimina geracao automatica de <?xml
			// version...standalone=true>
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			StringWriter sw = new StringWriter();

			// sw.write("<?xml version=\"1.0\"?>\n"); // without this line, the
			// first line of xml contains standalone = true
			// sw.write("<!DOCTYPE acd PUBLIC \"acd description//EN\"
			// xacdml.dtd>\n");

			sw.append("<?xml version=\"1.0\"?>\n"); // without this line, the
													// first line of xml
													// contains standalone =
													// true
			sw.append("<!DOCTYPE acd PUBLIC  \"acd description//EN\" \"xacdml.dtd\">\n");

			marshaller.marshal(acd, sw);

			return sw.toString();

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
