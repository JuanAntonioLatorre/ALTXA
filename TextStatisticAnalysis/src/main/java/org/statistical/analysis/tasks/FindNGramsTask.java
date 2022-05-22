package org.statistical.analysis.tasks;

import org.statistical.analysis.pojo.NGramAnalysis;
import org.statistical.analysis.pojo.NGramResult;
import org.statistical.analysis.pojo.ProcessedText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.statistical.analysis.utils.AnalysisUtils.getNGramResult;

public class FindNGramsTask implements Runnable {

    private static int counter;
    private List<List<String>> NGramsToSearch;
    private ProcessedText processedText;
    private ProcessedText processedTextToCompare;
    private NGramAnalysis nGramAnalysis;

    public FindNGramsTask(List<List<String>> NGramsToSearch, ProcessedText processedText,
                          ProcessedText processedTextToCompare, NGramAnalysis nGramAnalysis) {
        this.NGramsToSearch = NGramsToSearch;
        this.processedText = processedText;
        this.processedTextToCompare = processedTextToCompare;
        this.nGramAnalysis = nGramAnalysis;
    }

    @Override
    public void run() {
        for (List<String> sentence : NGramsToSearch) {
            for (int i = 0; i < sentence.size(); i++) {
                String word = sentence.get(i);
                List<String> nGram = new ArrayList<>(Collections.singletonList(word));
                boolean previousNGramExisted = true;
                for (int j = i; j < sentence.size(); j++) {
                    if (previousNGramExisted) {
                        String extraWord = sentence.get(j);
                        if (i != j)
                            nGram.add(extraWord);
                        String key = String.join(" ", nGram);
                        if (!nGramAnalysis.nGramResultMapContains(key)) {
                            NGramResult nGramResult = getNGramResult(nGram, processedText, processedTextToCompare);
                            if (nGramResult.getAppearancesInB() > 0) {
                                nGramAnalysis.putNGramResultOnMap(key, nGramResult);
                            } else {
                                previousNGramExisted = false;
                            }
                        }
                    }
                }
            }
            incrementCounter();
        }
    }

    public List<List<String>> getNGramsToSearch() {
        return NGramsToSearch;
    }

    public void setNGramsToSearch(List<List<String>> NGramsToSearch) {
        this.NGramsToSearch = NGramsToSearch;
    }

    public static int getCounter() {
        return counter;
    }

    public static int setCounterZero() {
        return counter = 0;
    }

    public static synchronized void incrementCounter() {
        FindNGramsTask.counter++;
    }
}
