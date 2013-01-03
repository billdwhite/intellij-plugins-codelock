package codelockplugin.handlers;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.Document;
import codelockplugin.tree.LockedTreeManager;
import codelockplugin.LockingPlugin;

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
