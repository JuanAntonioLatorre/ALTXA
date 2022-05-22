package org.statistical.analysis.pojo;

import java.util.List;

public class ProcessedText {
    private String fileName;
    private String rawText;
    private List<String> sentenceList;
    private List<List<String>> sentenceWordList;
    private List<String> words;
    private List<List<String>> wordFragments;

    public ProcessedText() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public List<String> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<String> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public List<List<String>> getSentenceWordList() {
        return sentenceWordList;
    }

    public void setSentenceWordList(List<List<String>> sentenceWordList) {
        this.sentenceWordList = sentenceWordList;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<List<String>> getWordFragments() {
        return wordFragments;
    }

    public void setWordFragments(List<List<String>> wordFragments) {
        this.wordFragments = wordFragments;
    }
}
