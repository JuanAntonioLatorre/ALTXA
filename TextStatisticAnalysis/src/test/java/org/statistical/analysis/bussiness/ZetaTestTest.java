package org.statistical.analysis.bussiness;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.statistical.analysis.business.StatisticalAnalysis;
import org.statistical.analysis.pojo.ProcessedText;
import org.statistical.analysis.pojo.Settings;
import org.statistical.analysis.pojo.ZetaTestXYItem;
import org.statistical.analysis.utils.IOUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.statistical.analysis.business.StatisticalAnalysis.separateAndProcessTexts;
import static org.statistical.analysis.utils.IOUtils.getKeywordList;

public class ZetaTestTest {
    Settings settings = new Settings(true, true, "([A-Z]{2,})");

    @Test
    public void testMarkersSimple() throws Exception {
        String a = "a a b a c a a a r";
        String b = "c c b c a c c c t";

        ProcessedText processedTextA = separateAndProcessTexts(a, true, settings);
        ProcessedText processedTextB = separateAndProcessTexts(b, true, settings);

        List<String> markersB = StatisticalAnalysis.getMarkers(processedTextB, processedTextA, new ArrayList<>());
        List<String> markersA = StatisticalAnalysis.getMarkers(processedTextA, processedTextB, new ArrayList<>());

        assertTrue(markersB.size() == 1 && markersB.get(0).equals("t"));
        assertTrue(markersA.size() == 1 && markersA.get(0).equals("r"));
    }

    @Test
    public void testMarkersWithFragments() throws Exception {
        String a = "m n b a c a a a r @#@ m a b a c a a a r@#@ c c b c a c c c r";
        String b = "x v b c a c c c t @#@ x c b c a c c c t@#@ c c b c a c c c t";

        ProcessedText processedTextA = separateAndProcessTexts(a, true, settings);
        ProcessedText processedTextB = separateAndProcessTexts(b, true, settings);

        List<String> markersB = StatisticalAnalysis.getMarkers(processedTextB, processedTextA, new ArrayList<>());
        List<String> markersA = StatisticalAnalysis.getMarkers(processedTextA, processedTextB, new ArrayList<>());

        assertTrue(markersA.size() == 3 && markersA.get(0).equals("r") && markersA.get(1).equals("m") && markersA.get(2).equals("n"));
        assertTrue(markersB.size() == 3 && markersB.get(0).equals("t") && markersB.get(1).equals("x") && markersB.get(2).equals("v"));
    }

