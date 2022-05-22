package org.statistical.analysis.pojo;

import java.util.List;
import java.util.Map;

public class KeywordAnalysis {
    private Integer sentenceCount;
    private Integer totalWords;
    private Double averageLettersByWorld;
    private Double wordsPerSentence;
    private List<String> uniqueWords;
    private Double richness;
    private Map<String, Double> keywordCount;

    public Double getRichness() {
        return richness;
    }

    public void setRichness(Double richness) {
        this.richness = richness;
    }

    public Double getWordsPerSentence() {
        return wordsPerSentence;
    }

    public void setWordsPerSentence(Double wordsPerSentence) {
        this.wordsPerSentence = wordsPerSentence;
    }

    public Map<String, Double> getKeywordCount() {
        return keywordCount;
    }

    public void setKeywordCount(Map<String, Double> keywordCount) {
        this.keywordCount = keywordCount;
    }

    public Integer getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(Integer sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public List<String> getUniqueWords() {
        return uniqueWords;
    }

    public void setUniqueWords(List<String> uniqueWords) {
        this.uniqueWords = uniqueWords;
    }

    public Integer getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Integer totalWords) {
        this.totalWords = totalWords;
    }

    public Double getAverageLettersByWorld() {
        return averageLettersByWorld;
    }

    public void setAverageLettersByWorld(Double averageLettersByWorld) {
        this.averageLettersByWorld = averageLettersByWorld;
    }
}
