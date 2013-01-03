package codelockplugin.tree;

import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

/**
 * All lockable classes inherit from this class.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public interface PsiTraverseable {
    /**
     * Used to check and lock elements in the psi codelockplugin.tree, by traversing through
     * the LockedTreeManager codelockplugin.tree of LockedElements
     *
     * @param psiElement - The PsiElement that represents this LockedElement
     * @param document   - the PsiElement''s document
     */
    public void traversePsi(PsiNamedElement psiElement, Document document);

    /**
     * Codelocks this element in the intellij editor
     *
     * @param psiElement The PsiElement to lock
     * @param document   the PsiElement''s document
     */
    public void lockThisElement(PsiElement psiElement, Document document);

    public static String TYPE_ATT = "Type_Attribute";
    public static String FILE_ELEMENT = "file";
    public static String FILE_NAME = "name";
    public static String CLASS_NAME = "name";
    public static String METHOD_NAME = "name";
    public static String REGION_NAME = "name";
    public static String CLASS_ELEMENT = "class";
    public static String METHOD_ELEMENT = "method";
    public static String REGION_ELEMENT = "region";

    public static String LOCK_ATT = "locked";
    public static String LOCKED_ELEMENT = "true";
    public static String UNLOCKED_ELEMENT = "false";

    public static String START_OFFSET = "startOffset";
    public static String END_OFFSET = "endOffset";


}
