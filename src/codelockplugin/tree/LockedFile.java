package codelockplugin.tree;

import codelockplugin.LockingPlugin;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.*;
import org.apache.log4j.Logger;
import org.jdom.Element;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents a File with locked elemnts in it.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedFile extends LockedElement {

    private LockedClass mLockedClass;

    //created inroder to use the readExternal to create its data
    public LockedFile(PsiTreeNode parent) {
        super(parent);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedFile");
    }

    public LockedFile(String fileName, boolean isLocked, PsiTreeNode parent, PsiElement psiElement) {
        super(fileName, isLocked, parent, psiElement);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedFile.[" + getName() + "]");

    }

    public void setLockedClass(LockedClass lockedClass) {
        mLockedClass = lockedClass;
    }


    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        if (!(element.getName().equals(FILE_ELEMENT)))
            return;
        mName = element.getAttributeValue(FILE_NAME);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedFile.[" + getName() + "]");
        mLockedClass = new LockedClass(this);
        List list = element.getChildren();
        if (list.size() != 1)
            assert (false);
        Element classElement = (Element) list.get(0);
        mLockedClass.readExternal(classElement);
    }

    public void writeExternal(Element element) throws WriteExternalException {
        log.info("writeExternal(" + element.getName() + ")");
        /* Element thisElement = new Element(getName());
        thisElement.setAttribute(TYPE_ATT, FILE_ELEMENT);
        mLockedClass.writeExternal(thisElement);
        element.addContent(thisElement);*/
        Element thisElement = new Element(FILE_ELEMENT);
        thisElement.setAttribute(FILE_NAME, getName());
        mLockedClass.writeExternal(thisElement);
        element.addContent(thisElement);
    }

    public void traversePsi(PsiNamedElement psiElement, Document document) {
        log.info("traversPsi(" + psiElement.getName() + " , document)");
        PsiElement[] psiElementArray = psiElement.getChildren();
        for (PsiElement psiSingleElement : psiElementArray)
            if (psiSingleElement instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) psiSingleElement;
                if (psiClass.getName().equals(mLockedClass.getName()))
                    mLockedClass.traversePsi(psiClass, document);
            }

    }

    public void lockClass(String className, PsiElement psiElement) {
        log.info(" lockClass(" + className + ")");
        if (!mLockedClass.isLocked())
            mLockedClass = new LockedClass(className, true, this, psiElement);


    }

    public void lockFunction(String className, String functionName, PsiElement psiElement, Document document) {
        log.info("lockFunction(" + className + " , " + functionName + ")");
        if (mLockedClass.getName().equals(className)) {
            if (!mLockedClass.isLocked()) { //if its locked, no use locking the function
                mLockedClass.lockFunction(functionName, psiElement, document);
            }

        } else
            assert (false);
    }

    public void lockThisElement(PsiElement psiElement, Document document) {
        assert (false);
    }

    public LockedClass getLockedClass() {
        return mLockedClass;
    }

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     */
    public TreeNode getChildAt(int childIndex) {
        if (childIndex == 0)
            return mLockedClass;
        else
            return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    public int getChildCount
            () {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    public int getIndex(TreeNode node) {
        if (mLockedClass.equals(node))
            return 0;
        else
            return -1;
    }

    /**
     * Returns true if the receiver allows children.
     */
    public boolean getAllowsChildren() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
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
        Hashtable<String, LockedClass> hash = new Hashtable<String, LockedClass>();
        hash.put(mLockedClass.getName(), mLockedClass);
        return hash.elements();
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public String toString() {
        return getName();
    }

    public void unlockElement(LockedElement child) {
        log.info("unlockElement: " + child.getName());
        removeGuardBlock();
        unlockChildrenElements();
        if (this.equals(child) || mLockedClass.equals(child))
            if (mParent != null && mParent instanceof LockedElement)
                ((LockedElement) mParent).unlockElement(this);

    }

    public void removeChildren() {
        mLockedClass = null;
    }

    public PsiElement loadPsi() {
        PsiFile[] psiFiles = LockingPlugin.getInstance().getFileByName(getName());
        for (PsiFile pFile : psiFiles)
            return pFile;
        return null;
    }
}
