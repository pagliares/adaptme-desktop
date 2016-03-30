package xacdml.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	private Next next;
	private Act regularActivity;
	private EntityClass entityClass;
	private EntityClass ec2;
	private Prev previous; 
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
		 
        int destroyIdCounter = 1;
		String queueName;
		Prev prev = factory.createPrev();
		Dead dx = factory.createDead();
		for (Dead dead : queues) {
			queueName = dead.getId();

			for (Act act : regularActivities) {
				List<EntityClass> entityClasses = act.getEntityClass();
				for (EntityClass entityClass : entityClasses) {
 
					prev = (Prev)entityClass.getPrev();
					dx = (Dead)prev.getDead();
					classToBedestroyed = dx.getClazz();
//					if (queueName.equals(dx.getId())) {
						// it is input. cannot be destroyed
//						mayBeDestroyed1 = false;
						
//					}
				}
			}
			if (mayBeDestroyed1) {
				destroyActivity = factory.createDestroy();
				
				
				destroyActivity.getPrev().add(prev);
				 
				destroyActivity.setClazz(classToBedestroyed);
				destroyActivity.setId("destroy"+destroyIdCounter);
				 
				acd.getDestroy().add(destroyActivity);
			}
			mayBeDestroyed1 = true;
			classToBedestroyed = null;
			destroyIdCounter++;
			prev = factory.createPrev();
			dx = factory.createDead();
		}
		
		
	}

	private void createRegularActivities(List<WorkProductXACDML> workProducts, MainPanelSimulationOfAlternativeOfProcess mainPanelSimulationOfAlternativeOfProcess) {
		 	
		completeListOfProcessContentRepository = calibratedProcessRepository.getProcessContents();
		calibratedProcessRepository.clearListOfTasks(); // Este metodo removeu um erro muito dificil que era a geracao de varias tarefas no xacdml duplicada
		listOfProcessContentRepositoryTasks = calibratedProcessRepository.getListProcessContentRepositoryWithTasksOnly(completeListOfProcessContentRepository);
 

		for (ProcessContentRepository processContentRepository : listOfProcessContentRepositoryTasks) {
			
			configureRegularActivity(processContentRepository);
		
			bindQueuesToActivities(workProducts, processContentRepository);

			configureDistribution(processContentRepository);
			
			configureObservers(mainPanelSimulationOfAlternativeOfProcess, processContentRepository);
			
			acd.getAct().add(regularActivity);	
		}
	}

	private void configureRegularActivity(ProcessContentRepository processContentRepository) {
		regularActivity = factory.createAct();
		regularActivity.setId(processContentRepository.getName());
	}

	/**
	 * 
	 * @param workProducts
	 * @param processContentRepository
	 * 
	 * A solucao esta nas composition e decomposition activities do ADOO do Hirata. Acho que para isso funcionar no XACDML, 
	 * vou ter que alterar o DTD para que a tag EntityClass passe a aceitar varios Prevs e Next attributes. Outra possibilidade 
	 * e por hora aceitar apenas relacionamentos 1 para 1, assim como xacdml. O metodo abaixo funciona para 1 x 1 apenas
	 * 
	 * A Solucao abaixo que aparentemente funcionava, mas nao pois a semantica deve ser coletiva e nao cada combinacao input/output (tenho
	 * que pegar a1b1 coletivamente para produzir c1d1 coletivamente, por exemplo
	 * um entity class tem que agregar um prev and um next.Como podemos ter n input queues and m outputqueues, temos n x m entities em uma tag <act>
	 * quantidade de entity classes depende do tamanho das listas de inputqueuenames ou outputqueue names
	 * Para ser necessario, preciso do NOME das filas de entrada e saida (apenas input e output work products nao funciona, pois podemos
	 * ter produtos de trabalho com o mesmo nome)
	 */
	private void bindQueuesToActivities(List<WorkProductXACDML> workProducts, ProcessContentRepository processContentRepository) {
		
		boolean hasPredecessor =  false;
		next = factory.createNext();   
		previous = factory.createPrev();
		entityClass = factory.createEntityClass(); 
		Dead dead = factory.createDead();
		
		List<ProcessContentRepository> listProcessContentRepository = processContentRepository.getPredecessors();
		
		if (listProcessContentRepository.size() != 0) {
			ProcessContentRepository predecessor  = listProcessContentRepository.get(0);
			  // preciso pegar o nome da fila de saida da atividade anterior
			  Set<MethodContentRepository> workProductsSaidaAnterior = predecessor.getOutputMethodContentsRepository();
			  Iterator<MethodContentRepository> iterator = workProductsSaidaAnterior.iterator();
			  String workpProductOutputNamePredecessor = iterator.next().toString();
		}
	  
		// mantive a implementacao como lista, para no futuro poder escalar. No momento, todos casos de teste sao 1 x 1 
		List<String> inputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
		List<String> outputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
		
		String taskNameXACDML;
		
		for (WorkProductXACDML workProductXACDML: workProducts) {
			
			taskNameXACDML = workProductXACDML.getTaskName();
			if (taskNameXACDML.equals(processContentRepository.getName())) {
				
				if (workProductXACDML.getInputOrOutput().equalsIgnoreCase("Input")) {
					inputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
				} else {
					outputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
				}			
			} 	
		}
		
		 int entityClassIdCounter = 1; 
		 int prevIdCounter = 1; 
		 int netxIdCounter = 1;
		for (String queueName : inputQueuesNameForSpecificProcessContentRepository) {
			
			for (Dead d : acd.getDead()) {
				previous = factory.createPrev();
				next = factory.createNext();
				if (d.getId().equals(queueName)) {
					
					previous.setId("prev"+prevIdCounter);
					previous.setDead(d);
					entityClass.setId("ec"+entityClassIdCounter); //tem que existir

					entityClass.setPrev(previous);

					for (String queueName1 : outputQueuesNameForSpecificProcessContentRepository) {
						for (Dead d1 : acd.getDead()) {
							if (d1.getId().equals(queueName1)) {
								next.setId("next"+netxIdCounter);
								next.setDead(d1);
								entityClass.setNext(next);	
							}
						}
					}
					
				}
				
			}
			regularActivity.getEntityClass().add(entityClass);
			entityClass = factory.createEntityClass();
			entityClassIdCounter++;
			prevIdCounter++;
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
//				acd.getDead().add(deadPermanentEntity);

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
