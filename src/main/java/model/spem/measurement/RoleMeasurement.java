package model.spem.measurement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import model.spem.Sample;
import model.spem.util.TimeEnum;

@Entity
@DiscriminatorValue("role measurement") // opcional
@XmlRootElement(name = "role measurement")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleMeasurement extends Measurement implements Serializable {

    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    // private Integer id;

    private int skill; // from 0 to 10
    private int experience;
    private TimeEnum experienceScale;

    @ManyToOne
    private Sample sample;

    // public Integer getId() {
    // return id;
    // }
    //
    // public void setId(Integer id) {
    // this.id = id;
    // }

    public Sample getSample() {
	return sample;
    }

    public void setSample(Sample sample) {
	this.sample = sample;
    }

    public int getSkill() {
	return skill;
    }

    public void setSkill(int skill) {
	this.skill = skill;
    }

    public int getExperience() {
	return experience;
    }

    public void setExperience(int experience) {
	this.experience = experience;
    }

    public TimeEnum getExperienceScale() {
	return experienceScale;
    }

    public void setExperienceScale(TimeEnum experienceScale) {
	this.experienceScale = experienceScale;
    }

}
