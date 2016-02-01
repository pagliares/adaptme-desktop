//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.19 at 07:02:24 PM BRST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "graphic",
    "stat",
    "entityClass",
    "whennext",
    "actObserver"
})
@XmlRootElement(name = "router")
public class Router {

    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;
    protected Graphic graphic;
    @XmlElement(required = true)
    protected Stat stat;
    @XmlElement(name = "entity_class", required = true)
    protected List<EntityClass> entityClass;
    @XmlElement(required = true)
    protected List<Whennext> whennext;
    @XmlElement(name = "act-observer")
    protected List<ActObserver> actObserver;

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
     * Gets the value of the graphic property.
     * 
     * @return
     *     possible object is
     *     {@link Graphic }
     *     
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * Sets the value of the graphic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Graphic }
     *     
     */
    public void setGraphic(Graphic value) {
        this.graphic = value;
    }

    /**
     * Gets the value of the stat property.
     * 
     * @return
     *     possible object is
     *     {@link Stat }
     *     
     */
    public Stat getStat() {
        return stat;
    }

    /**
     * Sets the value of the stat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stat }
     *     
     */
    public void setStat(Stat value) {
        this.stat = value;
    }

    /**
     * Gets the value of the entityClass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entityClass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityClass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityClass }
     * 
     * 
     */
    public List<EntityClass> getEntityClass() {
        if (entityClass == null) {
            entityClass = new ArrayList<EntityClass>();
        }
        return this.entityClass;
    }

    /**
     * Gets the value of the whennext property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the whennext property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWhennext().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Whennext }
     * 
     * 
     */
    public List<Whennext> getWhennext() {
        if (whennext == null) {
            whennext = new ArrayList<Whennext>();
        }
        return this.whennext;
    }

    /**
     * Gets the value of the actObserver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actObserver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActObserver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActObserver }
     * 
     * 
     */
    public List<ActObserver> getActObserver() {
        if (actObserver == null) {
            actObserver = new ArrayList<ActObserver>();
        }
        return this.actObserver;
    }

}