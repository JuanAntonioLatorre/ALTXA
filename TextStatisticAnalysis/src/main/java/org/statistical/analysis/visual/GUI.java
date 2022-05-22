package org.statistical.analysis.visual;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class GUI extends JFrame {
    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JPanel keywordTab;
    private JPanel nGramTab;
    private JPanel zTestTab;
    private KeywordAnalysisTab keywordTabContent;
    private NGramTab nGramTabContent;
    private ZTestTab zTestTabContent;
    private JMenuBar MenuBar;
    private JMenu helpMenu;
    private JMenu fileMenu;
    private JMenuItem aboutMenuItem;
    private JMenuItem settingsMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem donateMenuItem;
    private final String aboutMessage = "Querido usuario:\n" +
            "Gracias por utilizar ALTXA. Somos Juan Antonio Latorre, doctor en lingüística\n" +
            "forense por la Universidad Complutense de Madrid, y Carlos Antón, programador\n" +
            "informático. Hemos desarrollado este software para facilitar la implementación de los\n" +
            "estudios de atribución de autoría dentro del marco disciplinario de la lingüística\n" +
            "forense tanto en ambientes profesionales como docentes.\n" +
            "Puedes acceder a videotutoriales sobre cómo sacarle el máximo partido a cada una de\n" +
            "las funcionalidades de ALTXA en nuestro canal de YouTube Project ALTXA.\n" +
            "Para cualquier pregunta o sugerencia, no dudes en contactarnos a través de nuestra\n" +
            "dirección de correo infoaltxa@gmail.com y síguenos en nuestra cuenta de Twitter\n" +
            "@projectaltxa para estar al tanto de futuras actualizaciones del programa.\n" +
            "Bienvenido al Proyecto ALTXA. Atentamente,\n" +
            "Juan y Carlos.\n" +
            "\n" +
            "Dear user:\n" +
            "Thank you for using ALTXA. We are Juan Antonio Latorre, doctor in forensic\n" +
            "linguistics at the Universidad Complutense de Madrid, and Carlos Antón, computer\n" +
            "programmer. We have developed this software to facilitate the implementation of\n" +
            "authorship attribution studies within the disciplinary field of forensic linguistics in\n" +
            "professional and academic contexts.\n" +
            "You can access video tutorials on how to take advantage from every functionality of\n" +
            "ALTXA in our YouTube channel Project ALTXA.\n" +
            "Should you have any questions or comments, do not hesitate to contact us by e-mail\n" +
            "(infoaltxa@gmail.com) and follow us on our Twitter account @projectaltxa\n" +
            "to keep track of future updates of the software.\n" +
            "Welcome to Project ALTXA. Kind regards,\n" +
            "Juan and Carlos.\n";

    private final String donateMessage = "";

    public GUI() {
        aboutMenuItem = new JMenuItem("About");
        //  donateMenuItem = new JMenuItem("Donate");
        helpMenu.add(aboutMenuItem);
        //    helpMenu.add(donateMenuItem);

        settingsMenuItem = new JMenuItem("Settings");
        closeMenuItem = new JMenuItem("Close");

        fileMenu.add(settingsMenuItem);
        fileMenu.add(closeMenuItem);

        aboutMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(new JFrame(), aboutMessage,
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });

        settingsMenuItem.addActionListener(e -> {
            SettingsDialog.startSettings();
        });

        closeMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
/*

        JHyperlink paypalLink = new JHyperlink("Donate through Paypal");
        paypalLink.setURL("http://www.google.es");

        JFrame frame = new JFrame();
        frame.getContentPane().add(paypalLink);
        donateMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, paypalLink,
                    "Donate", JOptionPane.INFORMATION_MESSAGE);
        });
*/

        setJMenuBar(MenuBar);
        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ALTXA");
        pack();
        setSize(new Dimension(800, 800));
        setLocationRelativeTo(null);
        setVisible(true);

        URL iconURL = getClass().getClassLoader().getResource("text.png");
        if (iconURL != null)
            setIconImage(new ImageIcon(iconURL).getImage());

        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            GUI gui = new GUI();
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
        contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(-11760710));
        panel1.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        keywordTab = new JPanel();
        keywordTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Text Analysis", keywordTab);
        keywordTabContent = new KeywordAnalysisTab();
        keywordTab.add(keywordTabContent.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nGramTab = new JPanel();
        nGramTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("NGram Analysis", nGramTab);
        nGramTabContent = new NGramTab();
        nGramTab.add(nGramTabContent.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        zTestTab = new JPanel();
        zTestTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("ZTest Analysis", zTestTab);
        zTestTabContent = new ZTestTab();
        zTestTab.add(zTestTabContent.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MenuBar = new JMenuBar();
        MenuBar.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(MenuBar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        helpMenu = new JMenu();
        helpMenu.setLayout(new GridLayoutManager(1, 1, new Insets(2, 2, 0, 0), -1, -1));
        helpMenu.setText("Help");
        MenuBar.add(helpMenu, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        MenuBar.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        fileMenu = new JMenu();
        fileMenu.setLayout(new GridLayoutManager(1, 1, new Insets(2, 2, 0, 0), -1, -1));
        fileMenu.setText("File");
        MenuBar.add(fileMenu, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
