package codelockplugin.tree;

import codelockplugin.LockingPlugin;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

/**
 * Represents a locked Range in a file.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedRangeMarker extends LockedRegion implements PsiTreeNode {

    private int mStartOffset;
    private int mEndOffset;
    private RangeMarker mRangeMarker;

    public LockedRangeMarker(int startOffset, int endOffset, PsiTreeNode parent) {
        mStartOffset = startOffset;
        mEndOffset = endOffset;
        mParent = parent;
        mRangeMarker = null;
        setName();
    }

    public int getStartOffset() {
        return mStartOffset;
    }

    public int getEndOffset() {
        return mEndOffset;
    }

    public void setStartOffset(int startOffset) {
        mStartOffset = startOffset;
        setName();

    }

    private void setName() {
        mName = "Region_from_" + mStartOffset + "length_" + mEndOffset;
    }

    public void setEndOffset(int endOffset) {
        mEndOffset = endOffset;
        setName();
    }

    public void setRangeMarker(RangeMarker rangeMarker) {
        mRangeMarker = rangeMarker;
    }

    public RangeMarker getRangeMarker() {
        return mRangeMarker;
    }

    public PsiElement loadPsi() {
        return null;
    }

    public String toString() {
        return mName;
    }

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     */
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    public int getChildCount() {
        return 0;
    }

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     */
    public TreeNode getParent() {
        return mParent;
    }

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    public int getIndex(TreeNode node) {
        return 0;
    }

    /**
     * Returns true if the receiver allows children.
     */
    public boolean getAllowsChildren() {
        return false;
    }

    /**
     * Returns true if the receiver is a leaf.
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     */
    public Enumeration children() {
        return null;
    }

    public void unlockElement() {
        if (mParent instanceof LockedRegionsFile)
            ((LockedRegionsFile) mParent).unlockElement(this);
    }

    public void gotoElement() {
        PsiFile[] psiFiles = LockingPlugin.getInstance().getFileByName(((LockedRegionsFile) mParent).getName());
        for (PsiFile pFile : psiFiles) {

            PsiElement psiNav = pFile.findElementAt(getStartOffset());
            if (psiNav != null && psiNav instanceof Navigatable && ((Navigatable) psiNav).canNavigate())
                ((Navigatable) psiNav).navigate(true);
            else
                pFile.navigate(true);
            return;
        }

    }
}

