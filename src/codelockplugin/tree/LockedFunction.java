package codelockplugin.tree;

import codelockplugin.LockingPlugin;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNamedElement;
import org.apache.log4j.Logger;
import org.jdom.Element;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

/**
 * Represents a locked function in the code.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedFunction extends LockedElement {


    public LockedFunction(PsiTreeNode parent) {
        super(parent);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedFunction");
    }

    public LockedFunction(String name, boolean isLocked, PsiTreeNode parent, PsiElement psiElement) {
        super(name, isLocked, parent, psiElement);
        log = Logger.getLogger("codelockplugin.tree.LockedFunction.[" + getName() + "]");

    }

    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        assert (element.getName().equals(METHOD_ELEMENT));
        mName = element.getAttributeValue(METHOD_NAME);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedFunction.[" + getName() + "]");
        mIsLocked = element.getAttributeValue(LOCK_ATT).equals(LOCKED_ELEMENT);

    }

    public void writeExternal(Element element) throws WriteExternalException {
        log.info("writeExternal(" + element.getName() + ")");
        Element thisElement = new Element(METHOD_ELEMENT);
        thisElement.setAttribute(METHOD_NAME, getName());

        //if this is locked, everything under this element is locked
        if (isLocked()) {
            thisElement.setAttribute(LOCK_ATT, LOCKED_ELEMENT);
        } else
            assert (false); //at current code, there should be no element below function/method
        element.addContent(thisElement);
    }

    public void traversePsi(PsiNamedElement psiElement, Document document) {
        log.info("traversPsi(" + psiElement.getName() + " , document)");
        assert (psiElement instanceof PsiMethod);
        if (isLocked()) {
            log.debug("Locking: " + psiElement.getName());
            lockThisElement(psiElement, document);
        } else
            assert (false); //at current code, there should be no element below function/method


    }

    public void lockThisElement(PsiElement psiElement, Document document) {
        log.info("lockThisElement(psiElement , document)");
        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(psiElement.getTextOffset()));
        log.debug("Line:" + lineStartOffset + "  Length: " + psiElement.getTextLength() + "Offset:" + psiElement.getTextOffset());
        //mLockedRange.add(document.createGuardedBlock(lineStartOffset, document.getLineEndOffset(document.getLineNumber(psiElement.getTextLength() + lineStartOffset))));
        mLockedRange.add(document.createGuardedBlock(psiElement.getTextRange().getStartOffset(), psiElement.getTextRange().getEndOffset()));
        mDocument = document;
        document.startGuardedBlockChecking();
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
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    public int getIndex(TreeNode node) {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
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
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     */
    public Enumeration children() {
        return null;
    }

    public PsiElement loadPsi() {
        PsiElement parentPsi = mParent.loadPsi();
        PsiMethod[] psiMethods = LockingPlugin.getInstance().getMethodByName(getName());
        for (PsiMethod pMethod : psiMethods)
            if (pMethod.getParent().equals(parentPsi))
                return pMethod;
        return null;
    }

}
