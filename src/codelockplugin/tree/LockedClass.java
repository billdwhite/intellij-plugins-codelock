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
import java.util.Iterator;
import java.util.List;

/**
 * Represents a Locked class in the code.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedClass extends LockedElement {

    public LockedClass(PsiTreeNode parent) {
        super(parent);
        log = Logger.getLogger("codelockplugin.tree.LockedClass");
    }

    public LockedClass(String name, boolean isLocked, PsiTreeNode parent, PsiElement psiElement) {
        super(name, isLocked, parent, psiElement);
        log = Logger.getLogger("codelockplugin.tree.LockedClass");

    }

    public void traversePsi(PsiNamedElement psiElement, Document document) {
        assert (psiElement instanceof PsiClass);
        log.info("traversPsi(" + psiElement.getName() + " , document)");

        if (isLocked()) {
            log.debug("Locking: " + psiElement.getName());
            lockThisElement(psiElement, document);
            return;
        }

        PsiElement[] psiElementArray = psiElement.getChildren();
        for (PsiElement psiSingleElement : psiElementArray)
            if (psiSingleElement instanceof PsiClass || psiSingleElement instanceof PsiMethod) {
                PsiNamedElement psiNamedElement = (PsiNamedElement) psiSingleElement;
                if (mLockedElements.containsKey(psiNamedElement.getName()))
                    mLockedElements.get(psiNamedElement.getName()).traversePsi(psiNamedElement, document);
            }
    }

    public void lockThisElement(PsiElement psiElement, Document document) {
        log.info("lockThisElement(psiElement , document)");
        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(psiElement.getTextOffset()));
        log.debug("A:" + lineStartOffset + "  B: " + psiElement.getTextLength() + "C:" + psiElement.getTextOffset());
        //mLockedRange.add(document.createGuardedBlock(lineStartOffset, psiElement.getTextLength()));
        //
        //int lineStartOffset = document.getLineStartOffset(document.getLineNumber(psiElement.getTextOffset()));
        //log.debug("Line:" + lineStartOffset + "  Length: " + psiElement.getTextLength() + "Offset:" + psiElement.getTextOffset());
        //mLockedRange.add(document.createGuardedBlock(lineStartOffset, document.getLineEndOffset(document.getLineNumber(psiElement.get .getTextLength() +lineStartOffset))));
        mLockedRange.add(document.createGuardedBlock(psiElement.getTextRange().getStartOffset(), psiElement.getTextRange().getEndOffset()));
        //
        mDocument = document;
        document.startGuardedBlockChecking();
    }

    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        assert (element.getName().equals(CLASS_ELEMENT));
        mName = element.getAttributeValue(CLASS_NAME);
        log = Logger.getLogger("codelockplugin.codelockplugin.tree.LockedClass.[" + getName() + "]");
        mIsLocked = element.getAttributeValue(LOCK_ATT).equals(LOCKED_ELEMENT);
        List elementList = element.getChildren();
        for (Object singleElement : elementList) {
            if (singleElement instanceof Element) {
                Element childElement = (Element) singleElement;
                LockedElement lElement = createNewClassByType(childElement.getName());
                lElement.readExternal(childElement);
                mLockedElements.put(childElement.getAttributeValue(METHOD_NAME), lElement);
            }
        }


    }

    private LockedElement createNewClassByType(String type) {
        LockedElement lElement = null;
        if (type.equals(CLASS_ELEMENT))
            lElement = new LockedClass(this);
        else if (type.equals(METHOD_ELEMENT))
            lElement = new LockedFunction(this);
        else        //if we are here, its bad.  something went wrong in the hierarchy
            assert (false);
        return lElement;

    }

    /**
     * @param element
     * @throws WriteExternalException
     * @
     * @see com.intellij.openapi.util.JDOMExternalizable
     */
    public void writeExternal(Element element) throws WriteExternalException {
        log.info(" writeExternal(" + element.getName() + ")");

        Element thisElement = new Element(CLASS_ELEMENT);
        thisElement.setAttribute(CLASS_NAME, getName());

        log.debug("locked: " + isLocked());
        if (isLocked()) {
            thisElement.setAttribute(LOCK_ATT, LOCKED_ELEMENT);
        } else {
            thisElement.setAttribute(LOCK_ATT, UNLOCKED_ELEMENT);
            for (LockedElement lElement : mLockedElements.values()) {
                log.debug("lElement: " + lElement.getName());
                lElement.writeExternal(thisElement);
            }

        }
        element.addContent(thisElement);

    }

    /**
     * Codelocks this internal class, inside this class LockedElement
     *
     * @param className name of the class to lock
     */
    public void lockClass(String className, PsiElement psiElement) {
        log.info("lockClass(" + className + ")");
        if (mLockedElements.containsKey(className)) {
            LockedElement lElement = mLockedElements.get(className);
            if (lElement instanceof LockedClass) {
                //nothing to do
            } else {
                assert (false); //function by this name already exists
            }
        } else {
            LockedClass lClass = new LockedClass(className, true, this, psiElement);
            mLockedElements.put(className, lClass);
        }

    }

    /**
     * CodeLocks this function inside this class Element
     *
     * @param functionName name of the function
     */

    public void lockFunction(String functionName, PsiElement psiElement, Document document) {
        log.info("lockFunction(" + functionName + ")");
        if (mLockedElements.containsKey(functionName)) {
            LockedElement lElement = mLockedElements.get(functionName);
            if (lElement instanceof LockedFunction) {
                //((LockedFunction)lElement).lockThisElement(psiElement, document);
            } else {
                assert (false); //class by this name already exists
            }
        } else {
            LockedFunction lFunction = new LockedFunction(functionName, true, this, psiElement);
            lFunction.lockThisElement(psiElement, document);
            if (psiElement.getParent() instanceof PsiClass)
                for (PsiMethod pMethod : ((PsiClass) psiElement.getParent()).getMethods())
                    if (pMethod.getName().equals(((PsiNamedElement) psiElement).getName()) && pMethod != psiElement)
                        lFunction.lockThisElement(pMethod, document);
            mLockedElements.put(functionName, lFunction);
        }
    }

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     */
    public TreeNode getChildAt(int childIndex) {
        Iterator<LockedElement> iter = mLockedElements.values().iterator();
        int counter = 0;
        while (iter.hasNext()) {
            if (counter == childIndex)
                return iter.next();
            else {
                iter.next();
                counter++;
            }
        }

        return null;
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    public int getChildCount() {
        return mLockedElements.size();  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    public int getIndex(TreeNode node) {

        Iterator<LockedElement> iter = mLockedElements.values().iterator();
        int counter = 0;
        while (iter.hasNext()) {
            if (iter.next().equals(node))
                return counter;
            else
                counter++;
        }

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
        return mLockedElements.elements();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PsiElement loadPsi() {
        PsiElement parentPsi = mParent.loadPsi();
        PsiClass[] psiClasses = LockingPlugin.getInstance().getClassByName(getName());
        for (PsiClass pClass : psiClasses)
            if (pClass.getParent().equals(parentPsi))
                return pClass;
        return null;
    }

}
