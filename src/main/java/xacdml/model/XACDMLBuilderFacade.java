package xacdml.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.epf.uma.Task;

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
import xacdml.model.generated.Stat;
import xacdml.model.generated.Type;
import simulator.base.Role;
import simulator.base.WorkProduct;

public class XACDMLBuilderFacade {

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
	
	public String persistProcessInXMLWithJAXBOnlyString(Acd acd, String fileName) throws IOException {

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

	private Acd buildEntities(Acd acd) {
		ObjectFactory factory = new ObjectFactory();
		Class inquirer = factory.createClass();
		inquirer.setId("INQUIRER");
		acd.getClazz().add(inquirer);

		Class idle = factory.createClass();
		idle.setId("IDLE");
		acd.getClazz().add(idle);

		Class caller = factory.createClass();
		caller.setId("CALLER");
		acd.getClazz().add(caller);
		return acd;
	}
	
	public Acd buildEntities(List<Role> roles, List<WorkProduct> workProducts) {
		Acd acd = new Acd();
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
	
	public Acd buildActivities(Acd acd, List<Task> tasks) {
		ObjectFactory factory = new ObjectFactory();
		// ---------------------------- Begin Activity Talk
		Act talk = factory.createAct();
		talk.setId("TALK");

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
		idle.setId("IDLE");

		// ec1.setPrev(idle);
		// ec1.setNext(idle);
		//

		EntityClass ec2 = factory.createEntityClass();
		Dead wait1 = factory.createDead();
		// ec1.setPrev(wait1);
		// ec1.setNext(b1);

		talk.setStat(uniform);
		talk.setGraphic(box);
		talk.getEntityClass().add(ec1);
		talk.getEntityClass().add(ec2);
		acd.getAct().add(talk);
		// ---------------------------- End Activity Talk

		// ------------------------------START Activity SERVICE

		Act actService = factory.createAct();
		actService.setId("SERVICE");

		Stat uniform5 = factory.createStat();
		uniform5.setType("UNIFORM");
		uniform5.setParm1("2.0");
		uniform5.setParm2("6.0");

		Graphic box5 = factory.createGraphic();
		box5.setType("BOX");
		box5.setX("331");
		box5.setY("356");

		EntityClass ec5 = factory.createEntityClass();
		// ec1.setPrev(idle);
		// ec1.setNext(idle);

		EntityClass ec6 = factory.createEntityClass();
		// ec1.setPrev(wait0);
		// ec1.setNext(deadB0);

		actService.setStat(uniform5);
		actService.setGraphic(box5);
		actService.getEntityClass().add(ec1);
		actService.getEntityClass().add(ec2);
		
		acd.getAct().add(actService);

		// ------------------------------END Activity SERVICE

		return acd;
	}
	
	private Acd buildActivities(Acd acd) {
		ObjectFactory factory = new ObjectFactory();
		// ---------------------------- Begin Activity Talk
		Act talk = factory.createAct();
		talk.setId("TALK");

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
		idle.setId("IDLE");

		// ec1.setPrev(idle);
		// ec1.setNext(idle);
		//

		EntityClass ec2 = factory.createEntityClass();
		Dead wait1 = factory.createDead();
		// ec1.setPrev(wait1);
		// ec1.setNext(b1);

		talk.setStat(uniform);
		talk.setGraphic(box);
		talk.getEntityClass().add(ec1);
		talk.getEntityClass().add(ec2);
		acd.getAct().add(talk);
		// ---------------------------- End Activity Talk

		// ------------------------------START Activity SERVICE

		Act actService = factory.createAct();
		actService.setId("SERVICE");

		Stat uniform5 = factory.createStat();
		uniform5.setType("UNIFORM");
		uniform5.setParm1("2.0");
		uniform5.setParm2("6.0");

		Graphic box5 = factory.createGraphic();
		box5.setType("BOX");
		box5.setX("331");
		box5.setY("356");

		EntityClass ec5 = factory.createEntityClass();
		// ec1.setPrev(idle);
		// ec1.setNext(idle);

		EntityClass ec6 = factory.createEntityClass();
		// ec1.setPrev(wait0);
		// ec1.setNext(deadB0);

		actService.setStat(uniform5);
		actService.setGraphic(box5);
		actService.getEntityClass().add(ec1);
		actService.getEntityClass().add(ec2);
		
		acd.getAct().add(actService);

		// ------------------------------END Activity SERVICE

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
	
	
	private Acd buildDestroyActivities(Acd acd) {
		ObjectFactory factory = new ObjectFactory();
		// ---------------------------- Begin Destroy Activity DEP0

		Destroy destroyDep0 = factory.createDestroy();
		destroyDep0.setId("DEP0");

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

		// ---------------------------- END Destroy Activity DEP0

		// ---------------------------- Begin Destroy Activity DEP1
		Destroy destroyDep1 = factory.createDestroy();
		destroyDep1.setId("DEP1");

		Stat uniform3 = factory.createStat();
		uniform3.setType("UNIFORM");
		uniform3.setParm1("0.0");
		uniform3.setParm2("10.0");

		Graphic box7 = factory.createGraphic();
		box7.setType("BOX");
		box7.setX("600");
		box7.setY("354");

		Prev previousDeadB1 = factory.createPrev();
		// previousDeadB1.setDead(dead);
		destroyDep1.getPrev().add(previousDeadB1);
		destroyDep1.setGraphic(box7);

		acd.getDestroy().add(destroyDep1);

		// ---------------------------- Begin Destroy Activity DEP1

		return acd;

	}

	public Acd buildProcess(String acdId) {

		ObjectFactory factory = new ObjectFactory();
		Acd acd = factory.createAcd();
		acd.setId(acdId);

		acd = buildEntities(acd);
	//	acd = buildDeadStates(acd);
//		acd = buildGenerateActivities(acd);

		acd = buildActivities(acd);
		acd = buildDestroyActivities(acd);

		return acd;

	}

	public static void main(String[] args) throws IOException {
		XACDMLBuilderFacade t = new XACDMLBuilderFacade();
		Acd acd = t.buildProcess("HBC_Pagliares");
		t.persistProcessInXMLWithJAXB(acd, "ACD");
	}
}
