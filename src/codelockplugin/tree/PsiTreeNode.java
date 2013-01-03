package codelockplugin.tree;

import com.intellij.psi.PsiElement;

import javax.swing.tree.TreeNode;

/**
 * adds more methods to the regular TreeNode.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public interface PsiTreeNode extends TreeNode {
    public PsiElement loadPsi();

}
