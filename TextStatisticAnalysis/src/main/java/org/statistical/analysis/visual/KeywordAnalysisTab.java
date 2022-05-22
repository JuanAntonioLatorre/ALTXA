package org.statistical.analysis.visual;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.lang3.StringUtils;
import org.statistical.analysis.business.StatisticalAnalysis;
import org.statistical.analysis.pojo.KeywordAnalysis;
import org.statistical.analysis.pojo.Settings;
import org.statistical.analysis.utils.IOUtils;
import org.statistical.analysis.utils.PropertiesUtils;
import org.statistical.analysis.utils.VisualUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KeywordAnalysisTab {
    private JPanel KeywordAnalysisTab;

    private JTextField textTextField;
    private JTextField keywordFileTextField;

    private JButton textButton;
    private JButton keywordFileButton;

    private JButton executeButton;
    private JCheckBox outputCheckBox;

    private JScrollPane scrollPane;
    private JTextArea outputTextArea;

    public KeywordAnalysisTab() {
        textButton.addActionListener(VisualUtils.fileChooserActionListener(textTextField));
        keywordFileButton.addActionListener(VisualUtils.fileChooserActionListener(keywordFileTextField));
        executeButton.addActionListener(executeAnalysis());
    }

    private ActionListener executeAnalysis() {
        return e -> {
            if (StringUtils.isNotBlank(textTextField.getText()))
                try {
                    outputTextArea.setText(null);
                    executeKeywordAnalysis(textTextField.getText(), keywordFileTextField.getText(), outputTextArea);
                } catch (Throwable t) {
                    outputTextArea.append("\nERROR: an error occurred, could not complete analysis");
                    JOptionPane.showMessageDialog(new JFrame(), "AN ERROR OCCURRED: \n" + t.getMessage(),
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            else
                JOptionPane.showMessageDialog(new JFrame(), "Select files before executing",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
        };
    }

    private void executeKeywordAnalysis(String textFilePath, String keywordFilePath, JTextArea outputTextArea) throws Exception {
        List<String> keywords = StringUtils.isNotBlank(keywordFilePath) ? IOUtils.getKeywordList(keywordFilePath) : new ArrayList<>();
        String rawTextA = IOUtils.readTextFile(new File(textFilePath));

        Settings settings = new Settings(PropertiesUtils.getSettingsFile());

        KeywordAnalysis keywordAnalysisA = StatisticalAnalysis.getKeywordAnalysis(rawTextA, keywords, outputTextArea, settings);

        if (outputCheckBox.isSelected())
            IOUtils.writeOutAnalysis(keywordAnalysisA);

        outputTextArea.append(IOUtils.outputKeywordAnalysis(keywordAnalysisA));

        outputTextArea.update(outputTextArea.getGraphics());
        outputTextArea.setCaretPosition(outputTextArea.getText().length() - 1);
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
        KeywordAnalysisTab = new JPanel();
        KeywordAnalysisTab.setLayout(new GridLayoutManager(4, 3, new Insets(5, 5, 5, 5), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Text file");
        label1.setToolTipText("");
        KeywordAnalysisTab.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textTextField = new JTextField();
        textTextField.setBackground(new Color(-787713));
        textTextField.setEditable(false);
        KeywordAnalysisTab.add(textTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textButton = new JButton();
        textButton.setBackground(new Color(-10307442));
        textButton.setText("Choose file");
        KeywordAnalysisTab.add(textButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeButton = new JButton();
        executeButton.setBackground(new Color(-11760710));
        executeButton.setText("Execute keyword analysis");
        KeywordAnalysisTab.add(executeButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputCheckBox = new JCheckBox();
        outputCheckBox.setText("Output results to file");
        KeywordAnalysisTab.add(outputCheckBox, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        KeywordAnalysisTab.add(scrollPane, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        scrollPane.setViewportView(outputTextArea);
        final JLabel label2 = new JLabel();
        label2.setText("Keyword file (optional)");
        KeywordAnalysisTab.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keywordFileTextField = new JTextField();
        keywordFileTextField.setBackground(new Color(-787713));
        keywordFileTextField.setEditable(false);
        KeywordAnalysisTab.add(keywordFileTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        keywordFileButton = new JButton();
        keywordFileButton.setBackground(new Color(-10307442));
        keywordFileButton.setText("Choose file");
        KeywordAnalysisTab.add(keywordFileButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1.setLabelFor(textTextField);
        label2.setLabelFor(keywordFileTextField);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return KeywordAnalysisTab;
    }

}