    @Test
    public void testMarkersReal() throws Exception {
        String romeoAndJuliet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/RomeoAndJuliet.txt")).toURI()));
        String hamlet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/Hamlet.txt")).toURI()));

        ProcessedText processedTextRomeo = separateAndProcessTexts(romeoAndJuliet, true, settings);
        ProcessedText processedTextHamlet = separateAndProcessTexts(hamlet, true, settings);
        List<String> ignoredWordList = getKeywordList(Objects.requireNonNull(this.getClass().getResource("/IgnoredWords.txt")).getPath());

        List<String> hamletMarkers = StatisticalAnalysis.getMarkers(processedTextHamlet, processedTextRomeo, ignoredWordList);
        List<String> romeoMarkers = StatisticalAnalysis.getMarkers(processedTextRomeo, processedTextHamlet, ignoredWordList);

        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();

        String hamletMarkersGson = "[\"conveniently\",\"find\",\"morning\",\"pray\",\"do\\u0027t\",\"let\\u0027s\",\"duty\",\"fitting\",\"loves\",\"needful\",\"acquaint\",\"consent\",\"dumb\",\"impart\",\"advice\",\"hill\",\"eastward\",\"yon\",\"dew\",\"o\\u0027er\",\"walks\",\"clad\",\"mantle\",\"russet\",\"gracious\",\"hallow\\u0027d\",\"charm\",\"power\",\"witch\",\"takes\",\"fairy\",\"planets\",\"wholesome\",\"abroad\",\"singeth\",\"dawning\",\"bird\",\"celebrated\",\"birth\",\"saviour\\u0027s\",\"season\",\"\\u0027gainst\",\"crowing\",\"faded\",\"probation\",\"object\",\"present\",\"truth\",\"confine\",\"hies\",\"spirit\",\"erring\",\"extravagant\",\"sea\",\"warning\",\"awake\",\"throat\",\"shrill-sounding\",\"lofty\",\"morn\",\"trumpet\",\"heard\",\"summons\",\"fearful\",\"guilty\",\"started\",\"crew\",\"cock\",\"mockery\",\"malicious\",\"blows\",\"vain\",\"invulnerable\",\"air\",\"violence\",\"offer\",\"majestical\",\"wrong\",\"partisan\",\"stop\",\"death\",\"walk\",\"oft\",\"spirits\",\"womb\",\"treasure\",\"extorted\",\"uphoarded\",\"avoid\",\"foreknowing\",\"happily\",\"fate\",\"country\\u0027s\",\"privy\",\"race\",\"ease\",\"voice\",\"use\",\"sound\",\"illusion\",\"blast\",\"cross\",\"i\\u0027ll\",\"lo\",\"behold\",\"soft\",\"countrymen\",\"climature\",\"demonstrated\",\"earth\",\"coming\",\"omen\",\"prologue\",\"fates\",\"preceding\",\"harbingers\",\"events\",\"fierce\",\"precurse\",\"eclipse\",\"doomsday\",\"stands\",\"empire\",\"neptune\\u0027s\",\"influence\",\"moist\",\"sun\",\"disasters\",\"blood\",\"dews\",\"fire\",\"trains\",\"stars\",\"streets\",\"roman\",\"gibber\",\"squeak\",\"sheeted\",\"tenantless\",\"graves\",\"julius\",\"mightiest\",\"ere\",\"little\",\"palmy\",\"high\",\"eye\",\"mind\\u0027s\",\"trouble\",\"mote\",\"wars\",\"armed\",\"portentous\",\"sort\",\"e\\u0027en\",\"romage\",\"post-haste\",\"head\",\"chief\",\"source\",\"preparations\",\"motive\",\"main\",\"lost\",\"father\",\"foresaid\",\"compulsatory\",\"terms\",\"hand\",\"strong\",\"recover\",\"unto\",\"in\\u0027t\",\"stomach\",\"enterprise\",\"diet\",\"food\",\"resolutes\",\"lawless\",\"shark\\u0027d\",\"skirts\",\"full\",\"hot\",\"mettle\",\"unimproved\",\"young\",\"fell\",\"design\\u0027d\",\"article\",\"carriage\",\"cov\\u0027nant\",\"vanquisher\",\"inheritance\",\"return\\u0027d\",\"gaged\",\"competent\",\"moiety\",\"conqueror\",\"seiz\\u0027d\",\"stood\",\"lands\",\"life\",\"forfeit\",\"heraldry\",\"ratified\",\"compact\",\"seal\\u0027d\",\"slay\",\"esteem\\u0027d\",\"world\",\"hamlet\",\"combat\",\"dar\\u0027d\",\"pride\",\"emulate\",\"prick\\u0027d\",\"thereto\",\"fortinbras\",\"image\",\"whisper\",\"inform\",\"is\\u0027t\",\"day\",\"joint-labourer\",\"sweaty\",\"week\",\"sunday\",\"divide\",\"task\",\"sore\",\"shipwrights\",\"impress\",\"war\",\"implements\",\"mart\",\"foreign\",\"cannon\",\"brazen\",\"cast\",\"daily\",\"land\",\"subject\",\"toils\",\"nightly\",\"observant\",\"strict\",\"knows\",\"tell\",\"state\",\"eruption\",\"bodes\",\"opinion\",\"scope\",\"gross\",\"know\",\"work\",\"thought\",\"particular\",\"stalk\",\"martial\",\"jump\",\"strange\",\"ice\",\"polacks\",\"sledded\",\"smote\",\"parle\",\"angry\",\"frown\\u0027d\",\"combated\",\"norway\",\"ambitious\",\"armour\",\"thyself\",\"own\",\"avouch\",\"sensible\",\"believe\",\"god\",\"on\\u0027t\",\"pale\",\"tremble\",\"gone\",\"stay\",\"stalks\",\"see\",\"offended\",\"charge\",\"march\",\"denmark\",\"buried\",\"majesty\",\"form\",\"warlike\",\"fair\",\"time\",\"usurp\\u0027st\",\"question\",\"spoke\",\"wonder\",\"harrows\",\"mark\",\"looks\",\"scholar\",\"dead\",\"like\",\"figure\",\"look\",\"break\",\"peace\",\"beating\",\"bell\",\"burns\",\"heaven\",\"part\",\"illume\",\"course\",\"made\",\"pole\",\"westward\",\"that\\u0027s\",\"star\",\"yond\",\"nights\",\"story\",\"fortified\",\"ears\",\"assail\",\"awhile\",\"sit\",\"appear\",\"\\u0027twill\",\"tush\",\"speak\",\"eyes\",\"approve\",\"apparition\",\"minutes\",\"entreated\",\"twice\",\"sight\",\"dreaded\",\"touching\",\"hold\",\"belief\",\"fantasy\",\"says\",\"seen\",\"to-night\",\"appear\\u0027d\",\"thing\",\"welcome\",\"holla\",\"place\",\"reliev\\u0027d\",\"soldier\",\"honest\",\"farewell\",\"o\",\"good-night\",\"give\",\"dane\",\"liegemen\",\"mar\",\"ground\",\"friends\",\"ho\",\"hear\",\"think\",\"haste\",\"make\",\"bid\",\"watch\",\"rivals\",\"marcellus\",\"horatio\",\"meet\",\"night\",\"stirring\",\"mouse\",\"guard\",\"quiet\",\"heart\",\"sick\",\"cold\",\"bitter\",\"thanks\",\"relief\",\"francisco\",\"bed\",\"get\",\"twelve\",\"struck\",\"hour\",\"carefully\",\"come\",\"bernardo\",\"king\",\"long\",\"unfold\",\"answer\",\"who\\u0027s\"]";
        String romeoMarkersGson = "[\"lie\",\"kinsmen\",\"master\\u0027s\",\"\\u0027better\",\"better\",\"serve\",\"enter\",\"bear\",\"disgrace\",\"thumb\",\"bite\",\"pass\",\"frown\",\"begin\",\"sides\",\"marry\",\"run\",\"turn\",\"back\",\"weapon\",\"naked\",\"montagues\",\"tool\",\"poor\",\"fish\",\"flesh\",\"pretty\",\"able\",\"feel\",\"sense\",\"maidenheads\",\"heads\",\"cut\",\"cruel\",\"fought\",\"tyrant\",\"masters\",\"quarrel\",\"maids\",\"men\",\"push\",\"thrust\",\"vessels\",\"weaker\",\"women\",\"weakest\",\"slave\",\"weak\",\"shows\",\"montague\\u0027s\",\"maid\",\"man\",\"wall\",\"runn\\u0027st\",\"move\",\"moves\",\"montague\",\"house\",\"dog\",\"moved\",\"quickly\",\"collar\",\"neck\",\"ay\",\"draw\",\"choler\",\"mean\",\"colliers\",\"coals\",\"carry\",\"we\\u0027ll\",\"word\",\"o\\u0027\",\"gregory\"]";

        List<String> hamletMarkersControl = new Gson().fromJson(hamletMarkersGson, listType);
        List<String> romeoMarkersControl = new Gson().fromJson(romeoMarkersGson, listType);

        assertEquals(hamletMarkersControl, hamletMarkers);
        assertEquals(romeoMarkersControl, romeoMarkers);
    }

