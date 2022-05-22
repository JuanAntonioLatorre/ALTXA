package org.statistical.analysis.utils;

import org.apache.commons.lang3.StringUtils;
import org.statistical.analysis.pojo.NGramResult;
import org.statistical.analysis.pojo.ProcessedText;
import org.statistical.analysis.pojo.Settings;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AnalysisUtils {
    public static final String SENTENCE_END = ".!?:";
    public static final String IGNORE_TEXT_START = "[";
    public static final String IGNORE_TEXT_END = "]";
    private final static String REGEX = "('?\\b[^\\s]+\\b'?)";

    public static String deleteIgnoredSequences(String text) throws Exception {
        while (text.contains(IGNORE_TEXT_START) && text.contains(IGNORE_TEXT_END)) {
            int startIndex = text.indexOf(IGNORE_TEXT_START);
            int endIndex = text.indexOf(IGNORE_TEXT_END) + 1;
            if (startIndex < endIndex) {
                //Se pone espacio por si puede juntar palabras sin querer
                text = text.replace(text.substring(startIndex, endIndex), " ");
            } else {
                throw new Exception("Malformed ignored sequences caused by missing '[' or ']' or nested ignored sequences, " +
                        "(example '[ [  ] ]') nested ignored sequences are not permitted. Context: \""
                        + text.substring(endIndex - 30, text.length() > startIndex + 30 ? startIndex + 30 : text.length() - 1)
                        + "\"");
            }
        }
        if (text.contains(IGNORE_TEXT_START)) {
            throw new Exception("Malformed ignored sequences, context: \"" + text.substring(
                    text.indexOf(IGNORE_TEXT_START) - 30,
                    text.length() > text.indexOf(IGNORE_TEXT_START) + 30 ?
                            text.indexOf(IGNORE_TEXT_START) + 30 : text.length() - 1)
                    + "\"");
        } else if (text.contains(IGNORE_TEXT_END)) {
            throw new Exception("Malformed section to ignore, context: \"" + text.substring(
                    text.indexOf(IGNORE_TEXT_END) - 30,
                    text.length() > text.indexOf(IGNORE_TEXT_END) + 30 ?
                            text.indexOf(IGNORE_TEXT_END) + 30 : text.length() - 1)
                    + "\"");
        }
        return text;
    }


    public static String deleteIgnoredSequencesWithRegex(String text, String regex) {
        //Se pone espacio por si puede juntar palabras sin querer
        return text.replaceAll(regex, " ");
    }


    public static List<String> splitTextInSentences(String text) {
        List<String> sentenceList = Collections.singletonList(text);

        for (char c : SENTENCE_END.toCharArray()) {
            List<String> auxSentences = new ArrayList<>();
            for (String sentence : sentenceList) {
                auxSentences.addAll(Arrays.asList(sentence.split(Pattern.quote(String.valueOf(c)))));
            }
            sentenceList = auxSentences;
        }

        return sentenceList;
    }

    public static List<String> splitSentenceInWords(String sentence) {
        sentence = StringUtils.replace(sentence, "\n", " ");
        sentence = StringUtils.replace(sentence, "\r", " ");
        sentence = StringUtils.replace(sentence, "\t", " ");

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(sentence);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            if (StringUtils.isNotBlank(matcher.group(1))) {
                words.add(matcher.group(1).toLowerCase());
            }
        }
        return words;
    }

    public static Map<String, Double> getKeywordCount(List<String> keywords, List<String> words) {
        Map<String, Double> keywordMap = new LinkedHashMap<>();
        for (String keyword : keywords) {
            keywordMap.put(keyword, ((double) Collections.frequency(words, keyword.toLowerCase()) * 100) / words.size());
        }
        keywordMap = sortByValue(keywordMap);
        return keywordMap;
    }

    public static List<String> getUniqueWords(List<String> words) {
        List<String> uniqueWords = new ArrayList<>();
        for (String word : words) {
            if (!uniqueWords.contains(word)) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    public static double getAverageLettersByWord(List<String> words) {
        double avg = 0;
        int wordCount = words.size();
        int letterCount = 0;

        if (!words.isEmpty()) {
            for (String word : words) {
                letterCount = letterCount + word.length();
            }

            avg = (double) letterCount / (double) wordCount;
        }

        return avg;
    }

    public static ProcessedText getProcessedText(String text, Settings settings) throws Exception {
        return getProcessedText(text, settings, null);
    }

    public static ProcessedText getProcessedText(String text, Settings settings, JTextArea jTextArea) throws Exception {
        ProcessedText processedText = new ProcessedText();
        processedText.setRawText(text);

        if (settings.isDeleteSimple()) {
            VisualUtils.appendOnTextAreaConcurrentlyIfExists(jTextArea, "Deleting ignored sequences... \n");
            text = AnalysisUtils.deleteIgnoredSequences(text);
        }

        if (settings.isDeleteAdvanced()) {
            VisualUtils.appendOnTextAreaConcurrentlyIfExists(jTextArea, "Deleting ignored sequences with regex... \n");
            text = AnalysisUtils.deleteIgnoredSequencesWithRegex(text, settings.getRegex());
        }

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(jTextArea, "Splitting text into sentences... \n");
        List<String> sentenceList = AnalysisUtils.splitTextInSentences(text);
        List<List<String>> allSplitSentences = getAllSplitSentences(sentenceList);
        processedText.setSentenceList(sentenceList);

        VisualUtils.appendOnTextAreaConcurrentlyIfExists(jTextArea, "Splitting sentences into words... \n");

        processedText.setSentenceWordList(allSplitSentences);
        processedText.setWords(getAllWordsFromSentenceLists(allSplitSentences));

        if (settings.isDebugMode()) {
            VisualUtils.appendOnTextAreaConcurrentlyIfExists(jTextArea, "Writing debug file... \n");
            IOUtils.printDebugFile(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + "_DebugFile", processedText);
        }
        return processedText;
    }

    public static List<List<String>> getAllSplitSentences(List<String> sentences) {
        List<List<String>> splitSentences = new ArrayList<>();
        for (String sentence : sentences)
            splitSentences.add(AnalysisUtils.splitSentenceInWords(sentence));

        return splitSentences;
    }

    private static List<String> getAllWordsFromSentenceLists(List<List<String>> splitSentences) {
        List<String> words = new ArrayList<>();
        for (List<String> sentenceWords : splitSentences)
            words.addAll(sentenceWords);

        return words;
    }

    public static NGramResult getNGramResult(List<String> NGram, ProcessedText text, ProcessedText textToCompare) {
        NGramResult nGramResult = new NGramResult();
        nGramResult.setNGramSize(NGram.size());
        nGramResult.setAppearancesInA(countNGramAppearances(NGram, text));
        nGramResult.setAppearancesInB(countNGramAppearances(NGram, textToCompare));

        return nGramResult;
    }

    public static int countNGramAppearances(List<String> NGram, ProcessedText text) {
        int count = 0;
        String nGramRegex = "(?<=\\s|^)" + String.join(" ", NGram).toLowerCase() + "(?=\\s|$)";
        Pattern pattern = Pattern.compile(nGramRegex);
        for (List<String> sentenceInWords : text.getSentenceWordList()) {
            String sentenceToMatch = " " + String.join(" ", sentenceInWords).toLowerCase() + " ";
            Matcher matcher = pattern.matcher(sentenceToMatch);
            while (matcher.find()) {
                count += 1;
            }
        }

        return count;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static List<List<List<String>>> splitListForTasks(List<List<String>> sentenceWordList, int listSize) {
        List<List<List<String>>> splitLists = new ArrayList<>();
        int remaining = sentenceWordList.size();
        int cursor = 0;
        while (remaining > 0) {
            if (cursor + listSize > sentenceWordList.size())
                splitLists.add(sentenceWordList.subList(cursor, sentenceWordList.size()));
            else
                splitLists.add(sentenceWordList.subList(cursor, cursor + listSize));

            cursor += listSize;
            remaining -= listSize;
        }

        return splitLists;
    }
}
