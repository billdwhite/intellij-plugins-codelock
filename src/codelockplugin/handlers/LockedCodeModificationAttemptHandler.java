package codelockplugin.handlers;

import codelockplugin.ui.CodeChangeAttemptDialog;
import codelockplugin.LockingPlugin;
import com.intellij.openapi.editor.ReadOnlyFragmentModificationException;
import com.intellij.openapi.editor.actionSystem.ReadonlyFragmentModificationHandler;

import java.awt.*;

/**
 * Called when the user attempts to edit a piece of code that is current locked
 * by the codelock plugin.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockedCodeModificationAttemptHandler implements ReadonlyFragmentModificationHandler {
    public void handle(ReadOnlyFragmentModificationException e) {
        CodeChangeAttemptDialog dialog = new CodeChangeAttemptDialog();
        dialog.pack();
        dialog.setLocation(calculateDialogLocation(java.awt.MouseInfo.getPointerInfo()));

        dialog.setVisible(true);

    }

    private Point calculateDialogLocation(PointerInfo pInfo) {
        int x, y;
        int screenWidth = (int) pInfo.getDevice().getDefaultConfiguration().getBounds().getWidth();
        int screenHeight = (int) pInfo.getDevice().getDefaultConfiguration().getBounds().getHeight();
        if (pInfo.getLocation().getX() < 200)
            x = 200;
        else if (pInfo.getLocation().getX() > screenWidth - 200)
            x = screenWidth - 400;
        else
            x = (int) pInfo.getLocation().getX() - 100;

        if (pInfo.getLocation().getY() < 150)
            y = 150;
        else if (pInfo.getLocation().getY() > screenHeight - 150)
            y = screenHeight - 250;
        else
            y = (int) pInfo.getLocation().getY() - 60;

        return new Point(x, y);


    }

}
