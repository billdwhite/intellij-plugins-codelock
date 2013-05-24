package codelockplugin.handlers;

import codelockplugin.LockingPlugin;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

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
