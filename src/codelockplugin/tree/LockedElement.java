package codelockplugin.tree;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import org.apache.log4j.Logger;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;

/**
 * Abstract class that all Locked elements inherit from.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public abstract class LockedElement implements JDOMExternalizable, PsiTraverseable, PsiTreeNode {
    String mName;
    boolean mIsLocked;
    final PsiTreeNode mParent;
    Set<RangeMarker> mLockedRange;
    Document mDocument;
    Logger log;


    final Hashtable<String, LockedElement> mLockedElements;


    LockedElement(PsiTreeNode parent) {//for using with readExternal only.
        mLockedElements = new Hashtable<String, LockedElement>();
        mName = "DummyString";
        mIsLocked = false;
        mParent = parent;
        mLockedRange = new HashSet<RangeMarker>();
        mDocument = null;

    }

    LockedElement(String name, boolean isLocked, PsiTreeNode parent, PsiElement psiElement) {
        mLockedElements = new Hashtable<String, LockedElement>();
        mName = name;
        mIsLocked = isLocked;
        mParent = parent;
        mLockedRange = new HashSet<RangeMarker>();
        mDocument = null;
    }

    public void unlockElement() {
        unlockElement(this);
    }


    void unlockElement(LockedElement child) {
        log.info("unlockElement(" + child.getName() + ")");
        if (this.equals(child)) {
            unlockChildrenElements();
            unlockParent(child);
        } else if (mLockedElements.contains(child)) {
            mLockedElements.remove(child.getName());
            if (mLockedElements.isEmpty())
                unlockParent(this);
        }
    }

    void removeGuardBlock() {
        if (mLockedRange.size() > 0 && mDocument != null)
            for (RangeMarker rMarker : mLockedRange)
                mDocument.removeGuardedBlock(rMarker);

    }

    void unlockParent(LockedElement child) {
        if (mParent != null && mParent instanceof LockedElement)
            ((LockedElement) mParent).unlockElement(child);

    }

    void unlockChildrenElements() {
        removeGuardBlock();
        Enumeration enumer = children();
        if (enumer != null)
            while (enumer.hasMoreElements())
                ((LockedElement) enumer.nextElement()).unlockChildrenElements();
    }

    public void removeChildren() {
        mLockedElements.clear();
    }

    /**
     * checks if this Element, represents a locked Class/Method
     *
     * @return true if this Elements is locked
     */
    public boolean isLocked() {
        return mIsLocked;
    }

    /**
     * Name of Element
     *
     * @return String - name of Element
     */
    String getName() {
        return mName;
    }

    public String toString() {
        return getName();
    }


    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     */
    public TreeNode getParent() {
        return mParent;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void gotoElement() {
        PsiElement psiNav = loadPsi();
        if (psiNav != null && psiNav instanceof Navigatable && ((Navigatable) psiNav).canNavigate())
            ((Navigatable) psiNav).navigate(true);
    }


}
