package codelockplugin;

import codelockplugin.handlers.LockDocumentListener;
import codelockplugin.handlers.LockedCodeModificationAttemptHandler;
import codelockplugin.tree.LockedTreeManager;
import codelockplugin.ui.LockManagerUI;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.*;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.ui.content.Content;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;


/**
 * Main plugin component.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockingPlugin implements ProjectComponent, JDOMExternalizable {
    private final Project project;
    private static LockingPlugin instance;
    private final Logger log;
    private static final String LOCK_WINDOW_ID = "CodeLock";
    private ToolWindow mToolWindow;
    private boolean mIsExternalFileMode = false;
    private LockManagerUI mLockManagerUI;

    public LockingPlugin(Project project) {
        this.project = project;
        instance = this;
        java.net.URL imgURL = getClass().getResource("/codelockplugin/LoggerConfigFile.Logconfig");
        if (imgURL != null) {
            //   PropertyConfigurator.configure(imgURL);


        } else {
            // System.out.println("Logger config not found");
        }

        log = Logger.getLogger("codelockplugin.LockingPlugin");
        //Logger.getLogger("codelockplugin").setLevel(Level.ALL);
        //BasicConfigurator.configure();
      /*
        RollingFileAppender rpa = null;
        try {
            rpa = new RollingFileAppender(new PatternLayout("%-4r [%t] %-5p %c %x - %m%n"), "c:\\gg.txt");
            BasicConfigurator.configure(rpa);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        */


    }


    public static LockingPlugin getInstance() {
        return instance;

    }


    public void initComponent() {
        log.info("initComponent");
        EditorActionManager.getInstance().setReadonlyFragmentModificationHandler(new LockedCodeModificationAttemptHandler());
        if (project != null) {
            PsiDocumentManager.getInstance(project).addListener(new LockDocumentListener());
        } else
            log.error("Project not ready");
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "codelockplugin.LockingPlugin";
    }

    public void projectOpened() {
        log.info("projectOpened");
        createToolWindow();
        final String configFileName = "CodeLockPlugin.Pconfig";
        VirtualFile configFile = project.getBaseDir().findChild(configFileName);
        if (configFile != null) {
            mIsExternalFileMode = true;
        } else {
            try {
                mIsExternalFileMode = false;
                //project.getBaseDir().createChildData(null, configFileName);
            } catch (Exception e) {
            }
        }


    }

    public void projectClosed() {
        log.info("projectClosed");
        destroyToolWindow();
        // called when project is being closed
    }

    private void createToolWindow() {
        ToolWindowManager toolWindowManager;
        toolWindowManager = ToolWindowManager.getInstance(project);

        toolWindowManager.registerToolWindow(LOCK_WINDOW_ID, false, ToolWindowAnchor.RIGHT);


        mLockManagerUI = new LockManagerUI();
        mLockManagerUI.loadpane();


        mToolWindow = toolWindowManager.getToolWindow(LOCK_WINDOW_ID);
        Content content = mToolWindow.getContentManager().getFactory().createContent(mLockManagerUI.getRootPane(), "", false);
        mToolWindow.getContentManager().addContent(content);
        mToolWindow.activate(null);
    }

    public void activateToolManager() {
        mToolWindow.activate(null);
    }

    private void destroyToolWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow(LOCK_WINDOW_ID);
        toolWindow.getContentManager().removeAllContents(true);
        toolWindowManager.unregisterToolWindow(LOCK_WINDOW_ID);
    }


    public void lockCodeElement(AnActionEvent e) {
        try {
            PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
            PsiClass g;
            if (psiElement instanceof PsiClass) {
                PsiClass pClass = (PsiClass) psiElement;
                if (e.getData(LangDataKeys.PSI_FILE).equals(pClass.getParent())) {
                    Document document = getPsiDocumentManager().getDocument(e.getData(LangDataKeys.PSI_FILE));
                    LockedTreeManager.getInstance().lockClass(e.getData(LangDataKeys.PSI_FILE).getName(), pClass.getName(), psiElement, document);
                }
            } else if (psiElement instanceof PsiMethod) {
                PsiMethod pMethod = (PsiMethod) psiElement;
                Document document = getPsiDocumentManager().getDocument(e.getData(LangDataKeys.PSI_FILE));
                LockedTreeManager.getInstance().lockFunction(e.getData(LangDataKeys.PSI_FILE).getName(), ((PsiClass) pMethod.getParent()).getName(), pMethod.getName(), psiElement, document);
            }
            mLockManagerUI.updateTreeVisual();
        } catch (Error error) {
            // catch errors
            log.info("exception " + error);
        }

    }

    public void lockCodeRegion(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        SelectionModel sModel = editor.getSelectionModel();
        Document document = getPsiDocumentManager().getDocument(e.getData(LangDataKeys.PSI_FILE));
        LockedTreeManager.getInstance().lockRegion(e.getData(LangDataKeys.PSI_FILE).getName(),
            sModel.getSelectionStart(), sModel.getSelectionEnd(),
            document);
        mLockManagerUI.updateTreeVisual();

    }

    public void openLockMenu() {
        mLockManagerUI.updateTreeVisual();
        activateToolManager();
    }

    public void readExternal(Element element) throws InvalidDataException {
        log.info("readExternal(" + element.getName() + ")");
        codelockplugin.tree.LockedTreeManager.getInstance().readExternal(element);
    }

    public void writeExternal(Element element) throws WriteExternalException {
        log.info("writeExternal(" + element.getName() + ")");
        codelockplugin.tree.LockedTreeManager.getInstance().writeExternal(element);
    }

    PsiDocumentManager getPsiDocumentManager() {
        return PsiDocumentManager.getInstance(project);

    }

    public Editor[] getEditor(Document document) {
        return EditorFactory.getInstance().getEditors(document, project);
    }

    PsiShortNamesCache getPsiShortNamesCache() {
        //return PsiManager.getInstance(project).getShortNamesCache();
        return PsiShortNamesCache.getInstance(project);
    }

    public PsiClass[] getClassByName(String name) {
        //return getPsiShortNamesCache().getClassesByName(name, project.getProjectScope());
        return getPsiShortNamesCache().getClassesByName(name, ProjectScope.getProjectScope(project));
    }

    public PsiFile[] getFileByName(String name) {
        return getPsiShortNamesCache().getFilesByName(name);
    }

    public PsiMethod[] getMethodByName(String name) {
        return getPsiShortNamesCache().getMethodsByName(name, ProjectScope.getProjectScope(project));
    }
}

