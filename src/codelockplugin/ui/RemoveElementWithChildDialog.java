package codelockplugin.ui;

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


/**
 * Warning dialog when user attempts to delete an element with childs.
 *
 * @author Gil Tzadikevitch
 * @version 1.0
 */
public class RemoveElementWithChildDialog
extends JDialog {


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane theElementYouAreTextPane;
    private boolean answer;



    public RemoveElementWithChildDialog() {
        super((Frame) null, "Remove children?");
        setContentPane(contentPane);
        setModal(true);
        answer = false;
        getRootPane().setDefaultButton(buttonCancel);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    private void onOK() {
        answer = true;
        dispose();
    }



    private void onCancel() {
// add your code here if necessary
        dispose();
    }



    public boolean isOK() {
        return answer;
    }



    public static void main(String[] args) {
        RemoveElementWithChildDialog dialog = new RemoveElementWithChildDialog();
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
        contentPane.setMinimumSize(new Dimension(300, 130));
        contentPane.setPreferredSize(new Dimension(300, 130));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1,
                        new GridConstraints(1,
                                            0,
                                            1,
                                            1,
                                            GridConstraints.ANCHOR_CENTER,
                                            GridConstraints.FILL_BOTH,
                                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                            1,
                                            null,
                                            null,
                                            null,
                                            0,
                                            false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
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
        buttonOK.setFont(UIManager.getFont("Button.font"));
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
                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                       GridConstraints.SIZEPOLICY_FIXED,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        buttonCancel.setMnemonic('C');
        buttonCancel.setDisplayedMnemonicIndex(0);
        panel2.add(buttonCancel,
                   new GridConstraints(0,
                                       2,
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
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1,
                   new GridConstraints(0,
                                       1,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_HORIZONTAL,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       1,
                                       null,
                                       null,
                                       null,
                                       0,
                                       false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
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
        theElementYouAreTextPane = new JTextPane();
        theElementYouAreTextPane.setEditable(false);
        theElementYouAreTextPane.setEnabled(true);
        theElementYouAreTextPane.setFont(UIManager.getFont("Button.font"));
        theElementYouAreTextPane.setText(
        "The Element you are removing has children\n\nAre you sure you want to remove it?");
        panel3.add(theElementYouAreTextPane,
                   new GridConstraints(0,
                                       0,
                                       1,
                                       1,
                                       GridConstraints.ANCHOR_CENTER,
                                       GridConstraints.FILL_BOTH,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       GridConstraints.SIZEPOLICY_WANT_GROW,
                                       new Dimension(250, 80),
                                       new Dimension(270, 80),
                                       null,
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
