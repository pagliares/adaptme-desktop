package model.spem.measurement;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import model.spem.Sample;
import model.spem.util.TimeEnum;

@Entity
@DiscriminatorValue("duration measurement") // opcional
@XmlRootElement(name="duration_measurement")
@XmlAccessorType(XmlAccessType.FIELD)
public class DurationMeasurement extends Measurement implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double value;
	
 
	@Enumerated(EnumType.STRING)
	private TimeEnum scale;

 
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

 
	public TimeEnum getScale() {
		return scale;
	}

	public void setScale(TimeEnum scale) {
		this.scale = scale;
	}
	
	@Override
		public String toString() {
			return Double.toString(value) + " " + scale;
		}
}
