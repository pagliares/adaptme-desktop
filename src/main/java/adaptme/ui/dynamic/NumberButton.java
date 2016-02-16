package adaptme.ui.dynamic;

import javax.swing.JButton;

public class NumberButton extends JButton implements NumberCompontent {

    private static final long serialVersionUID = -3687241891711605039L;
    private String key;
    private int number;

    @Override
    public String getKey() {
	return key;
    }

    @Override
    public void setKey(String key) {
	this.key = key;
    }

    @Override
    public int getNumber() {
	return number;
    }

    @Override
    public void setNumber(int number) {
	this.number = number;
    }

}
