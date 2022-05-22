package org.statistical.analysis.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KeywordAnalysisUtilsTest {

    @Test
    public void testIgnore() throws Exception {
        String textIgnore = "Esto no se ignora [esto se ignora]";
        String textIgnore2 = "Esto no se ignora [][esto se ignora] hola [ asd]asd";
        String textIgnoreMalformed = "Esto no se ignora [][esto se ignora][][ hola [ asd]asd]]";

        assertEquals("Esto no se ignora  ", AnalysisUtils.deleteIgnoredSequences(textIgnore));
        assertEquals("Esto no se ignora    hola  asd", AnalysisUtils.deleteIgnoredSequences(textIgnore2));
        assertThrows(Exception.class, () -> AnalysisUtils.deleteIgnoredSequences(textIgnoreMalformed));
    }

    @Test
    public void testIgnoreAdvanced() {
        String textIgnore = "Esto no se ignora [esto se ignora]";
        String textIgnore2 = "Esto no se ignora [][esto se ignora] hola [ asd]asd";
        String textIgnore3 = "Esto no se ignora [][esto se ignora][][ hola [ asd]asd]]";
        String textIgnore4 = "ESTO FUERA Esto no eSto fuEra";

        assertEquals("Esto no se ignora  ", AnalysisUtils.deleteIgnoredSequencesWithRegex(textIgnore, "\\[.*?\\]"));
        assertEquals("Esto no se ignora    hola  asd", AnalysisUtils.deleteIgnoredSequencesWithRegex(textIgnore2, "\\[.*?\\]"));
        assertEquals("Esto no se ignora     asd]]", AnalysisUtils.deleteIgnoredSequencesWithRegex(textIgnore3, "\\[.*?\\]"));
        assertEquals("    Esto no    ", AnalysisUtils.deleteIgnoredSequencesWithRegex(textIgnore4, "\\w+[A-Z]+(\\w+)?"));
    }

    @Test
    public void testSplitSentences() {
        String text = "¡Esto es una frase! Esto es otra, a ver la coma. ¿Qué hará aquí?, no lo sé. Hola.";
        List<String> splitText = Arrays.asList("¡Esto es una frase", " Esto es otra, a ver la coma",
                " ¿Qué hará aquí", ", no lo sé", " Hola");

        assertArrayEquals(splitText.toArray(), AnalysisUtils.splitTextInSentences(text).toArray());
    }

    @Test

    public void testSplitWords() {
        String sentence = "'Tis is an example text to test the word splitter, aaaaaa";
        List<String> splitWords = Arrays.asList("'tis", "is", "an", "example", "text", "to", "test",
                "the", "word", "splitter", "aaaaaa");
        assertArrayEquals(splitWords.toArray(), AnalysisUtils.splitSentenceInWords(sentence).toArray());
    }

    @Test
    public void testUniqueWords() {
        List<String> words = Arrays.asList("this", "this", "an", "an", "an", "to", "test", "this", "word", "word");
        List<String> uniqueWords = Arrays.asList("this", "an", "to", "test", "word");
        assertArrayEquals(uniqueWords.toArray(), AnalysisUtils.getUniqueWords(words).toArray());
    }

    @Test
    public void testAvgLettersWords() {
        List<String> words = Arrays.asList("this", "an", "to", "test", "word", "whatever");
        assertEquals(4.0D, AnalysisUtils.getAverageLettersByWord(words));
    }
}

