package org.statistical.analysis.utils;

import org.apache.commons.lang3.StringUtils;
import org.statistical.analysis.pojo.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class IOUtils {

    public static String readTextFile(File file) throws IOException {
        if (!file.exists())
            throw new IOException("The file " + file.getName() + " does not exist");

        StringBuilder text = new StringBuilder();
        String input;
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            while ((input = br.readLine()) != null)
                text.append(" ").append(input);
        }
        return text.toString();
    }

    public static List<String> getKeywordList(String keywordFile) throws IOException {
        String text = readTextFile(new File(keywordFile)).toLowerCase();
        String[] words = text.split(" ");
        return new ArrayList<>(Arrays.asList(words));
    }

    public static String outputKeywordAnalysis(KeywordAnalysis keywordAnalysis) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("\n").append("RESULTS: ").append("\n");
        resultString.append(formattedPrint("Sentence count:", String.valueOf(keywordAnalysis.getSentenceCount())));
        resultString.append(formattedPrint("Word count:", String.valueOf(keywordAnalysis.getTotalWords())));
        resultString.append(formattedPrint("Average letters per word: ", String.valueOf(round3Decimals(keywordAnalysis.getAverageLettersByWorld()))));
        resultString.append(formattedPrint("Average words per sentence: ", String.valueOf(round3Decimals(keywordAnalysis.getWordsPerSentence()))));
        resultString.append(formattedPrint("Unique words: ", String.valueOf(keywordAnalysis.getUniqueWords().size())));
        resultString.append(formattedPrint("Richness: ", round3Decimals(keywordAnalysis.getRichness()) + "%"));
        if (keywordAnalysis.getKeywordCount() != null) {
            resultString.append("\n").append("KEYWORD RESULTS:").append("\n");
            for (Map.Entry<String, Double> entry : keywordAnalysis.getKeywordCount().entrySet()) {
                resultString.append(formattedPrint(
                        entry.getKey(),
                        round3Decimals(entry.getValue()) + "%",
                        String.valueOf((int) (entry.getValue() * keywordAnalysis.getTotalWords()) / 100)
                ));
            }
        }

        return resultString.toString();
    }

    public static String outputNGramAnalysis(NGramAnalysis nGramAnalysis) throws IOException {
        StringBuilder resultString = new StringBuilder();
        resultString.append("\n").append("NGRAM ANALYSIS RESULTS: ").append("\n");

        int lastSize = -1;
        int counter = 1;
        for (Map.Entry<String, NGramResult> entry : nGramAnalysis.getNGramResultMap().entrySet()) {
            if (lastSize != entry.getValue().getNGramSize()) {
                lastSize = entry.getValue().getNGramSize();
                resultString.append("-------- ").append(entry.getValue().getNGramSize()).append(" WORD N-GRAMS ------------------------------------------------------------------------").append("\n");
                resultString.append(formattedPrint("--- N-Gram ---", "--- " + nGramAnalysis.getTextAName() + " ---", "--- " + nGramAnalysis.getTextBName() + " ---")).append("\n");
            }
            resultString.append(formattedPrint(counter + ") " + entry.getKey(), String.valueOf(entry.getValue().getAppearancesInA()), String.valueOf(entry.getValue().getAppearancesInB()))).append("\n");
            counter++;
        }

        return resultString.toString();
    }

    public static void writeOutAnalysis(KeywordAnalysis keywordAnalysisA) throws IOException {
        File outFile = new File(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + "_analysis.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            writeKeywordAnalysis(bufferedWriter, keywordAnalysisA);
        }
    }

    public static void writeOutNGramAnalysisCsv(NGramAnalysis nGramAnalysis) throws IOException {
        File outFile = new File(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + "_NGramAnalysis.csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            int lastSize = -1;
            for (Map.Entry<String, NGramResult> entry : nGramAnalysis.getNGramResultMap().entrySet()) {
                if (lastSize != entry.getValue().getNGramSize()) {
                    lastSize = entry.getValue().getNGramSize();
                    bufferedWriter.write(entry.getValue().getNGramSize() + " WORD N-GRAMS,--------,-------- ");
                    bufferedWriter.newLine();
                    bufferedWriter.write("--- N-Gram ---, " + nGramAnalysis.getTextAName() + "," + nGramAnalysis.getTextBName());
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(entry.getKey() + "," + entry.getValue().getAppearancesInA() + "," + entry.getValue().getAppearancesInB());
                bufferedWriter.newLine();
            }
        }
    }

    public static void writeOutNGramCount(String textAName, Map<Integer, Integer> nGramCountA, String textBName, Map<Integer, Integer> nGramCountB) throws IOException {
        File outFile = new File(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + "_NGramCount.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            writeNGramCountToFile(textAName, nGramCountA, bufferedWriter);
            writeNGramCountToFile(textBName, nGramCountB, bufferedWriter);
        }
    }

    private static void writeNGramCountToFile(String textBName, Map<Integer, Integer> nGramCountB, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(textBName);
        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL NGRAM COUNT:");
        bufferedWriter.newLine();
        for (Map.Entry<Integer, Integer> entry : nGramCountB.entrySet()) {
            bufferedWriter.write("  " + entry.getKey() + " word nGrams: " + entry.getValue());
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();
    }

    private static void writeKeywordAnalysis(BufferedWriter bufferedWriter, KeywordAnalysis keywordAnalysis) throws IOException {
        bufferedWriter.write("Keyword analysis: ");
        bufferedWriter.newLine();
        bufferedWriter.write("RESULTS: ");
        bufferedWriter.newLine();
        bufferedWriter.write("  Sentence count: " + keywordAnalysis.getSentenceCount());
        bufferedWriter.newLine();
        bufferedWriter.write("  Word count: " + keywordAnalysis.getTotalWords());
        bufferedWriter.newLine();
        bufferedWriter.write("  Average letters per word: " + round3Decimals(keywordAnalysis.getAverageLettersByWorld()));
        bufferedWriter.newLine();
        bufferedWriter.write("  Average words per sentence: " + round3Decimals(keywordAnalysis.getWordsPerSentence()));
        bufferedWriter.newLine();
        bufferedWriter.write("  Unique words: " + keywordAnalysis.getUniqueWords().size());
        bufferedWriter.newLine();
        bufferedWriter.write("  Richness: " + round3Decimals(keywordAnalysis.getRichness()) + "%");
        bufferedWriter.newLine();

        if (keywordAnalysis.getKeywordCount() != null) {
            bufferedWriter.write("KEYWORD RESULTS:");
            bufferedWriter.newLine();
            for (Map.Entry<String, Double> entry : keywordAnalysis.getKeywordCount().entrySet()) {
                bufferedWriter.write("  " + entry.getKey() + "    " + round3Decimals(entry.getValue()) + "%" + "    " + (int) (entry.getValue() * keywordAnalysis.getTotalWords()) / 100);
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
        }
    }

    private static String formattedPrint(String key, String value) {
        return String.format("    %-35s - %2s", key, value) + "\n";
    }

    private static String formattedPrint(String key, String firstValue, String secondValue) {
        return String.format("    %-35s - %-35s  - %-2s", key, firstValue, secondValue) + "\n";
    }

    private static double round3Decimals(double a) {
        return (double) Math.round(a * 1000) / 1000;
    }


    public static double getPercent(int remaining, int total) {
        return ((double) remaining / total) * 100;
    }

    public static String outputNGramCount(Map<Integer, Integer> nGramCountA, String textName) {
        StringBuilder string = new StringBuilder();
        string.append(textName).append("\n");
        string.append("TOTAL NGRAM COUNT:");
        for (Map.Entry<Integer, Integer> entry : nGramCountA.entrySet()) {
            string.append("\n    " + entry.getKey() + " word nGrams: " + entry.getValue());
        }
        string.append("\n");
        string.append("\n");

        return string.toString();
    }

    public static void writeOutZetaTestCsv(String fileNameA, String filenameB, List<ZetaTestXYItem> zetaTestXYItemsA, List<ZetaTestXYItem> zetaTestXYItemsB, List<ZetaTestXYItem> zetaTestXYItemsC) throws IOException {
        File outFile = new File(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + "_zetaTest.csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            bufferedWriter.write("File," + fileNameA + " markers," + filenameB + " markers");
            bufferedWriter.newLine();
            for (ZetaTestXYItem item : zetaTestXYItemsA) {
                bufferedWriter.write(item.getText() + "," + item.getPercentageAuthorA() + "," + item.getPercentageAuthorB());
                bufferedWriter.newLine();
            }
            for (ZetaTestXYItem item : zetaTestXYItemsB) {
                bufferedWriter.write(item.getText() + "," + item.getPercentageAuthorA() + "," + item.getPercentageAuthorB());
                bufferedWriter.newLine();
            }
            for (ZetaTestXYItem item : zetaTestXYItemsC) {
                bufferedWriter.write(item.getText() + "," + item.getPercentageAuthorA() + "," + item.getPercentageAuthorB());
                bufferedWriter.newLine();
            }
        }
    }

    public static void writeOutMarkers(String fileName, List<String> markets) throws IOException {
        File outFile = new File(new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date()) + fileName + "_Markers.csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            for (String marker : markets) {
                bufferedWriter.write(marker);
                bufferedWriter.newLine();
            }
        }
    }

    public static String outputZTestAnalysis(ZetaTest zetaTest) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("\n").append("ZTEST ANALYSIS RESULTS: ").append("\n");
        resultString.append("Text\t").append(zetaTest.getTextAName())
                .append(" markers\t").append(zetaTest.getTextBName()).append(" markers").append("\n");

        for (ZetaTestXYItem item : zetaTest.getTextAGraphItems()) {
            resultString.append(item.getText()).append("\t")
                    .append(item.getPercentageAuthorA()).append("\t")
                    .append(item.getPercentageAuthorB()).append("\n");
        }

        for (ZetaTestXYItem item : zetaTest.getTextBGraphItems()) {
            resultString.append(item.getText()).append("\t")
                    .append(item.getPercentageAuthorA()).append("\t")
                    .append(item.getPercentageAuthorB()).append("\n");
        }

        for (ZetaTestXYItem item : zetaTest.getTextCGraphItems()) {
            resultString.append(item.getText()).append("\t")
                    .append(item.getPercentageAuthorA()).append("\t")
                    .append(item.getPercentageAuthorB()).append("\n");
        }

        return resultString.toString();
    }

    public static void printDebugFile(String fileName, ProcessedText text) throws IOException {
        File file = new File(fileName + ".txt");
        if (file.exists())
            file = new File(fileName + UUID.randomUUID() + ".txt");
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("#This file contains each word identified and split by ALTXA. Each line represents a sentence. All punctuation has been removed and all letters have been put on lowercase: ");
            for (List<String> sentence : text.getSentenceWordList()) {
                writer.newLine();
                writer.write(StringUtils.join(sentence, " "));
            }
        }
    }
}