    @Test
    public void testZTestResultsSimple() throws Exception {
        String a = "m n b a c a a a r @#@ m a b a c a a a r@#@ c c b c a c c c r";
        String b = "x v b c a c c c t @#@ x c b c a c c c t@#@ c c b c a c c c t";

        ProcessedText processedTextA = separateAndProcessTexts(a, true, settings);
        ProcessedText processedTextB = separateAndProcessTexts(b, true, settings);

        List<String> markersB = StatisticalAnalysis.getMarkers(processedTextB, processedTextA, new ArrayList<>());
        List<String> markersA = StatisticalAnalysis.getMarkers(processedTextA, processedTextB, new ArrayList<>());

        List<ZetaTestXYItem> zetaTestResultA = StatisticalAnalysis.getZetaTestResult(processedTextA, markersA, markersB);
        List<ZetaTestXYItem> zetaTestResultB = StatisticalAnalysis.getZetaTestResult(processedTextB, markersB, markersA);

        assertTrue(zetaTestResultA.size() == 3 && zetaTestResultA.get(0).getPercentageAuthorA() == 0.5D && zetaTestResultA.get(0).getPercentageAuthorB() == 0.0D
                && zetaTestResultA.get(1).getPercentageAuthorA() == 0.4D && zetaTestResultA.get(1).getPercentageAuthorB() == 0.0D
                && zetaTestResultA.get(2).getPercentageAuthorA() == 0.25D && zetaTestResultA.get(2).getPercentageAuthorB() == 0.0D);

        assertTrue(zetaTestResultB.size() == 3 && zetaTestResultB.get(0).getPercentageAuthorA() == 0.5D && zetaTestResultB.get(0).getPercentageAuthorB() == 0.0D
                && zetaTestResultB.get(1).getPercentageAuthorA() == 0.4D && zetaTestResultB.get(1).getPercentageAuthorB() == 0.0D
                && zetaTestResultB.get(2).getPercentageAuthorA() == 0.25D && zetaTestResultB.get(2).getPercentageAuthorB() == 0.0D);
    }

