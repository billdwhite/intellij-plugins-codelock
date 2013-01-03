package codelockplugin.tree;

import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiClass;

import java.util.HashSet;
import java.util.Enumeration;
import java.util.List;

import org.jdom.Element;
import org.apache.log4j.Logger;

import javax.swing.tree.TreeNode;

/**
 * represents a a file with locked regions.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedRegionsFile extends LockedRegion implements JDOMExternalizable, PsiTraverseable, PsiTreeNode {

    private final HashSet<LockedRangeMarker> mLockedRegions;
    private String mName;
    private Logger log;


    public LockedRegionsFile(PsiTreeNode parent) {
        mLockedRegions = new HashSet<LockedRangeMarker>();
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedRegionsFile");
        mParent = parent;
        mIsLocked = false;
    }

    public LockedRegionsFile(PsiTreeNode parent, String fileName) {
        this(parent);
        mName = fileName;
    }

    public void lockRegion(int selstart, int selEnd, Document document) {
        LockedRangeMarker lRangeMarker = new LockedRangeMarker(selstart, selEnd, this);
        mLockedRegions.add(lRangeMarker);
        RangeMarker marker = document.createGuardedBlock(selstart, selEnd);
        document.startGuardedBlockChecking();
        lRangeMarker.setRangeMarker(marker);
    }

    void unlockRegion(LockedRangeMarker rMarker) {
        mLockedRegions.remove(rMarker);
        if (rMarker.getRangeMarker() != null)
            rMarker.getRangeMarker().getDocument().removeGuardedBlock(rMarker.getRangeMarker());
    }


    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        if (!(element.getName().equals(FILE_ELEMENT)))
            return;
        mName = element.getAttributeValue(FILE_NAME);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedRegionsFile");
        List<Element> list = element.getChildren();
        for (Element singleRange : list) {
            int startOffset = Integer.valueOf(singleRange.getAttributeValue(START_OFFSET));
            int endOffset = Integer.valueOf(singleRange.getAttributeValue(END_OFFSET));
            log.debug("read - start: " + startOffset + "  end: " + endOffset);
            mLockedRegions.add(new LockedRangeMarker(startOffset, endOffset, this));

        }
    }

    public void writeExternal(Element element) throws WriteExternalException {
        log.info("writeExternal(" + element.getName() + ")");
        Element thisElement = new Element(FILE_ELEMENT);
        thisElement.setAttribute(FILE_NAME, getName());
        for (LockedRangeMarker rMarker : mLockedRegions) {
            Element rElement = new Element(REGION_ELEMENT);
            rElement.setAttribute(REGION_NAME, rMarker.toString());
            if (rMarker.getRangeMarker() != null) {
                rMarker.setStartOffset(rMarker.getRangeMarker().getStartOffset());
                rMarker.setEndOffset(rMarker.getRangeMarker().getEndOffset());
            }
            rElement.setAttribute(START_OFFSET, String.valueOf(rMarker.getStartOffset()));
            rElement.setAttribute(END_OFFSET, String.valueOf(rMarker.getEndOffset()));
            log.debug("write - start: " + rMarker.getStartOffset() + "  end: " + rMarker.getEndOffset());
            thisElement.addContent(rElement);

        }
        element.addContent(thisElement);
    }

    /**
     * Used to check and lock elements in the psi codelockplugin.tree, by traversing through
     * the LockedTreeManager codelockplugin.tree of LockedElements
     *
     * @param psiElement - The PsiElement that represents this LockedElement
     * @param document   - the PsiElement''s document
     */
    public void traversePsi(PsiNamedElement psiElement, Document document) {
        log.info("traversPsi(" + psiElement.getName() + " , document)");
        if (psiElement.getName().equals(getName()))
            for (LockedRangeMarker lMarker : mLockedRegions) {
                RangeMarker rMarker = document.createGuardedBlock(lMarker.getStartOffset(), lMarker.getEndOffset());
                log.debug("start: " + lMarker.getStartOffset() + "  end: " + lMarker.getEndOffset());
                lMarker.setRangeMarker(rMarker);
                document.startGuardedBlockChecking();
            }

    }

    /**
     * Codelocks this element in the intellij editor
     *
     * @param psiElement The PsiElement to lock
     * @param document   the PsiElement''s document
     */
    public void lockThisElement(PsiElement psiElement, Document document) {
        assert (false);
    }

    public PsiElement loadPsi() {
        return null;
    }

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     */
    public TreeNode getChildAt(int childIndex) {
        if (mLockedRegions.size() > childIndex)
            return (TreeNode) mLockedRegions.toArray()[childIndex];
        else
            return null;
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    public int getChildCount() {
        return mLockedRegions.size();
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
        int count = 0;
        for (int i = 0; i < mLockedRegions.size(); i++)
            if (mLockedRegions.toArray()[i].equals(node))
                count = i;
        return count;
    }

    /**
     * Returns true if the receiver allows children.
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * Returns true if the receiver is a leaf.
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     */
    public Enumeration children() {
        return null;
    }

    public String getName() {
        return mName;
    }

    public String toString() {
        return mName;
    }

    public void unlockElement(LockedRangeMarker child) {
        unlockRegion(child);
        if (getChildCount() == 0 && mParent instanceof LockedTreeManager)
            ((LockedTreeManager) mParent).unlockElement(this);

    }

    public void unlockElement() {
        for (LockedRangeMarker lRangeMarker : mLockedRegions)
            unlockRegion(lRangeMarker);
        if (mParent instanceof LockedTreeManager)
            ((LockedTreeManager) mParent).unlockElement(this);

    }
}
