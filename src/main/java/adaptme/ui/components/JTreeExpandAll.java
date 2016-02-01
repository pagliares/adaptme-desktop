package adaptme.ui.components;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class JTreeExpandAll {
    // If expand is true, expands all nodes in the tree.
    // Otherwise, collapses all nodes in the tree.
    public static void expandAll(JTree tree, boolean expand) {
	TreeNode root = (TreeNode) tree.getModel().getRoot();

	// Traverse tree from root
	expandAll(tree, new TreePath(root), expand);
    }

    /**
     * @return Whether an expandPath was called for the last node in the parent
     *         path
     */
    private static boolean expandAll(JTree tree, TreePath parent, boolean expand) {
	// Traverse children
	TreeNode node = (TreeNode) parent.getLastPathComponent();
	if (node.getChildCount() > 0) {
	    boolean childExpandCalled = false;
	    for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
		TreeNode n = (TreeNode) e.nextElement();
		TreePath path = parent.pathByAddingChild(n);
		childExpandCalled = expandAll(tree, path, expand) || childExpandCalled; // the
											// OR
											// order
											// is
											// important
											// here,
											// don't
											// let
											// childExpand
											// first.
											// func
											// calls
											// will
											// be
											// optimized
											// out
											// !
	    }

	    if (!childExpandCalled) { // only if one of the children hasn't
				      // called already expand
		// Expansion or collapse must be done bottom-up, BUT only for
		// non-leaf nodes
		if (expand) {
		    tree.expandPath(parent);
		} else {
		    tree.collapsePath(parent);
		}
	    }
	    return true;
	} else {
	    return false;
	}
    }
}
