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
    private boolean chosen;

    @XmlElementRef(name = "process_content")
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "processRepository")
    private List<ProcessContentRepository> processContents;

    @ManyToOne
    @XmlTransient
    private SimulationFacade simulationFacade;

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
}