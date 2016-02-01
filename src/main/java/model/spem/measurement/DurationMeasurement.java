package model.spem.measurement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.spem.Sample;
import model.spem.util.TimeEnum;

@Entity
@DiscriminatorValue("duration measurement") // opcional
@XmlRootElement(name = "duration measurement")
@XmlAccessorType(XmlAccessType.FIELD)
public class DurationMeasurement extends Measurement implements Serializable {

    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    // private Integer id;

    private double duration;

    @ManyToOne
    private Sample sample;

    @Enumerated(EnumType.STRING)
    private TimeEnum scale;

    // public Integer getId() {
    // return id;
    // }
    //
    // public void setId(Integer id) {
    // this.id = id;
    // }

    public double getValue() {
	return duration;
    }

    public void setValue(double value) {
	this.duration = value;
    }

    public Sample getSample() {
	return sample;
    }

    public void setSample(Sample sample) {
	this.sample = sample;
    }

    public TimeEnum getScale() {
	return scale;
    }

    public void setScale(TimeEnum scale) {
	this.scale = scale;
    }
}
