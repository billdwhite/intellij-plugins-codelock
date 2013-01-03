package codelockplugin.handlers;

import codelockplugin.LockingPlugin;
import codelockplugin.tree.LockedTreeManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

/**
 * Used when a code element lock is requested by the user.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class ActionElementLock extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        LockingPlugin.getInstance().lockCodeElement(e);

    }
}
