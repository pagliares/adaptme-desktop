//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.19 at 07:02:24 PM BRST 
//


package xacdml.model.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cond",
    "_default"
})
@XmlRootElement(name = "listcond")
public class Listcond {

    @XmlElement(required = true)
    protected List<Cond> cond;
    @XmlElement(name = "default")
    protected Default _default;

    /**
     * Gets the value of the cond property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cond property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCond().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cond }
     * 
     * 
     */
    public List<Cond> getCond() {
        if (cond == null) {
            cond = new ArrayList<Cond>();
        }
        return this.cond;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link Default }
     *     
     */
    public Default getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link Default }
     *     
     */
    public void setDefault(Default value) {
        this._default = value;
    }

}
