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
	    return new NegativeExponential();
//	case BINOMIAL:
//	    return new BinomialParameters();
//	case GAMMA:
//	    return new GammaParameters();
//	case GEOMETRIC:
//	    return new GeometricParameters();
//	case LOG_NORMAL:
//	    return new LogNormalParameters();
	case NORMAL:
	    return new NormalParameters();
	case POISSON:
	    return new PoissonParameters();
	default:
	    return null;
	}
    }
}
