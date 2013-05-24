package codelockplugin.handlers;

import codelockplugin.LockingPlugin;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Called when a user attempts to lock a region.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class ActionRegionLock extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        LockingPlugin.getInstance().lockCodeRegion(e);
    }
}
