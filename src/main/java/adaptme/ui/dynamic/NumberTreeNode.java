package adaptme.ui.dynamic;

import javax.swing.tree.DefaultMutableTreeNode;

public class NumberTreeNode extends DefaultMutableTreeNode implements NumberCompontent {

	private String key;
	private int number;
	
	public NumberTreeNode(Object object, String key) {
		super(object);
		this.key = key;
	}

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
