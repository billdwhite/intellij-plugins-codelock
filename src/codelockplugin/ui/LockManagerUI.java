package codelockplugin.ui;

import codelockplugin.tree.*;
import com.intellij.ui.LayeredIcon;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.Icons;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Hashtable;

/**
 * The menu that manages the locked regions and elements.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class LockManagerUI
extends JDialog {


    private JPanel panel1;
    private JTree tree1;
    //   private JButton closeButton;
    private JButton removeButton;
    private JButton goToButton;
    private JRadioButton regionsRadioButton;
    private JRadioButton codeElementsRadioButton;
    private Hashtable<iconType, Icon> iconsHash; {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }



    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        panel1.setPreferredSize(new Dimension(450, 300));
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setDividerSize(10);
        splitPane1.setEnabled(false);
        panel1.add(splitPane1,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_BOTH,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       null,
                                       new Dimension(200, 200),
                                       null,
                                       0,
                                       false));
        final JSplitPane splitPane2 = new JSplitPane();
        splitPane2.setDividerLocation(86);
        splitPane2.setDividerSize(10);
        splitPane2.setDoubleBuffered(true);
        splitPane2.setEnabled(false);
        splitPane2.setOpaque(true);
        splitPane2.setOrientation(0);
        splitPane1.setLeftComponent(splitPane2);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane2.setRightComponent(panel2);
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1,
                   new GridConstraints(1,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_VERTICAL,
                                       1,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       new Dimension(-1, 50),
                                       null,
                                       new Dimension(-1, 50),
                                       0,
                                       false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2,
                   new GridConstraints(2,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_VERTICAL,
                                       1,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_BOTH,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        removeButton = new JButton();
        removeButton.setFont(UIManager.getFont("Button.font"));
        removeButton.setText("Remove <<");
        panel3.add(removeButton,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_HORIZONTAL,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        goToButton = new JButton();
        goToButton.setFont(UIManager.getFont("Button.font"));
        goToButton.setText("GoTo...");
        goToButton.setMnemonic('G');
        goToButton.setDisplayedMnemonicIndex(0);
        panel3.add(goToButton,
                   new GridConstraints(1,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_HORIZONTAL,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane2.setLeftComponent(panel4);
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null));
        codeElementsRadioButton = new JRadioButton();
        codeElementsRadioButton.setFont(UIManager.getFont("Button.font"));
        codeElementsRadioButton.setSelected(true);
        codeElementsRadioButton.setText("Elements");
        panel4.add(codeElementsRadioButton,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_WEST,
                                       GridConstraints.FILL_NONE,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        regionsRadioButton = new JRadioButton();
        regionsRadioButton.setFont(UIManager.getFont("Button.font"));
        regionsRadioButton.setText("Regions");
        panel4.add(regionsRadioButton,
                   new GridConstraints(1,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_WEST,
                                       GridConstraints.FILL_NONE,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        final JScrollPane scrollPane1 = new JScrollPane();
        splitPane1.setRightComponent(scrollPane1);
        tree1 = new JTree();
        tree1.setMaximumSize(new Dimension(410, 64));
        tree1.setMinimumSize(new Dimension(250, 0));
        tree1.setPreferredSize(new Dimension(300, 64));
        scrollPane1.setViewportView(tree1);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(codeElementsRadioButton);
        buttonGroup.add(regionsRadioButton);
    }



    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }



    private enum iconType {
        eLockedClass,
        eUnlockedClass,
        eLockedMethod,
        eUnlockedMethod
    }



    private static final Logger log;



    static {
        log = Logger.getLogger("codelockplugin.ui.LockManagerUI");
    }



    public LockManagerUI() {
        super((Frame) null, "CodeLock manager");
        //private JPanel contentPane;
        loadIcons();
        //setContentPane(panel1);
        setModal(true);

        removeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                removeButtonPressed();

            }
        });
        goToButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                gotoButtonPressed();
            }
        });

        tree1.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             */
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);    //To change body of overridden methods use File | Settings | File Templates.
                log.debug("Click count: " + e.getClickCount());
                if (e.getClickCount() > 1) {
                    gotoButtonPressed();
                }


            }
        });
        codeElementsRadioButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                selectTree();
            }
        });
        regionsRadioButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                selectTree();
            }
        });
    }



    public void loadpane() {
        setContentPane(panel1);
        LockedCodeTreeCellRenderer renderer = new LockedCodeTreeCellRenderer();
        tree1.setCellRenderer(renderer);
        switchToElementsTree();
        tree1.setEditable(false);
        tree1.setExpandsSelectedPaths(true);
        tree1.setRootVisible(true);
    }



    private void selectTree() {
        if (codeElementsRadioButton.isSelected()) {
            switchToElementsTree();
        }
        else {
            switchToRegionsTree();
        }
    }



    void switchToElementsTree() {
        LockedTreeManager.getInstance().setCodeElementMode(true);
        recreateTree();
        updateTreeVisual();

    }



    void switchToRegionsTree() {
        LockedTreeManager.getInstance().setCodeElementMode(false);
        recreateTree();
        tree1.expandPath(tree1.getPathForRow(0));
        //   tree1.expandRow(1);
        updateTreeVisual();

    }



    public void updateTreeVisual() {
        tree1.treeDidChange();
        tree1.updateUI();

    }



    void removeButtonPressed() {
        if (tree1.getSelectionPath() == null) {
            return;
        }

        Object[] path = tree1.getSelectionPath().getPath();
        if (path.length > 0) {
            if (path[path.length - 1] instanceof LockedElement) {
                if (((LockedElement) path[path.length - 1]).isLocked() || askRemoveChildrenDialog()) {
                    ((LockedElement) path[path.length - 1]).unlockElement();
                }
                //recreateTree();
                tree1.updateUI();
            }
            else if (path[path.length - 1] instanceof LockedRegion) {
                if (((LockedRegion) path[path.length - 1]).isLocked() || askRemoveChildrenDialog()) {
                    ((LockedRegion) path[path.length - 1]).unlockElement();
                }
                //recreateTree();
                tree1.updateUI();
            }
        }

    }



    void gotoButtonPressed() {
        if (tree1.getSelectionPath() == null) {
            return;
        }
        Object[] path = tree1.getSelectionPath().getPath();
        if (path.length > 0) {
            if (path[path.length - 1] instanceof LockedElement) {
                ((LockedElement) path[path.length - 1]).gotoElement();
            }
            if (path[path.length - 1] instanceof LockedRegion) {
                ((LockedRegion) path[path.length - 1]).gotoElement();
            }
        }

    }



    void loadIcons() {
        iconsHash = new Hashtable<iconType, Icon>();
        LayeredIcon lockedClass = new LayeredIcon(2);
        lockedClass.setIcon(Icons.LOCKED_ICON, 1);
        lockedClass.setIcon(Icons.CLASS_ICON, 0);
        LayeredIcon lockedMethod = new LayeredIcon(2);
        lockedMethod.setIcon(Icons.LOCKED_ICON, 1);
        lockedMethod.setIcon(Icons.METHOD_ICON, 0);
        LayeredIcon unlockedClass = new LayeredIcon(2);
        unlockedClass.setIcon(Icons.PUBLIC_ICON, 1);
        unlockedClass.setIcon(Icons.CLASS_ICON, 0);
        LayeredIcon unlockedMethod = new LayeredIcon(2);
        unlockedMethod.setIcon(Icons.PUBLIC_ICON, 1);
        unlockedMethod.setIcon(Icons.METHOD_ICON, 0);
        iconsHash.put(iconType.eLockedClass, lockedClass);
        iconsHash.put(iconType.eUnlockedClass, unlockedClass);
        iconsHash.put(iconType.eLockedMethod, lockedMethod);
        iconsHash.put(iconType.eUnlockedMethod, unlockedMethod);

        /*iconsHash.put(iconType.eLockedClass, createImageIcon("/codelockplugin/ui/images/lockedClass.jpg"));
       iconsHash.put(iconType.eUnlockedClass, createImageIcon("/codelockplugin/ui/images/unlockedClass.jpg"));
       iconsHash.put(iconType.eLockedMethod, createImageIcon("/codelockplugin/ui/images/lockedMethod.jpg"));
       iconsHash.put(iconType.eUnlockedMethod, createImageIcon("/codelockplugin/ui/images/unlockedMethod.jpg"));*/
    }



    boolean askRemoveChildrenDialog() {
        RemoveElementWithChildDialog dialog = new RemoveElementWithChildDialog();
        dialog.pack();
        dialog.setLocation(calculateDialogLocation(MouseInfo.getPointerInfo()));
        dialog.setVisible(true);

        return dialog.isOK();
    }



    private Point calculateDialogLocation(PointerInfo pInfo) {
        int x, y;
        int screenWidth = (int) pInfo.getDevice().getDefaultConfiguration().getBounds().getWidth();
        int screenHeight = (int) pInfo.getDevice().getDefaultConfiguration().getBounds().getHeight();
        if (pInfo.getLocation().getX() < 200) {
            x = 200;
        }
        else if (pInfo.getLocation().getX() > screenWidth - 200) {
            x = screenWidth - 400;
        }
        else {
            x = (int) pInfo.getLocation().getX() - 100;
        }

        if (pInfo.getLocation().getY() < 150) {
            y = 150;
        }
        else if (pInfo.getLocation().getY() > screenHeight - 150) {
            y = screenHeight - 250;
        }
        else {
            y = (int) pInfo.getLocation().getY() - 60;
        }

        return new Point(x, y);


    }



    void recreateTree() {
        TreeModel t = new DefaultTreeModel(LockedTreeManager.getInstance());
        tree1.setModel(t);

    }



    ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        else {
            log.error("Couldn't find file: " + path);
            return null;
        }
    }



    public static void main(String[] args) {
        LockManagerUI dialog = new LockManagerUI();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }



    private class LockedCodeTreeCellRenderer
    extends DefaultTreeCellRenderer {


        public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

            super.getTreeCellRendererComponent(
            tree, value, sel,
            expanded, leaf, row,
            hasFocus);
            if (value instanceof LockedClass) {
                if (((LockedElement) value).isLocked()) {
                    setForeground(Color.RED);
                    setIcon(iconsHash.get(iconType.eLockedClass));

                }
                else {
                    setIcon(iconsHash.get(iconType.eUnlockedClass));
                }
            }
            else if (value instanceof LockedFunction) {
                if (((LockedElement) value).isLocked()) {

                    setForeground(Color.RED);
                    setIcon(iconsHash.get(iconType.eLockedMethod));
                }
                else {
                    setIcon(iconsHash.get(iconType.eUnlockedMethod));
                }
            }
            return this;
        }


    }

}




