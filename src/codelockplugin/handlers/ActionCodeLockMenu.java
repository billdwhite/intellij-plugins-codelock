package codelockplugin.handlers;

import codelockplugin.LockingPlugin;
import codelockplugin.ui.LockManagerUI;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Used when a user opens the codelock menu.
 *z
 * @author gil
 * @version 1.0
 */
public class ActionCodeLockMenu extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        LockingPlugin.getInstance().openLockMenu();
    }
}
