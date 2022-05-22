package org.statistical.analysis.pojo;

import org.statistical.analysis.constants.SettingsConstants;

import java.util.Properties;

public class Settings {

    private boolean deleteSimple;
    private boolean deleteAdvanced;
    private String regex;
    private boolean debugMode;

    public Settings(boolean deleteSimple, boolean deleteAdvanced, String regex) {
        this.deleteSimple = deleteSimple;
        this.deleteAdvanced = deleteAdvanced;
        this.regex = regex;
    }

    public Settings(Properties properties) {
        this.deleteSimple = Boolean.parseBoolean(properties.getProperty(SettingsConstants.DELETE_IGNORED_SEQUENCES));
        this.deleteAdvanced = Boolean.parseBoolean(properties.getProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES));
        this.regex = properties.getProperty(SettingsConstants.ADVANCED_DELETE_IGNORED_SEQUENCES_REGEX);
        this.debugMode = Boolean.parseBoolean(properties.getProperty(SettingsConstants.DEBUG_MODE));
    }

    public boolean isDeleteSimple() {
        return deleteSimple;
    }

    public void setDeleteSimple(boolean deleteSimple) {
        this.deleteSimple = deleteSimple;
    }

    public boolean isDeleteAdvanced() {
        return deleteAdvanced;
    }

    public void setDeleteAdvanced(boolean deleteAdvanced) {
        this.deleteAdvanced = deleteAdvanced;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