    @Test
    public void testZTestResultsReal() throws Exception {
        String romeoAndJuliet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/RomeoAndJuliet.txt")).toURI()));
        String hamlet = IOUtils.readTextFile(new File(Objects.requireNonNull(this.getClass().getResource("/Hamlet.txt")).toURI()));

        ProcessedText processedTextRomeo = separateAndProcessTexts(romeoAndJuliet, true, settings);
        ProcessedText processedTextHamlet = separateAndProcessTexts(hamlet, true, settings);
        List<String> ignoredWordList = getKeywordList(Objects.requireNonNull(this.getClass().getResource("/IgnoredWords.txt")).getPath());

        List<String> hamletMarkers = StatisticalAnalysis.getMarkers(processedTextHamlet, processedTextRomeo, ignoredWordList);
        List<String> romeoMarkers = StatisticalAnalysis.getMarkers(processedTextRomeo, processedTextHamlet, ignoredWordList);

        List<ZetaTestXYItem> zetaTestResultHamlet = StatisticalAnalysis.getZetaTestResult(processedTextHamlet, hamletMarkers, romeoMarkers);
        List<ZetaTestXYItem> zetaTestResultRomeo = StatisticalAnalysis.getZetaTestResult(processedTextRomeo, romeoMarkers, hamletMarkers);

        assertTrue(zetaTestResultHamlet.size() == 1 && zetaTestResultHamlet.get(0).getPercentageAuthorA() == 0.7295825771324864D && zetaTestResultHamlet.get(0).getPercentageAuthorB() == 0.0D);
        assertTrue(zetaTestResultRomeo.size() == 1 && zetaTestResultRomeo.get(0).getPercentageAuthorA() == 0.44047619047619047D && zetaTestResultRomeo.get(0).getPercentageAuthorB() == 0.0D);
    }

}
