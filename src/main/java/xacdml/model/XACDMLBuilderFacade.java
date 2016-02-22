package xacdml.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.epf.uma.Process;

import adaptme.base.MethodLibraryWrapper;
import adaptme.ui.window.perspective.ProbabilityDistributionInnerPanel;
import adaptme.ui.window.perspective.RoleResourcesBottomPanel;
import adaptme.ui.window.perspective.RoleResourcesPanel;
import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import adaptme.ui.window.perspective.WorkProductResourcesPanel;
import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;
import xacdml.model.generated.Acd;
import xacdml.model.generated.Act;
import xacdml.model.generated.ActObserver;
import xacdml.model.generated.Class;
import xacdml.model.generated.Dead;
import xacdml.model.generated.Destroy;
import xacdml.model.generated.EntityClass;
import xacdml.model.generated.Generate;
import xacdml.model.generated.Graphic;
import xacdml.model.generated.Next;
import xacdml.model.generated.ObjectFactory;
import xacdml.model.generated.Prev;
import xacdml.model.generated.QueueObserver;
import xacdml.model.generated.Simtime;
import xacdml.model.generated.Stat;
import xacdml.model.generated.Type;
import simulator.base.ActiveObserverType;
import simulator.base.QueueObserverType;
import simulator.base.Role;
import simulator.base.Task;
import simulator.base.WorkProduct;

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
		
  		Dead deadPermanentEntity = factory.createDead();

 		// Creation of dead resource queues necessary to the creation of regular activities
  	    // nao uso for each pois preciso do indice para pegar a quantidade digitada na coluna da JTable roles
  		
  		Class permanentEntity = factory.createClass();
  		
  		for (int i = 0; i < roles.size(); i++) {  

			Role role = roles.get(i);
			
			// cria a entidade permanente
 			
 			permanentEntity.setId(role.getName());
			acd.getClazz().add(permanentEntity);
			
			String resourceQueueName = listOfRoleResourcesBottomPanel.get(i).getQueueNameTextField().getText();

//			String resourceQueueName = roleResourcePanel.getRoleResourcesBottomPannel().getQueueNameTextField().getText();
			
			// cria a fila para armazenenar a entidade permanente
			deadPermanentEntity.setId(resourceQueueName);
			deadPermanentEntity.setClazz(permanentEntity);
			
			
			// pega atributos configurados na Jtable role, necessario tambem par o tipo de fila abaixo
			
			JTable roleTable = roleResourcePanel.getTableRole();
			Integer initialQuantity = (Integer)roleTable.getModel().getValueAt(i, 3);
			role.setIntialQuantity(initialQuantity);
			
			// define o tipo da fila da entidade permanente (QUEUE, STACK or SET)
			String queueType = (roleTable.getModel().getValueAt(i, 1)).toString();
			String queueSize = roleTable.getModel().getValueAt(i, 2).toString();
			Type queue = factory.createType();
			
			queue.setStruct(queueType);
			queue.setSize(queueSize);
			queue.setInit(Integer.toString(role.getIntialQuantity()));
			deadPermanentEntity.setType(queue);
 		
			
			List<QueueObserver> queueObservers = listOfRoleResourcesBottomPanel.get(i).getObservers();
			 
            for (QueueObserver queueObserver: queueObservers) 
            	deadPermanentEntity.getQueueObserver().add(queueObserver);
            
            
			acd.getDead().add(deadPermanentEntity);
			deadPermanentEntity = factory.createDead();
			 permanentEntity = factory.createClass();
		}

 		
		// build generate activities
		
		Generate generateActivity = factory.createGenerate();
		List<JPanel> listProbabilityDistributionPanel = workProdutResourcesPanel.getListOfProbabilityDistributionPanels();
		
		for (int i = 0; i < workProducts.size(); i++) {  

			WorkProduct workProduct = workProducts.get(i);
			
			generateActivity.setId("Generate activity for : " + workProduct.getName());
			
			Class temporalEntity = factory.createClass();
			temporalEntity.setId("Temporal entity " + workProduct.getName());
			acd.getClazz().add(temporalEntity);
	 		generateActivity.setClazz(temporalEntity);   
	 		
//	 		// ate a linha 247, configura os dados da distribuicao selecionada
	 		JTable workProductTable = workProdutResourcesPanel.getTableWorkProduct();
//			// pega o painel x associado com o workproduct x
			ProbabilityDistributionInnerPanel probabilityDistributionInnerPanel = (ProbabilityDistributionInnerPanel) listProbabilityDistributionPanel.get(i);
		 
			Parameters parameters = probabilityDistributionInnerPanel.getParameters();

			Stat distribution = factory.createStat();
			
			if (parameters instanceof ConstantParameters) {
				ConstantParameters constantParameters = (ConstantParameters)parameters;
				distribution = factory.createStat();
	 			distribution.setType("CONSTANT");
	 			distribution.setParm1(Double.toString(constantParameters.getValue()));
	 			
			} else if (parameters instanceof UniformParameters) {
				UniformParameters UniformParameters = (UniformParameters)parameters;
				distribution = factory.createStat();
	 			distribution.setType("UNIFORM");
	 			distribution.setParm1(Double.toString(UniformParameters.getLow()));
	 			distribution.setParm2(Double.toString(UniformParameters.getHigh()));
 	 			
			} else if (parameters instanceof NegativeExponential) {
				NegativeExponential negativeExponential = (NegativeExponential)parameters;
				distribution = factory.createStat();
	 			distribution.setType("NEGEXP");
	 			distribution.setParm1(Double.toString(negativeExponential.getAverage()));
 	 			
			} else if (parameters instanceof NormalParameters) {
				NormalParameters normalParameters = (NormalParameters)parameters;
				distribution = factory.createStat();
	 			distribution.setType("NORMAL");
	 			distribution.setParm1(Double.toString(normalParameters.getMean()));
	 			distribution.setParm2(Double.toString(normalParameters.getStandardDeviation()));

			} else if (parameters instanceof PoissonParameters) {
				PoissonParameters poissonParameters = (PoissonParameters)parameters;
				distribution = factory.createStat();
	 			distribution.setType("POISSON");
	 			distribution.setParm1(Double.toString(poissonParameters.getMean()));
 			}
			
//			// Configura os observers da Generate Activity
//			List<QueuObserver> queueObservers = workProdutResourcesPanel.getWorkProductResourcesBottomRightPanel().getObservers();
//			 
//            for (QueuObserver queueObserver: queueObservers) 
//            	deadPermanentEntity.getQueueObserver().add(queueObserver);
// 				 
//				generateActivity.getActObserver().add(actObserver);
//			}
			
			
            
			Dead deadTemporalEntity = factory.createDead();
			String queueName = workProduct.getQueueName();
			deadTemporalEntity.setId(queueName);
			deadTemporalEntity.setClazz(temporalEntity);
			
			JTable tableWorkProduct = workProdutResourcesPanel.getTableWorkProduct();
//			// define o tipo da fila da entidade temporaria (QUEUE, STACK or SET)
			String queueType = (tableWorkProduct.getModel().getValueAt(i, 2)).toString();
			String queueCapacity = tableWorkProduct.getModel().getValueAt(i, 4).toString();
			
		 

			Type queue = factory.createType();
			queue.setStruct(queueType);
			queue.setSize(queueCapacity);
			queue.setInit("0"); // conferir
			
			deadTemporalEntity.setType(queue);

 			
			// Configura os observers da queue após a generate activity 
			List<QueueObserver> queueObservers = workProdutResourcesPanel.getWorkProductResourcesBottomRightPanel().getObservers();
						 
			for (QueueObserver queueObserver: queueObservers) 
				deadTemporalEntity.getQueueObserver().add(queueObserver);
 
 
 
			acd.getDead().add(deadTemporalEntity);
			
			Next nextDead = factory.createNext();
			nextDead.setDead(deadTemporalEntity);
			
			generateActivity.setStat(distribution);
			generateActivity.getNext().add(nextDead);

			acd.getGenerate().add(generateActivity);
			generateActivity = factory.createGenerate();
			
		// end build generate activities
		
		// begin destroy
//			Destroy destroyDep0 = factory.createDestroy();
//
//			destroyDep0.setId("Destroy : " + workProduct.getName());
////			destroyDep0.setClazz(temporalEntity);  (vou precisar colocar isso dentro do laco de roles, para cada iteracao, tenho que fazer tudo)
//
//			Stat uniform2 = factory.createStat();
//			uniform2.setType("UNIFORM");
//			uniform2.setParm1("0.0");
//			uniform2.setParm2("10.0");
//
//			//Prev previous = factory.createPrev();
//			//previous.setDead(dead);
//			//destroyDep0.getPrev().add(previous);
////			destroyDep0.setGraphic(box3);
//			// esta faltando destroy.setStat no codigo
//			
//			acd.getDestroy().add(destroyDep0);
//			
//			generateActivity = factory.createGenerate();
//			destroyDep0 = factory.createDestroy();
//		}
			
			// end destroy
		
		// begin buildActivities
		
//         Act regularActivity = factory.createAct();
//		
//		for (String task: tasks){
//			
//			regularActivity.setId(task);
//
//			Stat uniform = factory.createStat();
//			uniform.setType("UNIFORM");
//			uniform.setParm1("1.0");
//			uniform.setParm2("5.0");

//			EntityClass ec1 = factory.createEntityClass();
//			 
//			ec1.setPrev(dead);
//			
//		    ec1.setNext(dead);
//			
//			EntityClass ec2 = factory.createEntityClass();
//	 
//		    ec2.setPrev(dead);
//			ec2.setNext(dead);
//
//			regularActivity.setStat(uniform);
 //			regularActivity.getEntityClass().add(ec1);
//			regularActivity.getEntityClass().add(ec2);
			
//			acd.getAct().add(regularActivity);	
//			regularActivity = factory.createAct();
//		}
		
		// end buildActivities
		}
		this.acd = acd;
		String result = null;
		try {
			result = persistProcessInXMLWithJAXBOnlyString(acd);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return result;
		}
		
 	}
	
	

	public Acd getAcd() {
		return acd;
	}
}
