package org.statistical.analysis.visual;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang3.StringUtils;
import org.statistical.analysis.constants.SettingsConstants;
import org.statistical.analysis.utils.PropertiesUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class SettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox deleteTextSequencesCheckBox;
    private JTextField regexTextField;
    private JCheckBox advancedDeleteSequencesCheckbox;
    private JCheckBox debugModeCheckBox;

    public SettingsDialog() throws IOException {
        setTitle("Settings");
        setLocationRelativeTo(null);

        URL iconURL = getClass().getClassLoader().getResource("text.png");
        if (iconURL != null)
            setIconImage(new ImageIcon(iconURL).getImage());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Throwable ex) {
                JOptionPane.showMessageDialog(new JFrame(), "An error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        loadSettings();

        advancedDeleteSequencesCheckbox.addActionListener(e -> regexTextField.setEnabled(advancedDeleteSequencesCheckbox.isSelected()));


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void loadSettings() throws IOException {
        Properties settingsFile = PropertiesUtils.getSettingsFile();

        boolean deleteIgnoredSequences = Boolean.parseBoolean(settingsFile.getProperty(SettingsConstants.DELETE_IGNORED_SEQUENCES));
        boolean debugMode = Boolean.parseBoolean(settingsFile.getProperty(SettingsConstants.DEBUG_MODE));
        boolean advancedDeleteIgnoredSequences = Boolean.parseBoolean(settingsFile.getProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES));
        String advancedDeleteIgnoredSequencesREGEX = settingsFile.getProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES_REGEX);

        deleteTextSequencesCheckBox.setSelected(deleteIgnoredSequences);
        debugModeCheckBox.setSelected(debugMode);
        advancedDeleteSequencesCheckbox.setSelected(advancedDeleteIgnoredSequences);
        if (advancedDeleteIgnoredSequences)
            regexTextField.setText(advancedDeleteIgnoredSequencesREGEX);

        regexTextField.setEnabled(advancedDeleteIgnoredSequences);
    }

    private void onOK() throws IOException {
        if (advancedDeleteSequencesCheckbox.isSelected() && StringUtils.isBlank(regexTextField.getText())) {
            regexTextField.setBorder(new LineBorder(Color.RED, 2));
            JOptionPane.showMessageDialog(new JFrame(),
                    "If advanced delete sequences is checked, a regular expression is needed",
                    "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            saveSettings();
            dispose();
        }
    }

    private void saveSettings() throws IOException {
        Properties properties = new Properties();

        properties.setProperty(SettingsConstants.DELETE_IGNORED_SEQUENCES, String.valueOf(deleteTextSequencesCheckBox.isSelected()));
        properties.setProperty(SettingsConstants.DEBUG_MODE, String.valueOf(debugModeCheckBox.isSelected()));
        properties.setProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES, String.valueOf(advancedDeleteSequencesCheckbox.isSelected()));
        properties.setProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES_REGEX, regexTextField.getText());

        PropertiesUtils.saveSettingsFile(properties);
    }

    private void onCancel() {
        dispose();
    }

    public static void startSettings() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SettingsDialog dialog = new SettingsDialog();
            dialog.pack();
            dialog.setVisible(true);
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(new JFrame(), "An error occurred: " + t.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 13, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteTextSequencesCheckBox = new JCheckBox();
        deleteTextSequencesCheckBox.setText("Delete text sequences between  [ ]");
        deleteTextSequencesCheckBox.setToolTipText("If checked, any text between brackets [ ] will be erased");
        panel3.add(deleteTextSequencesCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(265, 24), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel3.add(separator1, new GridConstraints(2, 0, 1, 13, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        advancedDeleteSequencesCheckbox = new JCheckBox();
        advancedDeleteSequencesCheckbox.setText("Delete text sequences through REGEX");
        panel3.add(advancedDeleteSequencesCheckbox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(265, 24), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-8223095));
        label1.setText("Advanced settings");
        panel3.add(label1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(265, 16), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Regular expression:");
        label2.setToolTipText("Anything that matches this expression on the text will be removed");
        panel3.add(label2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(265, 16), null, 0, false));
        regexTextField = new JTextField();
        panel3.add(regexTextField, new GridConstraints(5, 1, 1, 12, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        debugModeCheckBox = new JCheckBox();
        debugModeCheckBox.setText("Debug Mode");
        debugModeCheckBox.setToolTipText("If checked, every execution will ouput a file for every text that is analyzed, with its words split the way ALTXA has split them, to check for word parsing errors");
        panel3.add(debugModeCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
