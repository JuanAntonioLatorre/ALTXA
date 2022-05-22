package org.statistical.analysis.pojo;

import java.util.Map;

public class NGramAnalysis {
    private String textAName;
    private String textBName;
    private Map<String, NGramResult> nGramResultMap;

    public NGramAnalysis(String textAName, String textBName) {
        this.textAName = textAName;
        this.textBName = textBName;
    }

    public NGramAnalysis() {
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

    public Map<String, NGramResult> getNGramResultMap() {
        return nGramResultMap;
    }

    public synchronized void putNGramResultOnMap(String key, NGramResult nGramResult) {
        this.nGramResultMap.put(key, nGramResult);
    }

    public synchronized boolean nGramResultMapContains(String key) {
        return this.nGramResultMap.containsKey(key);
    }

    public void setNGramResultMap(Map<String, NGramResult> nGramResultMap) {
        this.nGramResultMap = nGramResultMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NGramAnalysis) {
            for (Map.Entry<String, NGramResult> entry : this.nGramResultMap.entrySet()) {
                if (((NGramAnalysis) obj).nGramResultMap.containsKey(entry.getKey())) {
                    NGramResult nGramResult = ((NGramAnalysis) obj).nGramResultMap.get(entry.getKey());
                    if (nGramResult.getAppearancesInA() != entry.getValue().getAppearancesInA() ||
                            nGramResult.getAppearancesInB() != entry.getValue().getAppearancesInB() ||
                            nGramResult.getNGramSize() != entry.getValue().getNGramSize()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
