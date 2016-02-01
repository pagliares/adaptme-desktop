package model.spem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.measurement.Measurement;

@Entity
@Table(name = "SAMPLE")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
// @XmlSeeAlso(TaskDescriptorRepository.class)

// @XmlSeeAlso({TaskDescriptorRepository.class, ActivityRepository.class,
// IterationRepository.class, RoleDescriptorRepository.class})
public class Sample implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Integer id;

    @XmlAttribute
    private String name;

    @Transient
    private int size;

    @Transient
    private BestFitDistribution distribution;

    @Transient
    private Parameters parameters;

    @XmlTransient
    @OneToOne(cascade = CascadeType.PERSIST)
    private ProcessContentRepository processContentRepository;

    @XmlTransient
    @OneToOne(cascade = CascadeType.PERSIST)
    private MethodContentRepository methodContentRepository;

    @XmlTransient
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "sample")
    private List<Measurement> measurements;

    public Sample() {
	measurements = new ArrayList<>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BestFitDistribution getDistribution() {
	return distribution;
    }

    public void setDistribution(BestFitDistribution distribution) {
	this.distribution = distribution;
    }

    public Parameters getParameters() {
	return parameters;
    }

    public void setParameters(Parameters parameters) {
	this.parameters = parameters;
    }

    public List<Measurement> getMeasurements() {
	return measurements;
    }

    public void setMeasurement(List<Measurement> measurement) {
	measurements = measurement;
    }

    public boolean addMeasurement(Measurement measurement) {
	return measurements.add(measurement);
    }

    public Parameters computeParameters() {
	// Parameters parameters = new Parameters();
	// DurationMeasurement durationMeasurement = null;
	// double sum = 0.0;
	// double mean = 0.0;
	// double sd = 0.0;
	// for (Measurement measurement : measurements) {
	// if (measurement instanceof DurationMeasurement) {
	// durationMeasurement = (DurationMeasurement)measurement;
	// sum += durationMeasurement.getValue();
	// }
	// }
	// mean = sum / measurements.size();
	// sd = mean; // calculate sd
	// parameters.setMean(mean);
	// parameters.setStandardDeviation(sd);
	// return parameters;
	return null;
    }

    // TODO Verify if there is some API in Java that automatically makes best
    // fit from a given dataset
    public boolean computeDistribution() {
	if (Math.random() > 0.5) {
	    distribution = BestFitDistribution.LOG_NORMAL;
	} else {
	    distribution = BestFitDistribution.NORMAL;
	}
	return true;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public MethodContentRepository getMethodContentRepository() {
	return methodContentRepository;
    }

    public void setMethodContentRepository(MethodContentRepository methodContentRepository) {
	this.methodContentRepository = methodContentRepository;
    }

    public ProcessContentRepository getProcessContentRepository() {
	return processContentRepository;
    }

    public void setProcessContentRepository(ProcessContentRepository processContentRepository) {
	this.processContentRepository = processContentRepository;
    }

    @Override
    public String toString() {
	return name;
    }
}
