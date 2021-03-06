package codelockplugin.handlers;

import codelockplugin.tree.LockedTreeManager;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

/**
 * Called when a file or document are created.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockDocumentListener implements PsiDocumentManager.Listener {


    public void documentCreated(Document document, PsiFile psiFile) {
        //do nothing
    }

    public void fileCreated(PsiFile psiFile, Document document) {
        LockedTreeManager.getInstance().traversePsi(psiFile, document);
    }


}
