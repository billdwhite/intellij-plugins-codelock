package codelockplugin.ui;

import codelockplugin.LockingPlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;


/**
 * Warning dialog when user attempts to edit a locked piece of code.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class CodeChangeAttemptDialog
extends JDialog {


    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane theCodeYouAttemptedTextPane;
    private JButton managerButton;



    public CodeChangeAttemptDialog() {
        super((Frame) null, "CodeLock");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        managerButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                // LockManagerUI rr = new LockManagerUI();
                //rr.pack();
                //rr.setVisible(true);
                LockingPlugin.getInstance().activateToolManager();
                onOK();
            }
        });

    }



    private void onOK() {
// add your code here
        dispose();
    }



    public static void main(String[] args) {
        CodeChangeAttemptDialog dialog = new CodeChangeAttemptDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }



    {
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setAutoscrolls(false);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1,
                        new GridConstraints(1,
                                            0,
                                            1,
                                            1,
                                            GridConstraints.ANCHOR_CENTER,
                                            GridConstraints.FILL_BOTH,
                                            1,
                                            1,
                                            null,
                                            null,
                                            null,
                                            0,
                                            false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2,
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
        buttonOK = new JButton();
        buttonOK.setSelected(true);
        buttonOK.setText("OK");
        buttonOK.setMnemonic('O');
        buttonOK.setDisplayedMnemonicIndex(0);
        panel2.add(buttonOK,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_HORIZONTAL,
                                       GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_CAN_GROW,
                                       new Dimension(150, -1),
                                       null,
                                       null,
                                       0,
                                       false));
        managerButton = new JButton();
        managerButton.setText("Manager...");
        managerButton.setMnemonic('M');
        managerButton.setDisplayedMnemonicIndex(0);
        panel2.add(managerButton,
                   new GridConstraints(0,
                                       1,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_HORIZONTAL,
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       new Dimension(90, -1),
                                       0,
                                       false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3,
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
        theCodeYouAttemptedTextPane = new JTextPane();
        theCodeYouAttemptedTextPane.setEditable(false);
        theCodeYouAttemptedTextPane.setFont(new Font(theCodeYouAttemptedTextPane.getFont().getName(),
                                                     theCodeYouAttemptedTextPane.getFont().getStyle(),
                                                     14));
        theCodeYouAttemptedTextPane.setText(
        "The code you attempted to edit is locked by the CodeLock plugin.\n\nYou must first unlock it.\n");
        panel3.add(theCodeYouAttemptedTextPane,
                   new GridConstraints(0,
                                       0,
                                       2,
                                       1,
                                       GridConstraints.ANCHOR_NORTHWEST,
                                       GridConstraints.FILL_NONE,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       new Dimension(150, 100),
                                       new Dimension(250, 100),
                                       new Dimension(250, 100),
                                       0,
                                       false));
    }



    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}