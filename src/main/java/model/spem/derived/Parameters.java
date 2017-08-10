package model.spem.derived;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Parameters {

	public static Parameters createParameter(BestFitDistribution distribution) {
		switch (distribution) {
		case CONSTANT:
			return new ConstantParameters();
		case UNIFORM:
			return new UniformParameters();
		case NEGATIVE_EXPONENTIAL:
			return new NegativeExponentialParameters();
		case NORMAL:
			return new NormalParameters();
		case POISSON:
			return new PoissonParameters();
		case LOGNORMAL:
			return new LogNormalParameters();
		case WEIBULL:
			return new WeibullParameters();
		case GAMMA:
			return new GammaParameters();
		case EXPONENTIAL:
			return new ExponentialParameters();
		default:
			return null;
		}
	}
}
