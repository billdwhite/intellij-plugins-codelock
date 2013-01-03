package codelockplugin.tree;

import com.intellij.psi.PsiFile;
import codelockplugin.LockingPlugin;

/**
 * Abstract class that all of the Locked region elements in herit from.
 * Created by IntelliJ IDEA.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public abstract class LockedRegion implements PsiTreeNode {

    boolean mIsLocked;
    PsiTreeNode mParent;
    String mName;

    public boolean isLocked() {
        return mIsLocked;
    }


    public abstract void unlockElement();

    public void gotoElement() {
        PsiFile[] psiFiles = LockingPlugin.getInstance().getFileByName(getName());
        for (PsiFile pFile : psiFiles) {
            pFile.navigate(true);
            return;
        }
    }

    String getName() {
        return mName;
    }


}
