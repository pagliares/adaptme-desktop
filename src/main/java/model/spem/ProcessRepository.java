package model.spem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.util.ProcessContentType;

@Entity
@Table(name = "PROCESS")
@XmlRootElement(name = "process")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessRepository implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Integer id;

    @XmlAttribute
    private String name = "Teste process name";
    
    @XmlAttribute
    private String simulationObjective;


    @XmlAttribute
    private boolean chosen;

    @XmlElementRef(name = "process_content")
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "processRepository")
    private List<ProcessContentRepository> processContents;

    @ManyToOne
    @XmlTransient
    private SimulationFacade simulationFacade;
    
    @XmlTransient
    // faltar ignorar JPA
    private List<ProcessContentRepository> listTasks = new ArrayList<>();

    public ProcessRepository() {
	processContents = new ArrayList<>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isChosen() {
	return chosen;
    }

    public void setChosen(boolean chosen) {
	this.chosen = chosen;
    }

    public SimulationFacade getSimulationFacade() {
	return simulationFacade;
    }

    public void setSimulationFacade(SimulationFacade simulationFacade) {
	this.simulationFacade = simulationFacade;
    }

    public boolean addProcessElement(ProcessContentRepository processContentsRepository) {
	return processContents.add(processContentsRepository);
    }

    public List<ProcessContentRepository> getProcessContents() {
	return processContents;
    }

    public void setProcessContents(List<ProcessContentRepository> processContents) {
	this.processContents = processContents;
    }

    @Override
    public String toString() {
	return name;
    }

	public String getSimulationObjective() {
		return simulationObjective;
	}

	public void setSimulationObjective(String simulationObjective) {
		this.simulationObjective = simulationObjective;
	}
	
	// With this return, i can get list of roles, additional roles, input/output workproducts
	public List<ProcessContentRepository> getListProcessContentRepositoryWithTasksOnly(List<ProcessContentRepository> listOfProcessContentRepository) {
 		if (listOfProcessContentRepository == null) {
			return null;
		} else {
			for (ProcessContentRepository pcr: listOfProcessContentRepository ) {
				if (pcr.getType().equals(ProcessContentType.TASK)) {
					listTasks.add(pcr);
				} else
					getListProcessContentRepositoryWithTasksOnly(pcr.getChildren());		 
			}
			return listTasks;
		}
	}
	
	// Este metodo removeu um erro muito dificil que era a geracao de varias tarefas no xacdml duplicada
	public void clearListOfTasks() {
		listTasks.clear();
	}
	
	public void imprimeTasks(List<ProcessContentRepository> listOfProcessContentRepository) {
		 
		if (listOfProcessContentRepository == null) {
			return;
		} else {
			for (ProcessContentRepository pcr: listOfProcessContentRepository ) {
				if (pcr.getType().equals(ProcessContentType.TASK)) {
					System.out.println(pcr.getName());
				} else
					imprimeTasks(pcr.getChildren()); 
			}
		 
		}
	}
	
	
	
	
}
