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
import adaptme.ui.window.perspective.RoleResourcesPanel;
import adaptme.ui.window.perspective.WorkProductResourcesPanel;
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

	public Acd buildEntities(Acd acd, List<Role> roles, List<WorkProduct> workProducts) {
		 
		ObjectFactory factory = new ObjectFactory();
		
		for (Role role: roles) {
			Class clazz = factory.createClass();
			clazz.setId(role.getName());
			acd.getClazz().add(clazz);
		}
		
		for (WorkProduct workProduct: workProducts) {
			Class clazz = factory.createClass();
			clazz.setId(workProduct.getName());
			acd.getClazz().add(clazz);
		}
		
		
		return acd;
	}
	
	public Acd buildDeadStates(Acd acd, List<Role> roles, List<WorkProduct> workProducts) {

		ObjectFactory factory = new ObjectFactory();
		Dead dead = factory.createDead();
		
		for (WorkProduct workProduct: workProducts) {
			dead.setId(workProduct.getQueueName());
			// dead.setClazz(inquirer);
			
			Type queue = factory.createType();
			queue.setStruct("QUEUE");
			queue.setSize(Integer.toString(workProduct.getQuantity()));
			queue.setInit("0"); // conferir
			dead.setType(queue);
			
			// Verificar a necessidade de colocar Graphic
			Graphic circle = factory.createGraphic();
			circle.setType("CIRCLE");
			circle.setX("198");
			circle.setY("349");
			dead.setGraphic(circle);
			
			// buscar valores do JTable 3.2 - Falta um tipo de observer - nem todos no xacdml da tese tem queuobserver
			QueueObserver queueObserver = factory.createQueueObserver();
			queueObserver.setType("LENGTH");
			queueObserver.setName("SERVICE_OBS");
			dead.getQueueObserver().add(queueObserver);

			acd.getDead().add(dead);
			dead = factory.createDead();
		}
		
		
		for (Role role: roles) {
			dead.setId("Resource queue: " + role.getName());
			// dead.setClazz(inquirer);
			
			Type queue = factory.createType();
			queue.setStruct("QUEUE");
			queue.setSize(Integer.toString(role.getIntialQuantity()));
			queue.setInit("0"); // conferir
			dead.setType(queue);
			
			// Verificar a necessidade de colocar Graphic
			Graphic circle = factory.createGraphic();
			circle.setType("CIRCLE");
			circle.setX("198");
			circle.setY("349");
			dead.setGraphic(circle);
			
			// buscar valores do JTable 3.2 - Falta um tipo de observer - nem todos no xacdml da tese tem queuobserver
			QueueObserver queueObserver = factory.createQueueObserver();
			queueObserver.setType("LENGTH");
			queueObserver.setName("SERVICE_OBS");
			dead.getQueueObserver().add(queueObserver);

			acd.getDead().add(dead);
			dead = factory.createDead();
		}

		return acd;
	}
	
	public Acd buildActivities(Acd acd, Set<String> tasks) {
	
		ObjectFactory factory = new ObjectFactory();
		Act regularActivity = factory.createAct();
		
		for (String task: tasks){
			
			regularActivity.setId(task);

			Stat uniform = factory.createStat();
			uniform.setType("UNIFORM");
			uniform.setParm1("1.0");
			uniform.setParm2("5.0");

			Graphic box = factory.createGraphic();
			box.setType("BOX");
			box.setX("319");
			box.setY("110");

			EntityClass ec1 = factory.createEntityClass();
			Dead idle = factory.createDead();
			idle.setId("IDLE"); // pegar nome do panel 3.1

			// ec1.setPrev(idle);
			// ec1.setNext(idle);
			//

			EntityClass ec2 = factory.createEntityClass();
			Dead wait1 = factory.createDead();
			wait1.setId("IDLE"); // pegar nome do panel 3.1
			// ec1.setPrev(wait1);
			// ec1.setNext(b1);

			regularActivity.setStat(uniform);
			regularActivity.setGraphic(box);
			regularActivity.getEntityClass().add(ec1);
			regularActivity.getEntityClass().add(ec2);
			
			acd.getAct().add(regularActivity);	
			regularActivity = factory.createAct();
		}
	
		return acd;
	}
	
	public Acd buildGenerateActivities(Acd acd, List<WorkProduct> workProducts) {

		ObjectFactory factory = new ObjectFactory();
		Generate callGenerate = factory.createGenerate();
		
		for (WorkProduct workProduct: workProducts) {
			callGenerate.setId("Generate : " + workProduct.getName());
			// Class clazz = factory.createClass(); // new
			// clazz.setId("CALL"); // new
            // callGenerate.setClazz((Class) acd.getClazz().get(0)); // new
			
			// Falta uma coluna no panel 3.2. Demand WorkProduct possui distribuicao de probabilidade
			Stat negExp = factory.createStat();
			negExp.setType("NEGEXP");
			negExp.setParm1("7.0");
			
			Graphic box = factory.createGraphic();
			box.setType("BOX");
			box.setX("73");
			box.setY("101");
			
			ActObserver actObserver = factory.createActObserver();
			actObserver.setType("ACTIVE");
			actObserver.setName("CALL_OBS");
			
			Next nextDead = factory.createNext();
			// nextDead.setDead(dead);
			callGenerate.getActObserver().add(actObserver);
			callGenerate.setGraphic(box);
			callGenerate.setStat(negExp);
			callGenerate.getNext().add(nextDead);

			acd.getGenerate().add(callGenerate);
			callGenerate = factory.createGenerate();
		}

		return acd;
	}

	public Acd buildDestroyActivities(Acd acd, List<WorkProduct> workProducts) {
		
		ObjectFactory factory = new ObjectFactory();
		Destroy destroyDep0 = factory.createDestroy();
		for (WorkProduct workProduct: workProducts) {
			
			destroyDep0.setId("Destroy : " + workProduct.getName());

			Stat uniform2 = factory.createStat();
			uniform2.setType("UNIFORM");
			uniform2.setParm1("0.0");
			uniform2.setParm2("10.0");

			Graphic box3 = factory.createGraphic();
			box3.setType("BOX");
			box3.setX("602");
			box3.setY("108");

			Prev previous = factory.createPrev();
			// previous.setDead(dead);
			destroyDep0.getPrev().add(previous);
			destroyDep0.setGraphic(box3);
			// esta faltando destroy.setStat no codigo
			acd.getDestroy().add(destroyDep0);
			destroyDep0 = factory.createDestroy();
		}

		return acd;
	}
	
	public String buildProcess(String acdId, List<Role> roles, List<WorkProduct> workProducts, Set<String> tasks, 
			RoleResourcesPanel roleResourcePanel, WorkProductResourcesPanel workProdutResourcesPanel) {
        String teste = buildXACDML(acdId, roles, workProducts, tasks, roleResourcePanel, workProdutResourcesPanel);
        return teste;
	}
	
	public String buildXACDML(String acdId, List<Role> roles, List<WorkProduct> workProducts, Set<String> tasks, 
			RoleResourcesPanel roleResourcePanel, WorkProductResourcesPanel workProdutResourcesPanel){
		
		ObjectFactory factory = new ObjectFactory();
		
		Acd acd = factory.createAcd();
		acd.setId(acdId);
		
		Simtime simulationTime = new Simtime();
		simulationTime.setTime("500");
		acd.setSimtime(simulationTime);
		
  		Dead deadPermanentEntity = factory.createDead();

 		// Creation of dead resource queues necessary to the creation of regular activities
  	    // nao uso for each pois preciso do indice para pegar a quantidade digitada na coluna
  		for (int i = 0; i < roles.size(); i++) {  

			Role role = roles.get(i);
 			Class permanentEntity = factory.createClass();
 			permanentEntity.setId(role.getName());
			acd.getClazz().add(permanentEntity);
			
			deadPermanentEntity.setId("Resource queue: " + role.getName());
			deadPermanentEntity.setClazz(permanentEntity);
 			
			 
			JTable roleTable = roleResourcePanel.getTableRole();
			Integer quantity = (Integer)roleTable.getModel().getValueAt(i, 1);
            role.setIntialQuantity(quantity);
            
            
            Type queue = factory.createType();
			queue.setStruct("QUEUE");
			queue.setSize(Integer.toString(role.getIntialQuantity()));
			queue.setInit("0"); // conferir
			deadPermanentEntity.setType(queue);
			
            Boolean isStationary = (Boolean)roleTable.getModel().getValueAt(i, 2);
            if (isStationary) {
            	QueueObserver queueObserver = factory.createQueueObserver();
            	queueObserver.setType("STATIONARY");
            	deadPermanentEntity.getQueueObserver().add(queueObserver);
            }
            
			// Verificar a necessidade de colocar Graphic
			//Graphic circle = factory.createGraphic();
			//circle.setType("CIRCLE");
			//circle.setX("198");
			//circle.setY("349");
			//dead.setGraphic(circle);
			
			// buscar valores do JTable 3.2 - Falta um tipo de observer - nem todos no xacdml da tese tem queuobserver
			QueueObserver queueObserver = factory.createQueueObserver();
			queueObserver.setType("LENGTH");
			queueObserver.setName("SERVICE_OBS");
			deadPermanentEntity.getQueueObserver().add(queueObserver);

			acd.getDead().add(deadPermanentEntity);
			deadPermanentEntity = factory.createDead();
		}
 		
		// build generate activities
		
		Generate generateActivity = factory.createGenerate();
		List<JPanel> listProbabilityDistributionPanel = workProdutResourcesPanel.getListOfProbabilityDistributionPanels();
		
		for (int i = 0; i < workProducts.size(); i++) {  

			WorkProduct workProduct = workProducts.get(i);
			
			JTable workProductTable = workProdutResourcesPanel.getTableWorkProduct();
			ProbabilityDistributionInnerPanel probabilityDistributionInnerPanel = (ProbabilityDistributionInnerPanel) listProbabilityDistributionPanel.get(i);
			
			System.out.println(probabilityDistributionInnerPanel.getName());
			System.out.println(probabilityDistributionInnerPanel.getPanelTitleLabel());
			System.out.println(probabilityDistributionInnerPanel.getComboBox().getSelectedItem());
			Parameters parameters = probabilityDistributionInnerPanel.getParameters();
			System.out.println(parameters.toString());

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
			
			generateActivity.setId("Generate activity for : " + workProduct.getName());
			
			Class temporalEntity = factory.createClass();
			temporalEntity.setId("Temporal entity " + workProduct.getName());
			acd.getClazz().add(temporalEntity);
	 		generateActivity.setClazz(temporalEntity);   

	 		
  			
			
			//Graphic box = factory.createGraphic();
			//box.setType("BOX");
			//box.setX("73");
			//box.setY("101");
			
			ActObserver actObserver = factory.createActObserver();
			actObserver.setType("ACTIVE");
			actObserver.setName("CALL_OBS");
			
			Dead deadTemporalEntity = factory.createDead();
			
			deadTemporalEntity.setId(workProduct.getQueueName());
			deadTemporalEntity.setClazz(temporalEntity);

			Type queue = factory.createType();
			queue.setStruct("QUEUE");
			queue.setSize(Integer.toString(workProduct.getQuantity()));
			queue.setInit("0"); // conferir
			deadTemporalEntity.setType(queue);

			// Verificar a necessidade de colocar Graphic
			//Graphic circle = factory.createGraphic();
			//circle.setType("CIRCLE");
			//circle.setX("198");
			//circle.setY("349");
			//deadTemporalEntity.setGraphic(circle);

			// buscar valores do JTable 3.2 - Falta um tipo de observer - nem todos no xacdml da tese tem queuobserver
			QueueObserver queueObserver = factory.createQueueObserver();
			queueObserver.setType("LENGTH");
			queueObserver.setName("SERVICE_OBS");
			deadTemporalEntity.getQueueObserver().add(queueObserver);

			acd.getDead().add(deadTemporalEntity);
			
			Next nextDead = factory.createNext();
			nextDead.setDead(deadTemporalEntity);
			
 			generateActivity.getActObserver().add(actObserver);
//			callGenerate.setGraphic(box);
			generateActivity.setStat(distribution);
			generateActivity.getNext().add(nextDead);

			acd.getGenerate().add(generateActivity);
			
		 
		// end build generate activities
		
		// begin destroy
			Destroy destroyDep0 = factory.createDestroy();

			destroyDep0.setId("Destroy : " + workProduct.getName());
//			destroyDep0.setClazz(temporalEntity);  (vou precisar colocar isso dentro do laco de roles, para cada iteracao, tenho que fazer tudo)

			Stat uniform2 = factory.createStat();
			uniform2.setType("UNIFORM");
			uniform2.setParm1("0.0");
			uniform2.setParm2("10.0");

//			Graphic box3 = factory.createGraphic();
//			box3.setType("BOX");
//			box3.setX("602");
//			box3.setY("108");

			//Prev previous = factory.createPrev();
			//previous.setDead(dead);
			//destroyDep0.getPrev().add(previous);
//			destroyDep0.setGraphic(box3);
			// esta faltando destroy.setStat no codigo
			
			acd.getDestroy().add(destroyDep0);
			
			generateActivity = factory.createGenerate();
			destroyDep0 = factory.createDestroy();
		}
			
			// end destroy
		
		 
 		
		// begin buildActivities
		
         Act regularActivity = factory.createAct();
		
		for (String task: tasks){
			
			regularActivity.setId(task);

			Stat uniform = factory.createStat();
			uniform.setType("UNIFORM");
			uniform.setParm1("1.0");
			uniform.setParm2("5.0");

			Graphic box = factory.createGraphic();
			box.setType("BOX");
			box.setX("319");
			box.setY("110");
//
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
			regularActivity.setStat(uniform);
			regularActivity.setGraphic(box);
//			regularActivity.getEntityClass().add(ec1);
//			regularActivity.getEntityClass().add(ec2);
			
			acd.getAct().add(regularActivity);	
			regularActivity = factory.createAct();
		}
		
		// end buildActivities
		
		
		
		this.acd = acd;
		String result = null;
		try {
			result = persistProcessInXMLWithJAXBOnlyString(acd);
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
