package xacdml.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import adaptme.ui.window.perspective.ProbabilityDistributionInnerPanel;
import adaptme.ui.window.perspective.RoleResourcesBottomPanel;
import adaptme.ui.window.perspective.RoleResourcesPanel;
import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import adaptme.ui.window.perspective.WorkProductResourcesPanel;
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
import simulator.base.WorkProduct;
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

public class XACDMLBuilderFacade {

	private Acd acd;
	 
	public void persistProcessInXMLWithJAXB(Acd acd, String fileName) throws IOException {

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

			File f = PathToFile.getPathToOutputFile(fileName, false);

			marshaller.marshal(acd, f);

			System.out.println(sw.toString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
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

	public String buildXACDML(String acdId, String simTime, List<Role> roles, List<WorkProduct> workProducts, Set<String> tasks, 
							  RoleResourcesPanel roleResourcePanel, WorkProductResourcesPanel workProdutResourcesPanel){
				
		List<RoleResourcesBottomPanel> listOfRoleResourcesBottomPanel = roleResourcePanel.getListOfRoleResourcesBottomPanels();
		ObjectFactory factory = new ObjectFactory();
		
		Acd acd = factory.createAcd();
		acd.setId(acdId);
		
		Simtime simulationTime = new Simtime();
		simulationTime.setTime(simTime);
		acd.setSimtime(simulationTime);
		
 		// Creation of dead resource queues necessary to the creation of regular activities
  	    // nao uso for each pois preciso do indice para pegar a quantidade digitada na coluna da JTable roles
		// Este laço engloba todos os outros na criação do XACDML
  		
		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);

			Class permanentEntity = factory.createClass();
			Dead deadPermanentEntity = factory.createDead();
			Type queueTypePermanentEntity = factory.createType();

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
			List<QueueObserver> queueObservers = listOfRoleResourcesBottomPanel.get(i).getObservers();
			for (QueueObserver queueObserver : queueObservers)
				deadPermanentEntity.getQueueObserver().add(queueObserver);

			// insere o dead state no acd
			acd.getDead().add(deadPermanentEntity);

			

		}
		// build generate activities
			List<JPanel> listProbabilityDistributionPanel = workProdutResourcesPanel.getListOfProbabilityDistributionPanels();

			for (int j = 0; j < workProducts.size(); j++) {

				// Para cada demand work product devo gerar: 
				// temporary entity, generate activity (e observers), queue for temporary entity (e observers), regular activities and destroy activities
				
				WorkProduct workProduct = workProducts.get(j);
				
				Generate generateActivity = factory.createGenerate();
				Class temporaryEntity = factory.createClass();
				Stat distribution = factory.createStat();
				Dead deadTemporalEntity = factory.createDead();
				Type queueTypeTemporaryEntity = factory.createType();
				Next nextDeadTemporaryEntityByGenerateActivity = factory.createNext();
				Next nextDeadTemporaryEntityByRegularActivity = factory.createNext();
				Act regularActivity = factory.createAct();
				EntityClass ec1 = factory.createEntityClass();
				EntityClass ec2 = factory.createEntityClass();
				Prev previous = factory.createPrev();
				Next next = factory.createNext();
				Destroy destroyActivity = factory.createDestroy();
				
				// primeiramente, crio e configuro a entidade temporaria criada acima
				generateActivity.setId("Generate activity for : " + workProduct.getName());
				temporaryEntity.setId("Temporary entity " + workProduct.getName());
				generateActivity.setClazz(temporaryEntity);
				acd.getClazz().add(temporaryEntity);
				
				// Em segundo lugar, configuro a distribuicao de probabilidade criada acima para a generate activity
				// (panel probability distribution parameters - painel x associado com o workproduct x)
				
				JTable workProductTable = workProdutResourcesPanel.getTableWorkProduct();
				
				ProbabilityDistributionInnerPanel probabilityDistributionInnerPanel = (ProbabilityDistributionInnerPanel) listProbabilityDistributionPanel.get(j);
				Parameters parametersDistributionGenerateActivity = probabilityDistributionInnerPanel.getParameters();

				if (parametersDistributionGenerateActivity instanceof ConstantParameters) {
					ConstantParameters constantParameters = (ConstantParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("CONSTANT");
					distribution.setParm1(Double.toString(constantParameters.getValue()));

				} else if (parametersDistributionGenerateActivity instanceof UniformParameters) {
					UniformParameters UniformParameters = (UniformParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("UNIFORM");
					distribution.setParm1(Double.toString(UniformParameters.getLow()));
					distribution.setParm2(Double.toString(UniformParameters.getHigh()));

				} else if (parametersDistributionGenerateActivity instanceof NegativeExponential) {
					NegativeExponential negativeExponential = (NegativeExponential) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("NEGEXP");
					distribution.setParm1(Double.toString(negativeExponential.getAverage()));

				} else if (parametersDistributionGenerateActivity instanceof NormalParameters) {
					NormalParameters normalParameters = (NormalParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("NORMAL");
					distribution.setParm1(Double.toString(normalParameters.getMean()));
					distribution.setParm2(Double.toString(normalParameters.getStandardDeviation()));

				} else if (parametersDistributionGenerateActivity instanceof PoissonParameters) {
					PoissonParameters poissonParameters = (PoissonParameters) parametersDistributionGenerateActivity;
					distribution = factory.createStat();
					distribution.setType("POISSON");
					distribution.setParm1(Double.toString(poissonParameters.getMean()));
				}
				
				// configura a generate activity com a distribuicao apropriada
				
				generateActivity.setStat(distribution);
				
                // Preciso gerar dois tipos de observers no painel work product resources
				// Terceiro: Configura os observers da Generate Activity
//				List<ActObserver> activityObservers = workProdutResourcesPanel.getWorkProductResourcesBottomRightPanel().getObservers();
//				
//				for (ActiveObserver activityObserver: queueObservers) {
//					deadPermanentEntity.getQueueObserver().add(queueObserver);
//					generateActivity.getQueueObserver().add(queueObserver);
//				}

				// Quarto: configuracao da fila para entidade temporaria incluindo seu tipo (QUEUE, STACK or SET) e observers
				
				String queueName = workProduct.getQueueName();
				String queueTypeTemporaryEntityString = (workProductTable.getModel().getValueAt(j, 2)).toString();
				String queueCapacityTemporaryEntityString = workProductTable.getModel().getValueAt(j, 4).toString();
				String queueInitialQuantityTemporaryEntityString = workProductTable.getModel().getValueAt(j, 5).toString();
				
				queueTypeTemporaryEntity.setStruct(queueTypeTemporaryEntityString);
				queueTypeTemporaryEntity.setSize(queueCapacityTemporaryEntityString);
				queueTypeTemporaryEntity.setInit(queueInitialQuantityTemporaryEntityString); 
				
				deadTemporalEntity.setId(queueName);
				deadTemporalEntity.setClazz(temporaryEntity);
				deadTemporalEntity.setType(queueTypeTemporaryEntity);

				List<QueueObserver> queueObservers = workProdutResourcesPanel.getWorkProductResourcesBottomRightPanel().getObservers();

				for (QueueObserver queueObserver : queueObservers)
					deadTemporalEntity.getQueueObserver().add(queueObserver);

				// quinto: adiciona a fila de entidade temporaria no acd
				
				acd.getDead().add(deadTemporalEntity);

				// sexto: a fila de entidade temporaria deve ser adicionada na generate activity
				
				nextDeadTemporaryEntityByRegularActivity.setDead(deadTemporalEntity);

				// setimo: termino da configuracao de generate activity, inserindo-a no acd
				
				generateActivity.getNext().add(nextDeadTemporaryEntityByRegularActivity);
				acd.getGenerate().add(generateActivity);
			
				// Oitavo:  configuracao de regular activities

				ProcessRepository calibratedProcessRepository = SPEMDrivenPerspectivePanel.processRepository;			
				List<ProcessContentRepository> listOfProcessContentRepository = calibratedProcessRepository.getProcessContents();
				Set<MethodContentRepository> setOfInputMethodContentRepository = null;
				Set<MethodContentRepository> setOfOutputMethodContentRepository = null;
				
				for (ProcessContentRepository processContentRepository : listOfProcessContentRepository) {
					
					if (processContentRepository.getType().equals(ProcessContentType.TASK)) {
						
						// primeiramente, pegando da incoming queues of workproducts, generated by generate activities
						
						setOfInputMethodContentRepository = processContentRepository.getInputMethodContentsRepository();
						regularActivity.setId(processContentRepository.getName());
						
						// cada input de um processContentRepository é uma fila previa de uma atividade XACDML
						for (MethodContentRepository inputMethodContentRepository : setOfInputMethodContentRepository) {
							
							// Refatorar: if (mcr.getName().equals(workProduct.getName())) A fila gerada bate com o metodo

							previous.setId("previousID");
							previous.setDead(deadTemporalEntity);
							ec1.setPrev(previous);
							regularActivity.getEntityClass().add(ec1);
						}
						
						setOfOutputMethodContentRepository = processContentRepository.getOutputMethodContentsRepository();
						for (MethodContentRepository mcr : setOfOutputMethodContentRepository) {
							
							// Refatorar: if (mcr.getName().equals(workProduct.getName())) A fila gerada bate com o metodo
							
							nextDeadTemporaryEntityByRegularActivity.setId("next id");
							nextDeadTemporaryEntityByRegularActivity.setDead(deadTemporalEntity);

							ec2.setNext(nextDeadTemporaryEntityByRegularActivity);
							regularActivity.getEntityClass().add(ec2);

						}
						
						Parameters parametersDistributionRegularActivity = processContentRepository.getSample().getParameters();

						distribution = factory.createStat();

						if (parametersDistributionRegularActivity instanceof ConstantParameters) {
							ConstantParameters constantParameters = (ConstantParameters) parametersDistributionRegularActivity;
							distribution = factory.createStat();
							distribution.setType("CONSTANT");
							distribution.setParm1(Double.toString(constantParameters.getValue()));

						} else if (parametersDistributionRegularActivity instanceof UniformParameters) {
							UniformParameters UniformParameters = (UniformParameters) parametersDistributionRegularActivity;
							distribution = factory.createStat();
							distribution.setType("UNIFORM");
							distribution.setParm1(Double.toString(UniformParameters.getLow()));
							distribution.setParm2(Double.toString(UniformParameters.getHigh()));

						} else if (parametersDistributionRegularActivity instanceof NegativeExponential) {
							NegativeExponential negativeExponential = (NegativeExponential) parametersDistributionRegularActivity;
							distribution = factory.createStat();
							distribution.setType("NEGEXP");
							distribution.setParm1(Double.toString(negativeExponential.getAverage()));

						} else if (parametersDistributionRegularActivity instanceof NormalParameters) {
							NormalParameters normalParameters = (NormalParameters) parametersDistributionRegularActivity;
							distribution = factory.createStat();
							distribution.setType("NORMAL");
							distribution.setParm1(Double.toString(normalParameters.getMean()));
							distribution.setParm2(Double.toString(normalParameters.getStandardDeviation()));

						} else if (parametersDistributionRegularActivity instanceof PoissonParameters) {
							PoissonParameters poissonParameters = (PoissonParameters) parametersDistributionRegularActivity;
							distribution = factory.createStat();
							distribution.setType("POISSON");
							distribution.setParm1(Double.toString(poissonParameters.getMean()));
						}
						regularActivity.setStat(distribution);
						acd.getAct().add(regularActivity);
					}
					 
				}

				// NONO: Configurar destroy activities
				 
				destroyActivity.setId("Destroy : " + workProduct.getName());
				destroyActivity.setClazz(temporaryEntity);  

				previous.setDead(deadTemporalEntity);
				destroyActivity.getPrev().add(previous);
				 
				acd.getDestroy().add(destroyActivity);	 
			}
		
			
		this.acd = acd;
		String result = null;
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
}
