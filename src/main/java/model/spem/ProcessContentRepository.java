package model.spem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.util.ProcessContentType;

@Entity
@Table(name = "PROCESS_CONTENT")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessContentRepository implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private boolean repeatable;
    
    @XmlAttribute
    private boolean hasMultipleOccurrences;
    
    @XmlAttribute
    private boolean eventDriven;
    
    @XmlAttribute
    private boolean ongoing;
    
    @XmlAttribute
    private boolean optional;
    
    @XmlAttribute
    private boolean planned;

    @XmlAttribute
    @Enumerated(EnumType.STRING)
    private ProcessContentType type;

    @ManyToOne
    @XmlTransient
    private ProcessRepository processRepository; // match the mapped by
						 // attribute of @OneToMany in
						 // WorkBreakDownStructure

    @XmlElementRef(name = "process_content")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processContentRepository")
    private Set<MethodContentRepository> inputMethodContentsRepository = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processContentRepository")
    private Set<MethodContentRepository> outputMethodContentsRepository = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "processContentRepository")
    private Sample sample;

    @XmlElementRef(name = "process_element")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "father")
    private List<ProcessContentRepository> children = new ArrayList<ProcessContentRepository>();

    @ManyToOne
    @XmlTransient
    private ProcessContentRepository father;

    // @XmlElementRef(name="process_element")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predecessor")
    @XmlTransient
    private List<ProcessContentRepository> predecessors = new ArrayList<ProcessContentRepository>();

    @ManyToOne
    @XmlTransient
    private ProcessContentRepository predecessor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processContentRepository")
    private List<MethodContentRepository> additionalRoles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private MethodContentRepository mainRole;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Sample getSample() {
	return sample;
    }

    public void setSample(Sample sample) {
	this.sample = sample;
    }

    public ProcessContentRepository getFather() {
	return father;
    }

    public void setFather(ProcessContentRepository father) {
	this.father = father;
    }

    public List<ProcessContentRepository> getChildren() {
	return children;
    }

    public void setChildren(List<ProcessContentRepository> children) {
	this.children = children;
    }

    public boolean addChild(ProcessContentRepository processElement) {
	ProcessContentType processContentTypeFather = type;

	switch (processContentTypeFather) {
	case PHASE:
	    children.add(processElement);
	    break;

	case ITERATION:
	    children.add(processElement);
	    break;

	case ACTIVITY:
	    children.add(processElement);
	    break;

	default:
	    return false;
	}
	return true;
    }

    public boolean addPredecessor(ProcessContentRepository processElement) {
	return predecessors.add(processElement);
    }

    public boolean addInputMethodContent(MethodContentRepository inputMethodContent) {
	return inputMethodContentsRepository.add(inputMethodContent);
    }

    public boolean addOutputMethodContent(MethodContentRepository outputMethodContent) {
	return outputMethodContentsRepository.add(outputMethodContent);
    }

    public List<ProcessContentRepository> getPredecessors() {
	return predecessors;
    }

    public void setPredecessors(List<ProcessContentRepository> predecessors) {
	this.predecessors = predecessors;
    }

    public void setPredecessor(ProcessContentRepository predecessor) {
	this.predecessor = predecessor;
    }

    public ProcessContentType getType() {
	return type;
    }

    public void setType(ProcessContentType type) {
	this.type = type;
    }

    public Set<MethodContentRepository> getInputMethodContentsRepository() {
	return inputMethodContentsRepository;
    }

    public void setInputMethodContentsRepository(Set<MethodContentRepository> inputMethodContentsRepository) {
	this.inputMethodContentsRepository = inputMethodContentsRepository;
    }

    public Set<MethodContentRepository> getOutputMethodContentsRepository() {
	return outputMethodContentsRepository;
    }

    public void setOutputMethodContentsRepository(Set<MethodContentRepository> outputMethodContentsRepository) {
	this.outputMethodContentsRepository = outputMethodContentsRepository;
    }

    @Override
    public String toString() {
	return name;
    }

    public List<MethodContentRepository> getAdditionalRoles() {
	return additionalRoles;
    }

    public void setAdditionalRoles(List<MethodContentRepository> additionalRoles) {
	this.additionalRoles = additionalRoles;
    }

    public MethodContentRepository getMainRole() {
	return mainRole;
    }

    public void setMainRole(MethodContentRepository mainRole) {
	this.mainRole = mainRole;
    }

    public ProcessRepository getProcessRepository() {
	return processRepository;
    }

    public void setProcessRepository(ProcessRepository processRepository) {
	this.processRepository = processRepository;
    }

    public boolean isRepeatable() {
	return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
	this.repeatable = repeatable;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isHasMultipleOccurrences() {
		return hasMultipleOccurrences;
	}

	public void setHasMultipleOccurrences(boolean hasMultipleOccurrences) {
		this.hasMultipleOccurrences = hasMultipleOccurrences;
	}

	public boolean isEventDriven() {
		return eventDriven;
	}

	public void setEventDriven(boolean eventDriven) {
		this.eventDriven = eventDriven;
	}

	public boolean isOngoing() {
		return ongoing;
	}

	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isPlanned() {
		return planned;
	}

	public void setPlanned(boolean planned) {
		this.planned = planned;
	}

}
