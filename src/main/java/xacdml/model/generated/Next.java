//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.19 at 07:02:24 PM BRST 
//


package xacdml.model.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "next")
public class Next {
	
	//	Esta propriedade id nao existe no original
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	protected String id;

    @XmlAttribute(name = "dead", required = true)
    @XmlIDREF
    protected Object dead;

    /**
     * Gets the value of the dead property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDead() {
        return dead;
    }

    /**
     * Sets the value of the dead property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    
    public void setDead(Object value) {
        this.dead = value;
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
    public String toString() {
    	return id;
    }

}
