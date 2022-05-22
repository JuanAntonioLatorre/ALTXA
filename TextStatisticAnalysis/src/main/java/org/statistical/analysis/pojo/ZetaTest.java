package org.statistical.analysis.pojo;

import java.util.List;

public class ZetaTest {
    private String textAName;
    private String textBName;
    private String textCName;

    private List<String> textAMarkers;
    private List<String> textBMarkers;

    private List<ZetaTestXYItem> textAGraphItems;
    private List<ZetaTestXYItem> textBGraphItems;
    private List<ZetaTestXYItem> textCGraphItems;

    public List<String> getTextAMarkers() {
        return textAMarkers;
    }

    public void setTextAMarkers(List<String> textAMarkers) {
        this.textAMarkers = textAMarkers;
    }

    public List<String> getTextBMarkers() {
        return textBMarkers;
    }

    public void setTextBMarkers(List<String> textBMarkers) {
        this.textBMarkers = textBMarkers;
    }

    public List<ZetaTestXYItem> getTextAGraphItems() {
        return textAGraphItems;
    }

    public void setTextAGraphItems(List<ZetaTestXYItem> textAGraphItems) {
        this.textAGraphItems = textAGraphItems;
    }

    public List<ZetaTestXYItem> getTextBGraphItems() {
        return textBGraphItems;
    }

    public void setTextBGraphItems(List<ZetaTestXYItem> textBGraphItems) {
        this.textBGraphItems = textBGraphItems;
    }

    public List<ZetaTestXYItem> getTextCGraphItems() {
        return textCGraphItems;
    }

    public void setTextCGraphItems(List<ZetaTestXYItem> textCGraphItems) {
        this.textCGraphItems = textCGraphItems;
    }

    public String getTextAName() {
        return textAName;
    }

    public void setTextAName(String textAName) {
        this.textAName = textAName;
    }

    public String getTextBName() {
        return textBName;
    }

    public void setTextBName(String textBName) {
        this.textBName = textBName;
    }

    public String getTextCName() {
        return textCName;
    }

    public void setTextCName(String textCName) {
        this.textCName = textCName;
    }
}
