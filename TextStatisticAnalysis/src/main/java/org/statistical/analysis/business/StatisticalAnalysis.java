package org.statistical.analysis.business;

import org.statistical.analysis.pojo.*;
import org.statistical.analysis.tasks.FindNGramsTask;
import org.statistical.analysis.tasks.FindNGramsThreadFactory;
import org.statistical.analysis.utils.AnalysisUtils;
import org.statistical.analysis.utils.IOUtils;
import org.statistical.analysis.utils.VisualUtils;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.statistical.analysis.utils.AnalysisUtils.*;
import static org.statistical.analysis.utils.IOUtils.getKeywordList;
import static org.statistical.analysis.utils.IOUtils.getPercent;

public class StatisticalAnalysis {

    private static final int FRAGMENT_WORD_COUNT = 2000;
    private static final int MARKER_LIMIT = 500;
    private static final String SEPARATOR = "@#@";

    public static KeywordAnalysis getKeywordAnalysis(String text, List<String> keywords, JTextArea outputTextArea, Settings settings) throws Exception {
        KeywordAnalysis keywordAnalysis = new KeywordAnalysis();
        ProcessedText processedText = getProcessedText(text, settings, outputTextArea);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Analyzing... \n");

        keywordAnalysis.setSentenceCount(processedText.getSentenceList().size());
        keywordAnalysis.setTotalWords(processedText.getWords().size());
        keywordAnalysis.setWordsPerSentence((double) processedText.getWords().size() / keywordAnalysis.getSentenceCount());

        if (!keywords.isEmpty())
            keywordAnalysis.setKeywordCount(AnalysisUtils.getKeywordCount(keywords, processedText.getWords()));

        keywordAnalysis.setUniqueWords(AnalysisUtils.getUniqueWords(processedText.getWords()));
        keywordAnalysis.setAverageLettersByWorld(AnalysisUtils.getAverageLettersByWord(processedText.getWords()));
        keywordAnalysis.setRichness(((double) keywordAnalysis.getUniqueWords().size() / keywordAnalysis.getTotalWords()) * 100);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Finished \n");

        return keywordAnalysis;
    }

    public static NGramAnalysis getNGramAnalysis(String text, String textName, String textToCompare, String textToCompareName, int threadNum, JTextArea outputTextArea, Settings settings) throws Exception {

        NGramAnalysis nGramAnalysis = new NGramAnalysis(textName, textToCompareName);
        nGramAnalysis.setNGramResultMap(new LinkedHashMap<>());

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Processing Text A... \n");

        ProcessedText processedText = getProcessedText(text, settings, outputTextArea);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Processing Text B... \n");

        ProcessedText processedTextToCompare = getProcessedText(textToCompare, settings, outputTextArea);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum, new FindNGramsThreadFactory("NGramTask"));

        List<List<List<String>>> lists = splitListForTasks(processedText.getSentenceWordList(), 100);

        for (List<List<String>> list : lists) {
            threadPoolExecutor.execute(new FindNGramsTask(list, processedText, processedTextToCompare, nGramAnalysis));
        }

        threadPoolExecutor.shutdown();
        int lastPercentOutput = 0;

        FindNGramsTask.setCounterZero();
        while (!threadPoolExecutor.isTerminated()) {
            int remaining = processedText.getSentenceWordList().size() - FindNGramsTask.getCounter();
            int percent = (int) (100 - getPercent(remaining, processedText.getSentenceWordList().size()));

            if (percent % 5 == 0 && percent > lastPercentOutput) {
                VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Analysing NGrams... " + FindNGramsTask.getCounter() + "/" + processedText.getSentenceWordList().size() + "\t\t\t" + percent + "%\n");
                lastPercentOutput = percent;
            }
        }

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Finished\n");

