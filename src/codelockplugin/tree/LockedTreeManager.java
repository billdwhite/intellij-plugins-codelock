package codelockplugin.tree;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.apache.log4j.Logger;
import org.jdom.Element;

import javax.swing.tree.TreeNode;
import java.util.*;

import codelockplugin.LockingPlugin;

/**
 * Manages and holds the entire locked element and regions tree.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedTreeManager extends LockedElement {
    private static final LockedTreeManager instance;
    private boolean mCodeElementMode;

    static {
        instance = new LockedTreeManager();
    }

    public static LockedTreeManager getInstance() {
        return instance;
    }

    private final Hashtable<String, LockedFile> mLockedFiles;
    private final Hashtable<String, LockedRegionsFile> mLockedRegions;

    private LockedTreeManager() {
        super("Locked elements", false, null, null);
        mLockedFiles = new Hashtable<String, LockedFile>();
        mLockedRegions = new Hashtable<String, LockedRegionsFile>();
        log = Logger.getLogger("codelockplugin.tree.LockedTreeManager");
        mCodeElementMode = true;

    }

    void addLockedFile(String fileName, LockedFile lockedFile) {
        mLockedFiles.put(fileName, lockedFile);
    }

    LockedFile getLockedFile(String fileName) {
        return mLockedFiles.get(fileName);
    }

    boolean containsLockedFile(String fileName) {
        return mLockedFiles.containsKey(fileName);
    }

    LockedRegionsFile getLockedRegion(String fileName) {
        return mLockedRegions.get(fileName);
    }

    boolean containsLockedRegion(String fileName) {
        return mLockedRegions.containsKey(fileName);
    }

    public void lockRegion(String fileName, int selStart, int selEnd, Document document) {
        log.info("lockRegion(" + fileName + ")");
        if (containsLockedRegion(fileName)) {
            getLockedRegion(fileName).lockRegion(selStart, selEnd, document);
        } else {
            LockedRegionsFile lRegionFile = new LockedRegionsFile(this, fileName);
            lRegionFile.lockRegion(selStart, selEnd, document);
            mLockedRegions.put(fileName, lRegionFile);
        }
    }

    public void lockClass(String fileName, String className, PsiElement psiElement, Document document) {
        log.info("lockClass(" + fileName + " , " + className + ")");
        if (containsLockedFile(fileName)) {
            getLockedFile(fileName).lockClass(className, psiElement);
        } else {//new, check this if creats bugs
            LockedFile lFile = new LockedFile(fileName, false, this, psiElement.getParent());
            LockedClass lClass = new LockedClass(className, true, lFile, psiElement);
            lClass.lockThisElement(psiElement, document);
            lFile.setLockedClass(lClass);
            addLockedFile(fileName, lFile);
        }

    }

    public void lockFunction(String fileName, String className, String functionName, PsiElement psiElement, Document document) {
        log.info("lockFunction(" + className + " , " + functionName + ")");
        if (containsLockedFile(fileName)) {
            getLockedFile(fileName).lockFunction(className, functionName, psiElement, document);
        } else {

            LockedFile lFile = new LockedFile(fileName, false, this, psiElement.getParent().getParent());
            LockedClass lClass = new LockedClass(className, false, lFile, psiElement.getParent());
            lFile.setLockedClass(lClass);
            lFile.lockFunction(className, functionName, psiElement, document);
            addLockedFile(fileName, lFile);
        }
    }


    public void traversePsi(PsiNamedElement psiElement, Document document) {
        log.info("traversPsi(" + psiElement.getName() + " , document)");
        if (!(psiElement instanceof PsiFile))
            assert (false);
        if (containsLockedFile(psiElement.getName()))
            getLockedFile(psiElement.getName()).traversePsi(psiElement, document);
        if (containsLockedRegion(psiElement.getName()))
            getLockedRegion(psiElement.getName()).traversePsi(psiElement, document);
    }

    public void lockThisElement(PsiElement psiElement, Document document) {
        assert (false);
    }

    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        Element lockedElements = element.getChild("LockedTreeManager").getChild("LockedElements");
        if (lockedElements != null) {
            List<Element> list = (List<Element>) lockedElements.getChildren();
            for (Element childElement : list) {
                log.debug("ChildElement: " + childElement.getName());
                LockedFile lFile = new LockedFile(this);
                lFile.readExternal(childElement);
                mLockedFiles.put(lFile.getName(), lFile);
            }
        }
        lockedElements = element.getChild("LockedTreeManager").getChild("LockedRegions");
        if (lockedElements != null) {
            List<Element> list = (List<Element>) lockedElements.getChildren();
            for (Element childElement : list) {
                log.debug("ChildRegion: " + childElement.getName());
                LockedRegionsFile lrFile = new LockedRegionsFile(this);
                lrFile.readExternal(childElement);
                mLockedRegions.put(lrFile.getName(), lrFile);
            }
        }


    }

    public void writeExternal(Element element) throws WriteExternalException {
        log.info("writeExternal(" + element.getName() + ")");
        Element thisElement = new Element("LockedTreeManager");
        Element lockedElements = new Element("LockedElements");

        for (LockedFile lFile : mLockedFiles.values()) {
            lFile.writeExternal(lockedElements);
        }
        thisElement.addContent(lockedElements);

        Element lockedRegions = new Element("LockedRegions");

        for (LockedRegionsFile lrFile : mLockedRegions.values()) {
            lrFile.writeExternal(lockedRegions);
        }
        thisElement.addContent(lockedRegions);

        element.addContent(thisElement);
    }

    public boolean isCodeElementMode() {
        return mCodeElementMode;
    }

    public void setCodeElementMode(boolean mCodeElementMode) {
        this.mCodeElementMode = mCodeElementMode;
    }/*
public Collection<LockedFile> getLockedFiles() {
   return mLockedFiles.values();
}
    */

    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     */
    public TreeNode getChildAt(int childIndex) {

        Iterator iter;
        if (mCodeElementMode)
            iter = mLockedFiles.values().iterator();
        else
            iter = mLockedRegions.values().iterator();
        int counter = 0;
        while (iter.hasNext()) {
            if (counter == childIndex)
                return (TreeNode) iter.next();
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
        if (mCodeElementMode)
            return mLockedFiles.size();
        else
            return mLockedRegions.size();
    }

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     */
    public TreeNode getParent() {
        return LockedTreeManager.getInstance();
    }

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     */
    public int getIndex(TreeNode node) {
        Iterator iter;
        if (mCodeElementMode)
            iter = mLockedFiles.values().iterator();
        else
            iter = mLockedRegions.values().iterator();
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
        if (mCodeElementMode)
            return mLockedFiles.elements();
        else
            return mLockedRegions.elements();
    }

    public void unlockElement(LockedElement child) {
        log.info("unlockElement(" + child.getName() + ")");
        mLockedFiles.remove(child.getName());

    }

    public void unlockElement(LockedRegionsFile child) {
        log.info("unlockElement(" + child.getName() + ")");
        mLockedRegions.remove(child.getName());


    }

    public void removeChildren() {
        mLockedFiles.clear();
    }

    public PsiElement loadPsi() {
        return null;
    }

    public String toString() {
        if (mCodeElementMode)
            return "Locked Elements";
        else
            return "Locked Regions";
    }

}
