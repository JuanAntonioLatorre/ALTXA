package org.statistical.analysis.utils;

import org.junit.Test;
import org.statistical.analysis.pojo.NGramResult;
import org.statistical.analysis.pojo.ProcessedText;
import org.statistical.analysis.pojo.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NGramAnalysisUtilsTests {
    Settings settings = new Settings(true, true, "([A-Z]{2,})");

    @Test
    public void testGetNGramResult() throws Exception {
        ProcessedText processedText = AnalysisUtils.getProcessedText("a, b, c ,b, c, d, e, q, s, d ,f ", settings);
        ProcessedText processedTextToCompare = AnalysisUtils.getProcessedText("asd, qwe, da ,ew, vv, a, b, c, a. a ,a ", settings);
        List<String> nGram = Arrays.asList("a", "b");
        NGramResult nGramResult = AnalysisUtils.getNGramResult(nGram, processedText, processedTextToCompare);
        assertEquals(1, nGramResult.getAppearancesInA());
        assertEquals(1, nGramResult.getAppearancesInB());

        nGram = Collections.singletonList("a");
        nGramResult = AnalysisUtils.getNGramResult(nGram, processedText, processedTextToCompare);
        assertEquals(1, nGramResult.getAppearancesInA());
        assertEquals(4, nGramResult.getAppearancesInB());

        nGram = Arrays.asList("b", "c");
        nGramResult = AnalysisUtils.getNGramResult(nGram, processedText, processedTextToCompare);
        assertEquals(2, nGramResult.getAppearancesInA());
        assertEquals(1, nGramResult.getAppearancesInB());
    }

    @Test
    public void testCountNGramAppearances() throws Exception {
        ProcessedText processedText = AnalysisUtils.getProcessedText("a b c d e f a b c d e f a s d a s d c a e r f a q e d", settings);

        List<String> nGram = Arrays.asList("a", "b", "c");
        int count = AnalysisUtils.countNGramAppearances(nGram, processedText);
        assertEquals(2, count);

        nGram = Arrays.asList("b", "c", "d");
        count = AnalysisUtils.countNGramAppearances(nGram, processedText);
        assertEquals(2, count);
    }

    @Test
    public void testSplitListForTasks() {
        List<List<String>> test = new ArrayList<>();
        List<String> testSub = Arrays.asList("1", "a", "a");
        List<String> testSub2 = Arrays.asList("2", "a", "a");
        List<String> testSub3 = Arrays.asList("3", "a", "a");
        List<String> testSub4 = Arrays.asList("4", "a", "a");

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        List<List<List<String>>> listResult = AnalysisUtils.splitListForTasks(test, 2);
        assertEquals(2, listResult.size());

        listResult = AnalysisUtils.splitListForTasks(test, 1);
        assertEquals(4, listResult.size());

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        test.add(testSub);
        test.add(testSub2);
        test.add(testSub3);
        test.add(testSub4);

        listResult = AnalysisUtils.splitListForTasks(test, 4);
        assertEquals(6, listResult.size());

        listResult = AnalysisUtils.splitListForTasks(test, 5);
        assertEquals(5, listResult.size());

        listResult = AnalysisUtils.splitListForTasks(test, 3);
        assertEquals(8, listResult.size());
    }
}
