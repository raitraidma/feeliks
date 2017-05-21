package feeliks.analyzer.service;

import feeliks.analyzer.AnalyzerResponse;
import feeliks.analyzer.service.analyzer.Analyzer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AnalyzerCollection {
    private List<Analyzer> analyzers = new LinkedList<Analyzer>();

    public void addAnalyzer(Analyzer analyzer) {
        analyzers.add(analyzer);
        Collections.sort(analyzers);
    }

    public AnalyzerResponse analyze(String textToAnalyze) {
        for (Analyzer analyzer : analyzers) {
            if (analyzer.belongsToAnalyzer(textToAnalyze)) {
                return analyzer.analyze(textToAnalyze);
            }
        }
        return new AnalyzerResponse().setResponse("Kahjuks ei oska vastata.");
    }
}
