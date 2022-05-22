package org.statistical.analysis.utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class VisualUtils {
    public static ActionListener fileChooserActionListener(JTextField textField) {
        return e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return (f.isDirectory() || f.getName().endsWith(".txt")) && !f.isHidden();
                }

                @Override
                public String getDescription() {
                    return "Plain text files (.txt)";
                }
            });

            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile() != null)
                    textField.setText(chooser.getSelectedFile().getAbsolutePath());
            }

            if (returnValue == JFileChooser.CANCEL_OPTION) {
                textField.setText(null);
            }
        };
    }

    public static void appendOnTextAreaConcurrentlyIfExists(JTextArea jTextArea, String string) {
        if (jTextArea != null) {
            jTextArea.append(string);
            jTextArea.update(jTextArea.getGraphics());
            jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
        }
    }

}
