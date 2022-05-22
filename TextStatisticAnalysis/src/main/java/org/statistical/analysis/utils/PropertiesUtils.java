package org.statistical.analysis.utils;

import org.statistical.analysis.constants.SettingsConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public abstract class PropertiesUtils {

    public static final String SETTINGS_PROPERTIES_PATH = "altxa_settings.properties";

    public static Properties getSettingsFile() throws IOException {
        Properties properties;
        File propertiesFile = new File(SETTINGS_PROPERTIES_PATH);
        if (propertiesFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
                properties = new Properties();
                properties.load(fileInputStream);
            }
        } else {
            properties = new Properties();
            properties.setProperty(SettingsConstants.DELETE_IGNORED_SEQUENCES, "false");
            properties.setProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES, "false");
            properties.setProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES_REGEX, "");
            properties.setProperty(SettingsConstants.DEBUG_MODE, "false");
            properties.store(new FileWriter(SETTINGS_PROPERTIES_PATH),
                    "Saves settings in between sessions. If deleted, a default file will be created if needed");
        }

        return properties;
    }

    public static void saveSettingsFile(Properties properties) throws IOException {
        properties.store(new FileWriter(SETTINGS_PROPERTIES_PATH),
                "Saves settings in between sessions. If deleted, a default file will be created if needed");
    }

}
