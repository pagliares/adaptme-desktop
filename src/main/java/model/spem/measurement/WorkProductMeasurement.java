package model.spem.measurement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import model.spem.Sample;

@Entity
public class WorkProductMeasurement extends Measurement implements Serializable {

    // @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    // private Integer id;

    private double size;
    private String sizeScale;

    private Integer quantity;
    private String quantityScale;

    @ManyToOne
    private Sample sample;
    private String name;

    // public Integer getId() {
    // return id;
    // }
    //
    // public void setId(Integer id) {
    // this.id = id;
    // }

    public double getValue() {
	return size;
    }

    public void setValue(double value) {
	size = value;
    }

    @Override
    public Sample getSample() {
	return sample;
    }

    @Override
    public void setSample(Sample sample) {
	this.sample = sample;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public double getSize() {
	return size;
    }

    public void setSize(double size) {
	this.size = size;
    }

    public String getSizeScale() {
	return sizeScale;
    }

    public void setSizeScale(String sizeScale) {
	this.sizeScale = sizeScale;
    }

    public String getQuantityScale() {
	return quantityScale;
    }

    public void setQuantityScale(String quantityScale) {
	this.quantityScale = quantityScale;
    }

    public void setName(String name) {
	this.name = name;
    }
}
