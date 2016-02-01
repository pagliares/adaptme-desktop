package model.spem.derived;

public class GammaParameters extends Parameters {
    private double shape;
    private double scale;
    private double inverseCumAccuracy;

    public double getShape() {
	return shape;
    }

    public void setShape(double shape) {
	this.shape = shape;
    }

    public double getScale() {
	return scale;
    }

    public void setScale(double scale) {
	this.scale = scale;
    }

    public double getInverseCumAccuracy() {
	return inverseCumAccuracy;
    }

    public void setInverseCumAccuracy(double inverseCumAccuracy) {
	this.inverseCumAccuracy = inverseCumAccuracy;
    }

}
