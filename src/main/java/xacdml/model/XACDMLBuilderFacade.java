package xacdml.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

	private Acd buildDeadStates(Acd acd) {

		ObjectFactory factory = new ObjectFactory();

		// ---------------------------- Begin Dead State WAIT0

		Dead dead = factory.createDead();
		dead.setId("WAIT0");
		// dead.setClazz(inquirer);

		Type queue = factory.createType();
		queue.setStruct("QUEUE");
		queue.setSize("10");
		queue.setInit("0");
		dead.setType(queue);

		Graphic circle = factory.createGraphic();
		circle.setType("CIRCLE");
		circle.setX("198");
		circle.setY("349");
		dead.setGraphic(circle);

		QueueObserver queueObserver = factory.createQueueObserver();
		queueObserver.setType("LENGTH");
		queueObserver.setName("SERVICE_OBS");
		dead.getQueueObserver().add(queueObserver);

		acd.getDead().add(dead);

		// ---------------------------- END Dead State WAIT0

		// ---------------------------- Begin Dead State WAIT1

		Dead dead1 = factory.createDead();
		dead1.setId("WAIT1");
		// // wait1.setClazz(caller);

		Type queue1 = factory.createType();
		queue1.setStruct("QUEUE");
		queue1.setSize("10");
		queue1.setInit("0");
		dead1.setType(queue1);

		Graphic circle3 = factory.createGraphic();
		circle3.setType("CIRCLE");
		circle3.setX("197");
		circle3.setY("107");
		dead1.setGraphic(circle3);

		QueueObserver queueObserver1 = factory.createQueueObserver();
		queueObserver1.setType("LENGTH");
		queueObserver1.setName("TALK_OBS");
		dead1.getQueueObserver().add(queueObserver1);

		acd.getDead().add(dead1);

		// ---------------------------- END Dead State WAIT1

		// ------------------------------START Dead state B0

		Dead deadB0 = factory.createDead();
		deadB0.setId("B0");
		// deadB0.setClazz(idle);

		Type queue5 = factory.createType();
		queue5.setStruct("QUEUE");
		queue5.setSize("10");
		queue5.setInit("0");
		deadB0.setType(queue5);

		Graphic circle5 = factory.createGraphic();
		circle5.setType("CIRCLE");
		circle5.setX("476");
		circle5.setY("358");
		deadB0.setGraphic(circle5);

		acd.getDead().add(deadB0);

		// ------------------------------END Dead state B0

		// ------------------------------START Dead state B1

		Dead deadB1 = factory.createDead();
		deadB1.setId("B1");
		// deadB1.setClazz(idle);

		Type queue6 = factory.createType();
		queue6.setStruct("QUEUE");
		queue6.setSize("10");
		queue6.setInit("0");
		deadB1.setType(queue6);

		Graphic circle6 = factory.createGraphic();
		circle6.setType("CIRCLE");
		circle6.setX("462");
		circle6.setY("111");
		deadB1.setGraphic(circle6);

		acd.getDead().add(deadB1);

		// ------------------------------END Dead state B0

		// ------------------------------START Dead state IDLE

		Dead deadIdle = factory.createDead();
		deadIdle.setId("IDLE");
		// deadIdle.setClazz(idle);

		Type queue4 = factory.createType();
		queue4.setStruct("QUEUE");
		queue4.setSize("2");
		queue4.setInit("2");
		deadIdle.setType(queue4);

		Graphic circle4 = factory.createGraphic();
		circle4.setType("CIRCLE");
		circle4.setX("334");
		circle4.setY("222");
		deadIdle.setGraphic(circle4);

		acd.getDead().add(deadIdle);

		// ------------------------------END Dead state IDLE

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

	private Acd buildGenerateActivities(Acd acd) {

		ObjectFactory factory = new ObjectFactory();

		// ---------------------------- Begin CallGenerate
		Generate callGenerate = factory.createGenerate();
		callGenerate.setId("CALL");

		// Class clazz = factory.createClass(); // new
		// clazz.setId("CALL"); // new
		//
		//
//		callGenerate.setClazz((Class) acd.getClazz().get(0)); // new

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

		// ------------------------------END CallGenerate

		// ---------------------------- Begin Generate Activity ARRIVAL

		Generate arrivalGenerate = factory.createGenerate();
		arrivalGenerate.setId("ARRIVAL");

		Stat negExp2 = factory.createStat();
		negExp2.setParm1("5.0");
		negExp2.setType("NEGEXP");

		Graphic box6 = factory.createGraphic();
		box6.setType("BOX");
		box6.setX("90");
		box6.setY("353");

		ActObserver arrivalActObserver = factory.createActObserver();
		arrivalActObserver.setType("ACTIVE");
		arrivalActObserver.setName("CUSTOMER_OBS");

		Next nextDead2 = factory.createNext();
		// nextDead.setDead(dead);
		arrivalGenerate.getNext().add(nextDead2);

		arrivalGenerate.setGraphic(box6);
		arrivalGenerate.setStat(negExp2);
		arrivalGenerate.getActObserver().add(arrivalActObserver);
		acd.getGenerate().add(arrivalGenerate);

		// ---------------------------- END Generate Activity ARRIVAL
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
		acd = buildDeadStates(acd);
		acd = buildGenerateActivities(acd);

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
