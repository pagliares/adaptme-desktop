package model.spem.measurement;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.Sample;
import model.spem.util.MeasurementType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="MEASUREMENT_TYPE")
@XmlRootElement(name="measurement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({DurationMeasurement.class, RoleMeasurement.class, WorkProductMeasurement.class})
//@XmlSeeAlso(DurationMeasurement.class)

public class Measurement implements Serializable {
	
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	
	@ManyToOne
	@XmlTransient 
	private Sample sample;

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Measurement createMeasurement(MeasurementType measurementType) {
		Measurement measurement = null;
		switch (measurementType) {
		case DURATION_MEASUREMENT:
			measurement = new DurationMeasurement();
			break;
		case ROLE_MEASUREMENT:
			measurement = new RoleMeasurement();
			break;
		case WORK_PRODUCT_MEASUREMENT:
			measurement = new WorkProductMeasurement();
			break;
		}
	 
		return measurement;	 
	}

}
