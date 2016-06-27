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
    private boolean mayQueueBeDestroyed = true;  //
    
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
		
		createRegularActivities(workProducts, mainPanelSimulationOfAlternativeOfProcess, roleResourcePanel);
		
		createSpecialActivitiesForIterationAndRelease();
		
		createDestroyActivities(roles, workProducts);

		return generateXACDML();

	}
	
	private void createSpecialActivitiesForIterationAndRelease() {
		// Tornar recursivo
		for (ProcessContentRepository processContentRepository : calibratedProcessRepository.getProcessContents()) {
			if (processContentRepository.getName().equals("Iteration") || processContentRepository.getName().equals("Release")) {
				regularActivity = factory.createAct();
				regularActivity.setId(processContentRepository.getName());	
				
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
				
				// Configuring  entity class for permanent entities. 
				// estou pegando a primeira fila, supondo que seja permanent entity
				// so estou fazendo isso, pois a simulacao nao funciona para um <act> sem entity class
 
				Dead queue = acd.getDead().get(0);
						 
				entityClass.setId("role0"); // tem que existir para evitar o erro IDREF no momento de marshalling
																			 
				previous.setId(queue.getId());
				previous.setDead(queue);
		 
				next.setId(queue.getId());
				next.setDead(queue);  // recebe Object. O que acontece se eu passar queue.getId()
						 
				entityClass.setPrev(previous);
				entityClass.setNext(next);
			
				
				 regularActivity.getEntityClass().add(entityClass);
				 acd.getAct().add(regularActivity);	
				
				 if (processContentRepository.getChildren().get(0).getName().equals("Iteration") || 
						 processContentRepository.getChildren().get(0).getName().equals("Release")) {
						regularActivity = factory.createAct();
						
						regularActivity.setId(processContentRepository.getChildren().get(0).getName());	
						// Configuring  entity class for permanent entities. 
						// estou pegando a primeira fila, supondo que seja permanent entity
						// so estou fazendo isso, pois a simulacao nao funciona para um <act> sem entity class
		 
						Dead queue1 = acd.getDead().get(0);
								 
						entityClass.setId("role1"); // tem que existir para evitar o erro IDREF no momento de marshalling
																					 
						previous.setId(queue1.getId());
						previous.setDead(queue1);
				 
						next.setId(queue1.getId());
						next.setDead(queue1);  // recebe Object. O que acontece se eu passar queue.getId()
								 
						entityClass.setPrev(previous);
						entityClass.setNext(next);
						
						
						parametersDistributionRegularActivity = processContentRepository.getChildren().get(0).getSample().getParameters(); // talvez pegar direto do painel

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
						 regularActivity.getEntityClass().add(entityClass);
						acd.getAct().add(regularActivity);
				 }  
			}	}
		}
	

	private void createDestroyActivities(List<Role> roles, List<WorkProductXACDML> workProducts) {
		
		// Algorithm
		// 1 - Nao pode ser input de nenhuma tarefa SPEM (regular activity)
		// 2 - Nao podemos destruir entidades permanentes (Resource queues)
		// 3 - Nao pode estar em uma fila next de uma generate activity

		// Algorithm : search queues that have entities to be destroyed
		// Only XACDML metamodel
		// Queue can not be input to any regular activity (internal actiivy)
		
		
		Object classToBedestroyed = null;
		Prev prev = factory.createPrev();
		List<Dead> queues = acd.getDead();
		List<Act> regularActivities = acd.getAct();
		 
        int destroyIdCounter = 1;
		String queueName;
		
		deadTemporalEntity = factory.createDead();
		
	
		for (Dead dead : queues) {
			mayQueueBeDestroyed = true;  //
			queueName = dead.getId();  // essa fila nao pode ser input para nenhuma entity classes in all activities

			mayQueueBeDestroyed = mayQueueBeDestroyed(regularActivities, queueName, mayQueueBeDestroyed);
			
			if (mayQueueBeDestroyed) {
				destroyActivity = factory.createDestroy();
				Prev prevx = factory.createPrev();
				prevx.setDead(dead);
				prevx.setId(dead.getId());
				destroyActivity.getPrev().add(prevx);  // erro prev apontando para input queue e nao caller output queue 0
				destroyActivity.setClazz(classToBedestroyed);
				destroyActivity.setId("destroy"+destroyIdCounter);
				 
				acd.getDestroy().add(destroyActivity);
			}
			mayQueueBeDestroyed = true;
			classToBedestroyed = null;
			destroyIdCounter++;
			prev = factory.createPrev();
			deadTemporalEntity = factory.createDead();
		}
		
		
	}

	private boolean mayQueueBeDestroyed(List<Act> regularActivities, String queueName, boolean mayQueueBeDestroyed) {
		for (Act act : regularActivities) {
			List<EntityClass> entityClasses = act.getEntityClass();
			for (EntityClass entityClass : entityClasses) {
				Prev prev1 = (Prev)entityClass.getPrev();  // erro aqui
				deadTemporalEntity = (Dead)prev1.getDead();  
 				if (queueName.equals(deadTemporalEntity.getId())) {
					// it is input. cannot be destroyed
					mayQueueBeDestroyed = false;	
				}
				prev1 = factory.createPrev();
			}
		}
		return mayQueueBeDestroyed;
 
	}

	private void createRegularActivities(List<WorkProductXACDML> workProducts, MainPanelSimulationOfAlternativeOfProcess mainPanelSimulationOfAlternativeOfProcess, RoleResourcesPanel roleResourcePanel) {
		 
		completeListOfProcessContentRepository = calibratedProcessRepository.getProcessContents();
		calibratedProcessRepository.clearListOfTasks(); // Este metodo removeu um erro muito dificil que era a geracao de varias tarefas no xacdml duplicada
		listOfProcessContentRepositoryTasks = calibratedProcessRepository.getListProcessContentRepositoryWithTasksOnly(completeListOfProcessContentRepository);
 

		for (ProcessContentRepository processContentRepository : listOfProcessContentRepositoryTasks) {
			
			regularActivity = factory.createAct();
			regularActivity.setId(processContentRepository.getName());
		
			int entityClassIdCounter = 1; 
			 
			next = factory.createNext();   
			previous = factory.createPrev();
			entityClass = factory.createEntityClass(); 
			deadTemporalEntity = factory.createDead();
		
			// Configuring  entity class for permanent entities. If more than one resource, include call getAdditionalPerformers on processContentRepository  
			String roleName = processContentRepository.getMainRole().getName();
			String queueNameRole = null;
			JTable roleTable = roleResourcePanel.getTableRole();
						 
			for (int i = 0; i < roleTable.getRowCount(); i++) {
				String roleNameTable = (roleTable.getModel().getValueAt(i, 0)).toString();
				if (roleNameTable.equals(roleName)) {
					queueNameRole = (roleTable.getModel().getValueAt(i, 1)).toString();
				}
			}
			
			for (Dead queue : acd.getDead()) {
				
				if (queue.getId().equals(queueNameRole)) {   
					entityClass.setId("role" + entityClassIdCounter); // tem que existir para evitar o erro IDREF no momento de marshalling
																		 
					previous.setId(queue.getId());
					previous.setDead(queue);
	 
					next.setId(queue.getId());
					next.setDead(queue);  // recebe Object. O que acontece se eu passar queue.getId()
					 
					entityClass.setPrev(previous);
					entityClass.setNext(next);
				}
			}
			 regularActivity.getEntityClass().add(entityClass);
			 
			// Configuring  entity class for non-permanent entities 
			 
	 		 next = factory.createNext();   
			 previous = factory.createPrev();
			 
			 deadTemporalEntity = factory.createDead();
			
			 
			// mantive a implementacao como lista, para no futuro poder escalar. No momento, todos casos de teste sao 1 x 1 
			List<String> inputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
			List<String> outputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
			
			
			String workpProductOutputNamePredecessor = "";
			Dead predecessorQueue = null;
			List<ProcessContentRepository> listPredecessorsProcessContentRepository = processContentRepository.getPredecessors();
					
			String taskNameXACDML;
			String predecessorTaskName;
			
			for (WorkProductXACDML workProductXACDML: workProducts) {
				
				taskNameXACDML = workProductXACDML.getTaskName();
				if (taskNameXACDML.equals(processContentRepository.getName())) {
					
					if (workProductXACDML.getInputOrOutput().equalsIgnoreCase("Input")) {
						inputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
					}
					
					if (workProductXACDML.getInputOrOutput().equalsIgnoreCase("Output")) {
						outputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
					}			
				} 
				
				// preciso criar uma entidade para cada saida
				for (String queueName : outputQueuesNameForSpecificProcessContentRepository) {
					 entityClass = factory.createEntityClass();
					 entityClass.setId("temp ec");
					 
					 // configura next  
					 for (String queueName1 : outputQueuesNameForSpecificProcessContentRepository) {
							for (Dead q1 : acd.getDead()) {
								if (q1.getId().equals(queueName1)) {
									next.setId(q1.getId());
									next.setDead(q1);
									entityClass.setNext(next);	
								}
							}
						}	
					 
					 // configure prev
						if (listPredecessorsProcessContentRepository.size() != 0) { // se tem predecessor explicito
						predecessorTaskName = listPredecessorsProcessContentRepository.get(0).getName(); // ta pegando so o name do predecessor. Suficiente aparentemetne
						// preciso pegar o nome da fila de saida da atividade anterior
						
						// se tem predecessor, buscamos a fila de saida deste predecessor
						 
						if (!predecessorTaskName.equals("")) {

							List<Act> activities = acd.getAct();
							for (Act act: activities) {
								if (predecessorTaskName.equals(act.getId())) {
									List<EntityClass> entities = act.getEntityClass();
									for (EntityClass entityClass: entities) {
										Next next = (Next)entityClass.getNext();
										predecessorQueue = (Dead)next.getDead();
										 
									}
								}  
							}
							previous.setId(predecessorQueue.getId());
							previous.setDead(predecessorQueue);
							entityClass.setPrev(previous);
						}
						
						
				} else {  // nao tem predecessor explicito
					for (String queueName1 : inputQueuesNameForSpecificProcessContentRepository) {  // so vai ter 1 por enquanto
						for (Dead queue : acd.getDead()) {
						if (queue.getId().equals(queueName1)) {
							 
								previous.setId(queue.getId());
								previous.setDead(queue);
								entityClass.setPrev(previous);
							 
						}
					}
				}
				}
				}		
			}
			
			System.out.println("input queue names.." + inputQueuesNameForSpecificProcessContentRepository);
			System.out.println("output queue names.." + outputQueuesNameForSpecificProcessContentRepository);
				
			regularActivity.getEntityClass().add(entityClass);

			
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
			acd.getAct().add(regularActivity);	
		}	
	}

	
	
	public void bindEntityClassesToQueues(ProcessContentRepository processContentRepository, List<WorkProductXACDML> workProducts) {
		next = factory.createNext();   
		previous = factory.createPrev();
		entityClass = factory.createEntityClass(); 
		deadTemporalEntity = factory.createDead();
		
		List<ProcessContentRepository> listPredecessorsProcessContentRepository = processContentRepository.getPredecessors();
		
		if (listPredecessorsProcessContentRepository.size() == 0) { // se nao tem predecessor
			
			boolean existeFila = false;
			Dead inputQueue = null; 
			boolean createQueue;
			// encontra o nome da fila
			String previousQueueName = null;
			 
			for (Dead queue : acd.getDead()) {
				for (WorkProductXACDML workProductXACDML : workProducts) {
					if (queue.getId().equals(workProductXACDML.getQueueName())) {
						previousQueueName = queue.getId();
						inputQueue = queue;
						existeFila = true;
					}
				}
			}
			
			if (existeFila) { //nao criar outra
				createQueue = false;
			} else {
				createQueue = true;
				previous.setId(inputQueue.getId());
				previous.setDead(inputQueue);
				entityClass.setPrev(previous);
			}
			
			
			
		} else {  // tem predecessor 
			
			Dead outputQueuePredecessorActivity = null; 
			
			String outputQueueNamePredecessorActivity = null;
			boolean existeFila = false;
			// encontra o nome da fila
			for (Dead queue : acd.getDead()) {
				for (WorkProductXACDML workProductXACDML: workProducts) {
					if (queue.getId().equals(workProductXACDML.getQueueName())){
						outputQueueNamePredecessorActivity = queue.getId();
						existeFila = true;
					}
				}
			}
			
			// com o nome da fila anterior eu consigo pegar as entity classes, a partir do nome da regular activity 
			
			// se tem predecessor, buscamos a fila de saida deste predecessor
			String predecessorTaskName = listPredecessorsProcessContentRepository.get(0).getName(); // ta pegando so o name do predecessor. Suficiente aparentemetne
			
			if (!predecessorTaskName.equals("")) {
 
				List<Act> activities = acd.getAct();
				for (Act act: activities) {
					if (predecessorTaskName.equals(act.getId())) {
						List<EntityClass> entities = act.getEntityClass();
						for (EntityClass entityClass: entities) {
							Next next = (Next)entityClass.getNext();
							outputQueuePredecessorActivity = (Dead)next.getDead();
							outputQueueNamePredecessorActivity = outputQueuePredecessorActivity.getId();
							
						}
					} else {
						outputQueueNamePredecessorActivity = "";
					}
					}
				}
			previous.setId(outputQueuePredecessorActivity.getId());
			previous.setDead(outputQueuePredecessorActivity);
			entityClass.setPrev(previous);
			
		}
		
		
		
		
		
		
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
	private void bindQueuesToActivities(List<WorkProductXACDML> workProducts, ProcessContentRepository processContentRepository, RoleResourcesPanel roleResourcePanel) {
		
		int entityClassIdCounter = 1; 
	 
		next = factory.createNext();   
		previous = factory.createPrev();
		entityClass = factory.createEntityClass(); 
		deadTemporalEntity = factory.createDead();
		
		
	  
	
		// Configuring  entity class for permanent entities. If more than one resource, include call getAdditionalPerformers on processContentRepository  
		String roleName = processContentRepository.getMainRole().getName();
		String queueNameRole = null;
		JTable roleTable = roleResourcePanel.getTableRole();
					 
		for (int i = 0; i < roleTable.getRowCount(); i++) {
			String roleNameTable = (roleTable.getModel().getValueAt(i, 0)).toString();
			if (roleNameTable.equals(roleName)) {
				queueNameRole = (roleTable.getModel().getValueAt(i, 1)).toString();
			}
		}
		
		for (Dead queue : acd.getDead()) {
			
			if (queue.getId().equals(queueNameRole)) {   
				entityClass.setId("role" + entityClassIdCounter); // tem que existir para evitar o erro IDREF no momento de marshalling
																	 
				previous.setId(queue.getId());
				previous.setDead(queue);
 
				next.setId(queue.getId());
				next.setDead(queue);  // recebe Object. O que acontece se eu passar queue.getId()
				 
				entityClass.setPrev(previous);
				entityClass.setNext(next);
			}
		}
		 regularActivity.getEntityClass().add(entityClass);
		 
		// Configuring  entity class for non-permanent entities 
		 
 		 next = factory.createNext();   
		 previous = factory.createPrev();
		 entityClass = factory.createEntityClass(); 
		 deadTemporalEntity = factory.createDead();
		
		 
		// mantive a implementacao como lista, para no futuro poder escalar. No momento, todos casos de teste sao 1 x 1 
		List<String> inputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
		List<String> outputQueuesNameForSpecificProcessContentRepository = new ArrayList<>();
		
		
		String workpProductOutputNamePredecessor = "";
		Dead predecessorQueue = null;
		List<ProcessContentRepository> listPredecessorsProcessContentRepository = processContentRepository.getPredecessors();
				
		String taskNameXACDML;
		String predecessorTaskName;
		
		for (WorkProductXACDML workProductXACDML: workProducts) {
			
			taskNameXACDML = workProductXACDML.getTaskName();
			
			if (listPredecessorsProcessContentRepository.size() != 0) {
					predecessorTaskName = listPredecessorsProcessContentRepository.get(0).getName(); // ta pegando so o name do predecessor. Suficiente aparentemetne
					// preciso pegar o nome da fila de saida da atividade anterior
					
					// se tem predecessor, buscamos a fila de saida deste predecessor
					String predecessorQueueName = "";
					if (!predecessorTaskName.equals("")) {
//						String outputQueueName = "";
						List<Act> activities = acd.getAct();
						for (Act act: activities) {
							if (predecessorTaskName.equals(act.getId())) {
								List<EntityClass> entities = act.getEntityClass();
								for (EntityClass entityClass: entities) {
									Next next = (Next)entityClass.getNext();
									predecessorQueue = (Dead)next.getDead();
									predecessorQueueName = predecessorQueue.getId();
									
								}
							} else {
								predecessorQueueName = "";
							}
							}
						}
					
					
			} else {
				predecessorTaskName = "";
			}
					
			//  cria filas de entrada. se tem predecessor, seta ela, caso contrario seta o match
				entityClass.setId("ec"+ ++entityClassIdCounter); //tem que existir
				
//				for (Dead queue : acd.getDead()) {
//					if (predecessorQueueName.equals(queue.getId())) {
//						predecessorQueue = queue;
//					}
//					
//				}
			
				if (taskNameXACDML.equals(processContentRepository.getName())) {
					
					if (workProductXACDML.getInputOrOutput().equalsIgnoreCase("Input")) {
						inputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
					} else {
						outputQueuesNameForSpecificProcessContentRepository.add(workProductXACDML.getQueueName());
					}			
				} 	
		}
		
		
		
		for (String queueName : inputQueuesNameForSpecificProcessContentRepository) {  // so vai ter 1 por enquanto
			for (Dead queue : acd.getDead()) {
				if (queue.getId().equals(queueName)) {
					if (predecessorQueue != null) {
						previous.setId(queue.getId());
						previous.setDead(predecessorQueue);
						entityClass.setPrev(previous);
					} else {
						previous.setId(queue.getId());
						previous.setDead(queue);
						entityClass.setPrev(previous);
					}
				}
			}
		}
		
		for (String queueName1 : outputQueuesNameForSpecificProcessContentRepository) {
			for (Dead q1 : acd.getDead()) {
				if (q1.getId().equals(queueName1)) {
					next.setId(q1.getId());
					next.setDead(q1);
					entityClass.setNext(next);	
				}
			}
		}
					
		regularActivity.getEntityClass().add(entityClass);
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

			boolean exists = false;
			for (Dead d: acd.getDead()) {
				
				if (d.getId().equals(deadTemporalEntity.getId())) {
					exists = true;
				}
			}
			if (!exists) {
				acd.getDead().add(deadTemporalEntity);
			}

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
//			Integer queueInitialQuantity = (Integer) roleTable.getModel().getValueAt(i, 4);
			Integer queueInitialQuantity = role.getIntialQuantity();
			role.setIntialQuantity(queueInitialQuantity);

			// define o tipo da fila da entidade permanente (QUEUE, STACK or
			// SET) e associa com o dead state
			String queueTypePermanentEntityString = (roleTable.getModel().getValueAt(i, 2)).toString();
			String queueSizePermanentEntityString = roleTable.getModel().getValueAt(i, 3).toString();

			queueTypePermanentEntity.setStruct(queueTypePermanentEntityString);
			queueTypePermanentEntity.setSize(queueSizePermanentEntityString);
//			queueTypePermanentEntity.setInit(Integer.toString(role.getIntialQuantity())); // mesmo que pegar de queueInitialQuantity definido acima?
			queueTypePermanentEntity.setInit(Integer.toString(queueInitialQuantity)); // mesmo que pegar de queueInitialQuantity definido acima?
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
