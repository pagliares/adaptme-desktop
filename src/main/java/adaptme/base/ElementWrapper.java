
package adaptme.base;

import javax.swing.ImageIcon;

import org.eclipse.epf.uma.Element;

/**
 * Encapsula um elemento UMA para generalizar seu uso.
 * 
 * @author eugf
 */
public class ElementWrapper extends Element {

    private String name = "unamed";
    private Element element = null;
    private ImageIcon icon = null;

    public ElementWrapper(Element element, String name, ImageIcon icon) {
	if (element != null) {
	    this.element = element;
	}

	if (name != null && !name.isEmpty()) {
	    this.name = name;
	}

	if (icon != null) {
	    this.icon = icon;
	}
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Element getElement() {
	return element;
    }

    public ImageIcon getIcon() {
	return icon;
    }

    @Override
    public String toString() {
	return name;
    }

}
