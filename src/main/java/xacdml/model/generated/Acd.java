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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "simtime",
    "clazz",
    "generate",
    "dead",
    "act",
    "router",
    "destroy",
    "instanceOrConnectOrSubmodel"
})
@XmlRootElement(name = "acd")
public class Acd {

    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;
    protected Simtime simtime;
    @XmlElement(name = "class", required = true)
    protected List<Class> clazz;
    protected List<Generate> generate;

    @XmlElement(required = true)
    protected List<Dead> dead;
    @XmlElement(required = true)
    protected List<Act> act;
    protected List<Router> router;
    protected List<Destroy> destroy;
    @XmlElements({
        @XmlElement(name = "instance", type = Instance.class),
        @XmlElement(name = "connect", type = Connect.class),
        @XmlElement(name = "submodel", type = Submodel.class)
    })
    protected List<Object> instanceOrConnectOrSubmodel;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the simtime property.
     * 
     * @return
     *     possible object is
     *     {@link Simtime }
     *     
     */
    public Simtime getSimtime() {
        return simtime;
    }

    /**
     * Sets the value of the simtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Simtime }
     *     
     */
    public void setSimtime(Simtime value) {
        this.simtime = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clazz property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClazz().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Class }
     * 
     * 
     */
    public List<Class> getClazz() {
        if (clazz == null) {
            clazz = new ArrayList<Class>();
        }
        return this.clazz;
    }

    /**
     * Gets the value of the generate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Generate }
     * 
     * 
     */
    public List<Generate> getGenerate() {
        if (generate == null) {
            generate = new ArrayList<Generate>();
        }
        return this.generate;
    }

    /**
     * Gets the value of the dead property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dead property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDead().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dead }
     * 
     * 
     */
    public List<Dead> getDead() {
        if (dead == null) {
            dead = new ArrayList<Dead>();
        }
        return this.dead;
    }

    /**
     * Gets the value of the act property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the act property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Act }
     * 
     * 
     */
    public List<Act> getAct() {
        if (act == null) {
            act = new ArrayList<Act>();
        }
        return this.act;
    }

    /**
     * Gets the value of the router property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the router property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Router }
     * 
     * 
     */
    public List<Router> getRouter() {
        if (router == null) {
            router = new ArrayList<Router>();
        }
        return this.router;
    }

    /**
     * Gets the value of the destroy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destroy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestroy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Destroy }
     * 
     * 
     */
    public List<Destroy> getDestroy() {
        if (destroy == null) {
            destroy = new ArrayList<Destroy>();
        }
        return this.destroy;
    }

    /**
     * Gets the value of the instanceOrConnectOrSubmodel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanceOrConnectOrSubmodel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanceOrConnectOrSubmodel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Instance }
     * {@link Connect }
     * {@link Submodel }
     * 
     * 
     */
    public List<Object> getInstanceOrConnectOrSubmodel() {
        if (instanceOrConnectOrSubmodel == null) {
            instanceOrConnectOrSubmodel = new ArrayList<Object>();
        }
        return this.instanceOrConnectOrSubmodel;
    }
    
    @Override
    public String toString() {
    	return id;
    }

}
