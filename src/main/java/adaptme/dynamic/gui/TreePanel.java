package adaptme.dynamic.gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import adaptme.dynamic.gui.renderer.TreeTableCellRenderSimpleUMA;

public class TreePanel extends JPanel implements UpdatePanel {

    private JTree tree;

    public TreePanel(DefaultTreeModel model) {
	tree = new JTree(model);
	tree.setCellRenderer(new TreeTableCellRenderSimpleUMA());
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tree,
		GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tree,
		GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));
	setLayout(groupLayout);
    }

    @Override
    public void updateContent() {
    }

    @Override
    public JPanel getPanel() {
	return this;
    }

    public JTree getTree() {
	return tree;
    }
}
