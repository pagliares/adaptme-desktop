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

import model.spem.Sample;
import model.spem.util.MeasurementType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MEASUREMENT_TYPE")
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Sample sample;

    public Sample getSample() {
	return sample;
    }

    public void setSample(Sample sample) {
	this.sample = sample;
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