        nGramAnalysis.setNGramResultMap(sortByValue(nGramAnalysis.getNGramResultMap()));
        return nGramAnalysis;
    }

    public static Map<Integer, Integer> getNGramCount(String text, Settings settings) throws Exception {
        ProcessedText processedText = getProcessedText(text, settings);

        Map<Integer, Integer> nGramMap = new HashMap<>();

        for (List<String> sentence : processedText.getSentenceWordList()) {
            for (int i = 0; i < sentence.size(); i++) {
                String word = sentence.get(i);
                List<String> nGram = new ArrayList<>(Collections.singletonList(word));
                for (int j = i; j < sentence.size(); j++) {
                    String extraWord = sentence.get(j);
                    if (i != j)
                        nGram.add(extraWord);
                    if (!nGramMap.containsKey(nGram.size())) {
                        nGramMap.put(nGram.size(), 1);
                    } else {
                        nGramMap.put(nGram.size(), nGramMap.get(nGram.size()) + 1);
                    }
                }
            }
        }

        return nGramMap;
    }

    public static ZetaTest getZetaTest(String textAFile, String textBFile, String textCFile, String ignoredWordsFile,
                                       JTextArea outputTextArea, Settings settings) throws Exception {
        ZetaTest zetaTest = new ZetaTest();

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Reading files...\n");

        String rawTextA = IOUtils.readTextFile(new File(textAFile));
        String rawTextB = IOUtils.readTextFile(new File(textBFile));
        String rawTextC = IOUtils.readTextFile(new File(textCFile));
        List<String> ignoredWordList = getKeywordList(ignoredWordsFile);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Processing files...\n");

        ProcessedText textsA = separateAndProcessTexts(rawTextA, true, settings);
        ProcessedText textsB = separateAndProcessTexts(rawTextB, true, settings);
        ProcessedText textC = separateAndProcessTexts(rawTextC, false, settings);

        String textAName = new File(textAFile).getName();
        String textBName = new File(textBFile).getName();
        String textCName = new File(textCFile).getName();

        zetaTest.setTextAName(textAName);
        zetaTest.setTextBName(textBName);
        zetaTest.setTextCName(textCName);

        textsA.setFileName(textAName);
        textsB.setFileName(textBName);
        textC.setFileName(textCName);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Obtaining markers...\n");
        List<String> textAMarkers = getMarkers(textsA, textsB, ignoredWordList);
        List<String> textBMarkers = getMarkers(textsB, textsA, ignoredWordList);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(outputTextArea, "Calculating zeta test...\n");

        List<ZetaTestXYItem> textAGraphItems = getZetaTestResult(textsA, textAMarkers, textBMarkers);
        List<ZetaTestXYItem> textBGraphItems = getZetaTestResult(textsB, textAMarkers, textBMarkers);
        List<ZetaTestXYItem> textCGraphItems = getZetaTestResult(textC, textAMarkers, textBMarkers);

        zetaTest.setTextAMarkers(textAMarkers);
        zetaTest.setTextBMarkers(textBMarkers);

        zetaTest.setTextAGraphItems(textAGraphItems);
        zetaTest.setTextBGraphItems(textBGraphItems);
        zetaTest.setTextCGraphItems(textCGraphItems);

        return zetaTest;
    }

    public static List<ZetaTestXYItem> getZetaTestResult(ProcessedText texts, List<String> textAMarkers, List<String> textBMarkers) {
        List<ZetaTestXYItem> zetaTestXYItems = new ArrayList<>();
        for (List<String> fragment : texts.getWordFragments()) {
            zetaTestXYItems.add(new ZetaTestXYItem(texts.getFileName(), getMarkerPercent(fragment, textAMarkers), getMarkerPercent(fragment, textBMarkers)));
        }

        return zetaTestXYItems;
    }

    private static double getMarkerPercent(List<String> fragment, List<String> textMarkers) {
        int markerPresentCount = 0;
        for (String marker : textMarkers) {
            if (fragment.contains(marker)) {
                markerPresentCount++;
            }
        }
        if (textMarkers.size() == 0)
            return 0D;

        return (double) markerPresentCount / getUniqueWords(fragment).size();
    }

    public static ProcessedText separateAndProcessTexts(String rawText, boolean split, Settings settings) throws Exception {
        List<ProcessedText> texts = new ArrayList<>();
        String[] splitText;

        if (split)
            splitText = rawText.split(SEPARATOR);
        else
            splitText = new String[]{rawText};

        for (String singleText : splitText) {
            ProcessedText processedText = getProcessedText(singleText, settings);
            processedText.setWordFragments(getFragments(processedText.getWords()));
            texts.add(processedText);
        }

        ProcessedText processedText = new ProcessedText();
        processedText.setWords(new ArrayList<>());
        processedText.setWordFragments(new ArrayList<>());
        for (ProcessedText p : texts) {
            processedText.getWords().addAll(p.getWords());
            processedText.getWordFragments().addAll(p.getWordFragments());
            processedText.setRawText(rawText);
        }

        return processedText;
    }

    public static List<String> getMarkers(ProcessedText textA, ProcessedText textB, List<String> ignoredWordList) {
        List<String> markers = new ArrayList<>();
        Map<String, Double> markerPunctuationMap = new LinkedHashMap<>();
        List<String> uniqueWords = getUniqueWords(textA.getWords());

        for (String word : uniqueWords) {
            if (!ignoredWordList.contains(word)) {
                int appearancesInA = getFragmentAppearances(textA.getWordFragments(), word);
                int appearancesInB = getFragmentAppearances(textB.getWordFragments(), word);
                int unAppearancesInB = textB.getWordFragments().size() - appearancesInB;

                double punctuation = (((double) appearancesInA / textA.getWordFragments().size()) + ((double) unAppearancesInB / textB.getWordFragments().size()));
                if (punctuation > 1D) {
                    markerPunctuationMap.put(word, punctuation);
                }
            }
        }

        markerPunctuationMap = sortByValue(markerPunctuationMap);
        int i = 0;
        for (Map.Entry<String, Double> entry : markerPunctuationMap.entrySet()) {
            if (i >= MARKER_LIMIT)
                break;
            markers.add(entry.getKey());
            i++;
        }

        return markers;
    }

    public static List<List<String>> getFragments(List<String> allWords) {
        List<List<String>> fragments = new ArrayList<>();
        for (int i = 0; i < allWords.size(); i = i + FRAGMENT_WORD_COUNT) {
            if (allWords.size() >= i + FRAGMENT_WORD_COUNT) {
                fragments.add(allWords.subList(i, i + FRAGMENT_WORD_COUNT));
            } else {
                if (fragments.size() > 0) {
                    List<String> lastItem = new ArrayList<>(fragments.get(fragments.size() - 1));
                    lastItem.addAll((allWords.subList(i, allWords.size())));
                    fragments.set(fragments.size() - 1, lastItem);
                } else {
                    fragments.add(allWords);
                }
            }
        }

        return fragments;
    }

    private static int getFragmentAppearances(List<List<String>> fragments, String word) {
        int appearances = 0;
        for (List<String> fragment : fragments) {
            if (fragment.contains(word)) {
                appearances++;
            }
        }
        return appearances;
    }
}
