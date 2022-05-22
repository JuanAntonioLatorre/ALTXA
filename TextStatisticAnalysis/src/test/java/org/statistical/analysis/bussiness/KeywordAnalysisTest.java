package org.statistical.analysis.bussiness;

import org.junit.Test;
import org.statistical.analysis.business.StatisticalAnalysis;
import org.statistical.analysis.pojo.KeywordAnalysis;
import org.statistical.analysis.pojo.Settings;
import org.statistical.analysis.utils.IOUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KeywordAnalysisTest {
    Settings settings = new Settings(true, true, "([A-Z]{2,})");

    @Test
    public void testAnalysis() throws Exception {
        String text = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
        List<String> keywords = Arrays.asList("A", "B");

        KeywordAnalysis keywordAnalysis = StatisticalAnalysis.getKeywordAnalysis(text, keywords, null, settings);

        assertEquals(1, keywordAnalysis.getSentenceCount());
        assertEquals(26, keywordAnalysis.getTotalWords());
        assertEquals(100, keywordAnalysis.getRichness());
        assertEquals(26, keywordAnalysis.getWordsPerSentence());
        assertEquals(26, keywordAnalysis.getUniqueWords().size());
        assertEquals(3.8461538461538463, keywordAnalysis.getKeywordCount().get("A"));
        assertEquals(3.8461538461538463, keywordAnalysis.getKeywordCount().get("B"));
        IOUtils.outputKeywordAnalysis(keywordAnalysis);

    }

    @Test
    public void testAnalysis2() throws Exception {
        String text = "ESTO NO SALE a b c d e. f g h i j k! [ a a a a a a a b b" +
                " b b b b ]l m n o p q r s? t u v w x y z";
        List<String> keywords = Arrays.asList("A", "B");

        KeywordAnalysis keywordAnalysis = StatisticalAnalysis.getKeywordAnalysis(text, keywords, null, settings);

        assertEquals(4, keywordAnalysis.getSentenceCount());
        assertEquals(26, keywordAnalysis.getTotalWords());
        assertEquals(100, keywordAnalysis.getRichness());
        assertEquals(6.5, keywordAnalysis.getWordsPerSentence());
        assertEquals(26, keywordAnalysis.getUniqueWords().size());
        assertEquals(3.8461538461538463, keywordAnalysis.getKeywordCount().get("A"));
        assertEquals(3.8461538461538463, keywordAnalysis.getKeywordCount().get("B"));
        IOUtils.outputKeywordAnalysis(keywordAnalysis);

    }

    @Test
    public void testAnalysis3() throws Exception {
        String text = "ESTO NO SALE a a A A A a b c d e. f g h i j k! [ " +
                "a a a a a a a b b b b b b ]l m B bb B n o p q r s? t u v w x y z";
        List<String> keywords = Arrays.asList("A", "B");

        KeywordAnalysis keywordAnalysis = StatisticalAnalysis.getKeywordAnalysis(text, keywords, null, settings);

        assertEquals(4, keywordAnalysis.getSentenceCount());
        assertEquals(34, keywordAnalysis.getTotalWords());
        assertEquals(79.41176470588235, keywordAnalysis.getRichness());
        assertEquals(8.5, keywordAnalysis.getWordsPerSentence());
        assertEquals(27, keywordAnalysis.getUniqueWords().size());
        assertEquals(17.647058823529413, keywordAnalysis.getKeywordCount().get("A"));
        assertEquals(8.823529411764707, keywordAnalysis.getKeywordCount().get("B"));
        IOUtils.outputKeywordAnalysis(keywordAnalysis);

    }

    @Test
    public void realKeywordTest() throws Exception {
        List<String> keyWords = Arrays.asList("your", "comes", "thee");

        String romeoAndJuliet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/RomeoAndJuliet.txt")).toURI()));
        KeywordAnalysis keywordAnalysis = StatisticalAnalysis.getKeywordAnalysis(romeoAndJuliet, keyWords, null, settings);

        assertEquals(52, keywordAnalysis.getSentenceCount());
        assertEquals(421, keywordAnalysis.getTotalWords());
        assertEquals(39.904, keywordAnalysis.getRichness(), 0.01);
        assertEquals(8.096, keywordAnalysis.getWordsPerSentence(), 0.01);
        assertEquals(168, keywordAnalysis.getUniqueWords().size());
        assertEquals(0.712, keywordAnalysis.getKeywordCount().get("your"), 0.01);
        assertEquals(0.473, keywordAnalysis.getKeywordCount().get("comes"), 0.01);
        assertEquals(0.710, keywordAnalysis.getKeywordCount().get("thee"), 0.01);
        IOUtils.outputKeywordAnalysis(keywordAnalysis);
    }
}
