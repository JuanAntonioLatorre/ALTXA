package org.statistical.analysis.bussiness;

import com.google.gson.Gson;
import org.junit.Test;
import org.statistical.analysis.business.StatisticalAnalysis;
import org.statistical.analysis.pojo.NGramAnalysis;
import org.statistical.analysis.pojo.Settings;
import org.statistical.analysis.utils.IOUtils;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class NGramAnalysisTest {
    Settings settings = new Settings(true, true, "([A-Z]{2,})");

    @Test
    public void testNGramSize() throws Exception {
        String text = "a b c d";
        String textToCompare = "a b c d";
        NGramAnalysis nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(10, nGramAnalysis.getNGramResultMap().size());

        text = "a b. c d";
        textToCompare = "a b c d";
        nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(6, nGramAnalysis.getNGramResultMap().size());

        text = "a. b? c. d!";
        textToCompare = "a b c d";
        nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(4, nGramAnalysis.getNGramResultMap().size());

        text = "a a b b! a a z";
        textToCompare = "a a b b c d e r";
        nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(8, nGramAnalysis.getNGramResultMap().size());
    }

    @Test
    public void testNGramAppearances() throws Exception {
        String text = "a b c d";
        String textToCompare = "a b c d";
        NGramAnalysis nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("a b").getAppearancesInB());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("a b c d").getAppearancesInA());
        assertNull(nGramAnalysis.getNGramResultMap().get("a c"));

        text = "a a b b! a a";
        textToCompare = "a a b b";
        nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(2, nGramAnalysis.getNGramResultMap().get("a a").getAppearancesInA());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("a a").getAppearancesInB());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("b b").getAppearancesInA());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("b b").getAppearancesInB());

        text = "a a b b! c a. a a, a b c a b c ac";
        textToCompare = "a a b b! c a. a a, a b c. a b c ac";
        nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(text, null, textToCompare, null, 1, null, settings);
        assertEquals(2, nGramAnalysis.getNGramResultMap().get("a b c").getAppearancesInA());
        assertEquals(2, nGramAnalysis.getNGramResultMap().get("a b c").getAppearancesInB());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("c ac").getAppearancesInA());
        assertEquals(1, nGramAnalysis.getNGramResultMap().get("ac").getAppearancesInB());
    }


    @Test
    public void realNGramTest() throws Exception {

        String romeoAndJuliet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/RomeoAndJuliet.txt")).toURI()));
        String hamlet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/Hamlet.txt")).toURI()));
        NGramAnalysis nGramAnalysis = StatisticalAnalysis.getNGramAnalysis(romeoAndJuliet, "romeoAndJuliet", hamlet, "hamlet", 2, null, settings);
        NGramAnalysis analysisToCompare = new Gson().fromJson("{\"textAName\":\"romeoAndJuliet\",\"textBName\":\"hamlet\",\"nGramResultMap\":{\"if you do\":{\"nGramSize\":3,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"if thou art\":{\"nGramSize\":3,\"appearancesInA\":1,\"appearancesInB\":1},\"to the\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":6},\"of the\":{\"nGramSize\":2,\"appearancesInA\":5,\"appearancesInB\":4}," +
                "\"thou art\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":3},\"if thou\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":3},\"is the\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":4}," +
                "\"of our\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":2},\"do you\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1},\"i have\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":3}," +
                "\"i am\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1},\"take it\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1},\"let us\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":3}" +
                ",\"with the\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":1},\"is to\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":1},\"of my\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2}" +
                ",\"and let\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2},\"from the\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2},\"as you\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\"" +
                ":1},\"you do\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"if you\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"and do\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\"" +
                ":1},\"which is\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"it as\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"piece of\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesIn" +
                "B\":1},\"in what\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"therefore i\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"to be\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearanc" +
                "esInB\":1},\"i strike\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"the\":{\"nGramSize\":1,\"appearancesInA\":20,\"appearancesInB\":65},\"and\":{\"nGramSize\":1,\"appearancesInA\":9,\"appearances" +
                "InB\":52},\"of\":{\"nGramSize\":1,\"appearancesInA\":13,\"appearancesInB\":42},\"to\":{\"nGramSize\":1,\"appearancesInA\":11,\"appearancesInB\":34},\"i\":{\"nGramSize\":1,\"appearancesInA\":20,\"appearancesInB\":17}" +
                ",\"it\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":32},\"our\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":19},\"in\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":19},\"you\":" +
                "{\"nGramSize\":1,\"appearancesInA\":9,\"appearancesInB\":11},\"a\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":12},\"is\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":13},\"that\":{\"nGramSi" +
                "ze\":1,\"appearancesInA\":3,\"appearancesInB\":16},\"not\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":11},\"my\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":7},\"thou\":{\"nGramSize\":1,\"a" +
                "ppearancesInA\":7,\"appearancesInB\":6},\"with\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":11},\"as\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":8},\"sir\":{\"nGramSize\":1,\"appearancesIn" +
                "A\":12,\"appearancesInB\":1},\"if\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":7},\"us\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":8},\"me\":{\"nGramSize\":1,\"appearancesInA\":4,\"appeara" +
                "ncesInB\":8},\"by\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":11},\"do\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":6},\"no\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":5},\"" +
                "will\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":4},\"\\u0027tis\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":8},\"for\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":7},\"we\":{" +
                "\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":7},\"be\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":5},\"but\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":7},\"at\":{\"nGramSize\":1," +
                "\"appearancesInA\":4,\"appearancesInB\":5},\"which\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8},\"what\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8},\"have\":{\"nGramSize\":1,\"appeara" +
                "ncesInA\":1,\"appearancesInB\":8},\"his\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8},\"thee\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":5},\"art\":{\"nGramSize\":1,\"appearancesInA\":3" +
                ",\"appearancesInB\":4},\"they\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":2},\"take\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":2},\"well\":{\"nGramSize\":1,\"appearancesInA\":2,\"appear" +
                "ancesInB\":5},\"let\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":4},\"stand\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":3},\"them\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB" +
                "\":2},\"say\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4},\"comes\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4},\"or\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4},\"then\":" +
                "{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4},\"your\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":2},\"when\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4},\"here\":{\"nGramSi" +
                "ze\":1,\"appearancesInA\":2,\"appearancesInB\":3},\"shall\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":3},\"good\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4},\"therefore\":{\"nGramSize\"" +
                ":1,\"appearancesInA\":3,\"appearancesInB\":1},\"strike\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":2},\"all\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":3},\"am\":{\"nGramSize\":1,\"appeara" +
                "ncesInA\":3,\"appearancesInB\":1},\"thy\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":2},\"being\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"from\":{\"nGramSize\":1,\"appearancesInA\":1" +
                ",\"appearancesInB\":2},\"are\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":2},\"law\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"fear\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearanc" +
                "esInB\":1},\"one\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"any\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":2},\"side\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"" +
                "dare\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"nay\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"list\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"how\":{\"nGr" +
                "amSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"two\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"been\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"piece\":{\"nGramSize\":1,\"" +
                "appearancesInA\":1,\"appearancesInB\":1},\"known\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"off\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"myself\":{\"nGramSize\":1,\"appearancesIn" +
                "A\":1,\"appearancesInB\":1},\"show\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"ever\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"true\":{\"nGramSize\":1,\"appearancesInA\":1,\"appear" +
                "ancesInB\":1},\"goes\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"away\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"valiant\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":" +
                "1},\"stir\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"live\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"an\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1}}}", NGramAnalysis.class);
        assertEquals(analysisToCompare, nGramAnalysis);

        NGramAnalysis wrongAnalysisToCompare = new Gson().fromJson("{\"textAName\":\"romeoAndJuliet\",\"textBName\":\"hamlet\",\"nGramResultMap\":{\"if you do\":{\"nGramSize\":3,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"if thou art\":{\"nGramSize\":3,\"appearancesInA\":1,\"appearancesInB\":1},\"to the\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":6},\"of the\":{\"nGramSize\":2," +
                "\"appearancesInA\":5,\"appearancesInB\":4},\"thou art\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":3},\"if thou\":{\"nGramSize\":2,\"appearancesInA\":2," +
                "\"appearancesInB\":3},\"is the\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":4},\"let us\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":3}," +
                "\"of our\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":2},\"do you\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1}," +
                "\"i have\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":3},\"i am\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1}," +
                "\"take it\":{\"nGramSize\":2,\"appearancesInA\":3,\"appearancesInB\":1},\"with the\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":1}," +
                "\"is to\":{\"nGramSize\":2,\"appearancesInA\":2,\"appearancesInB\":1},\"of my\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2}," +
                "\"and let\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2},\"from the\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":2}," +
                "\"as you\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"you do\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"if you\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"and do\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"which is\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"it as\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"piece of\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"stand and\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"in what\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"therefore i\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"to be\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1},\"i strike\":{\"nGramSize\":2,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"the\":{\"nGramSize\":1,\"appearancesInA\":20,\"appearancesInB\":65},\"and\":{\"nGramSize\":1,\"appearancesInA\":9,\"appearancesInB\":52}," +
                "\"of\":{\"nGramSize\":1,\"appearancesInA\":13,\"appearancesInB\":42},\"to\":{\"nGramSize\":1,\"appearancesInA\":11,\"appearancesInB\":34}," +
                "\"i\":{\"nGramSize\":1,\"appearancesInA\":20,\"appearancesInB\":17},\"it\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":31}," +
                "\"our\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":19},\"in\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":19}," +
                "\"you\":{\"nGramSize\":1,\"appearancesInA\":9,\"appearancesInB\":11},\"a\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":12}," +
                "\"is\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":13},\"that\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":16}," +
                "\"not\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":11},\"my\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":7}," +
                "\"with\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":11}," +
                "\"as\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":8},\"sir\":{\"nGramSize\":1,\"appearancesInA\":12,\"appearancesInB\":1}," +
                "\"if\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":7},\"us\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":8}," +
                "\"me\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":8},\"by\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":11}," +
                "\"do\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":6},\"no\":{\"nGramSize\":1,\"appearancesInA\":6,\"appearancesInB\":5}," +
                "\"will\":{\"nGramSize\":1,\"appearancesInA\":7,\"appearancesInB\":4},\"\\u0027tis\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":8}," +
                "\"for\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":7},\"we\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":7}," +
                "\"be\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":5},\"but\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":7}," +
                "\"at\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":5},\"which\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8}," +
                "\"what\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8},\"have\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8}," +
                "\"his\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":8},\"thee\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":5}," +
                "\"art\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":4},\"they\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":2}," +
                "\"take\":{\"nGramSize\":1,\"appearancesInA\":5,\"appearancesInB\":2},\"well\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":5}," +
                "\"let\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":4},\"stand\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":3}," +
                "\"them\":{\"nGramSize\":1,\"appearancesInA\":4,\"appearancesInB\":2},\"say\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4}," +
                "\"comes\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4},\"or\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":4}," +
                "\"then\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4},\"your\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":2}," +
                "\"when\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4},\"here\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":3}," +
                "\"shall\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":3},\"good\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":4}," +
                "\"therefore\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":1},\"strike\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":2}," +
                "\"all\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":3},\"am\":{\"nGramSize\":1,\"appearancesInA\":3,\"appearancesInB\":1},\"thy\":{\"nGramSize\":1," +
                "\"appearancesInA\":2,\"appearancesInB\":2},\"being\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"from\":{\"nGramSize\":1,\"appearancesInA\":1," +
                "\"appearancesInB\":2},\"are\":{\"nGramSize\":3,\"appearancesInA\":1,\"appearancesInB\":2},\"law\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"" +
                "fear\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"one\":{\"nGramSize\":1,\"appearancesInA\":2,\"appearancesInB\":1},\"any\":{\"nGramSize\":1," +
                "\"appearancesInA\":1,\"appearancesInB\":2},\"side\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"dare\":{\"nGramSize\":1,\"appearancesInA\":1" +
                ",\"appearancesInB\":2},\"nay\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"list\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1}," +
                "\"how\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"two\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"been\":{\"nGramSize\":" +
                "1,\"appearancesInA\":1,\"appearancesInB\":1},\"piece\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"known\":{\"nGramSize\":1,\"appearancesInA\":" +
                "1,\"appearancesInB\":1},\"off\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"myself\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1" +
                "},\"show\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"ever\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"true\":{\"nGramSize\":" +
                "1,\"appearancesInA\":1,\"appearancesInB\":1},\"goes\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"away\":{\"nGramSize\":1,\"appearancesInA\":1," +
                "\"appearancesInB\":1},\"valiant\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"stir\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1}" +
                ",\"live\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1},\"an\":{\"nGramSize\":1,\"appearancesInA\":1,\"appearancesInB\":1}}}", NGramAnalysis.class);
        assertNotEquals(wrongAnalysisToCompare, nGramAnalysis);
    }

    @Test
    public void testNGramCount() throws Exception {
        String text = "a b c d";
        Map<Integer, Integer> nGramCount = StatisticalAnalysis.getNGramCount(text, settings);

        assertEquals(4, nGramCount.get(1));
        assertEquals(3, nGramCount.get(2));
        assertEquals(2, nGramCount.get(3));
        assertEquals(1, nGramCount.get(4));

        text = "b a c d e f g h i j k";
        nGramCount = StatisticalAnalysis.getNGramCount(text, settings);

        assertEquals(11, nGramCount.get(1));
        assertEquals(10, nGramCount.get(2));
        assertEquals(9, nGramCount.get(3));
        assertEquals(8, nGramCount.get(4));

        text = "a b c d. a b d e";
        nGramCount = StatisticalAnalysis.getNGramCount(text, settings);

        assertEquals(8, nGramCount.get(1));
        assertEquals(6, nGramCount.get(2));
        assertEquals(4, nGramCount.get(3));
        assertEquals(2, nGramCount.get(4));

    }


}
