package model.spem;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.util.MethodContentType;

@Entity
@Table(name = "METHOD_CONTENT")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MethodContentRepository implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    @XmlAttribute
    private MethodContentType type;

    @XmlTransient
    @ManyToOne
    private ProcessContentRepository processContentRepository;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "methodContentRepository")
    private Sample sample;

    public MethodContentRepository() {
	// TODO Auto-generated constructor stub
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public ProcessContentRepository getProcessElementRepository() {
	return processContentRepository;
    }

    public void setProcessContentRepository(ProcessContentRepository processElementRepository) {
	processContentRepository = processElementRepository;
    }

    public MethodContentType getType() {
	return type;
    }

    public void setType(MethodContentType type) {
	this.type = type;
    }

    public Sample getSample() {
	return sample;
    }

    public void setSample(Sample sample) {
	this.sample = sample;
    }

    @Override
    public String toString() {
	return name;
    }

}
