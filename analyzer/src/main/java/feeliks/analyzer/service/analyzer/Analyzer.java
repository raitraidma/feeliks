package feeliks.analyzer.service.analyzer;

import feeliks.analyzer.AnalyzerResponse;
import feeliks.analyzer.Properties;

public abstract class Analyzer implements Comparable<Analyzer> {
    private int priority;
    private Properties properties;

    public Analyzer(int priority, Properties properties) {
        this.priority = priority;
        this.properties = properties;
    }

    public int getPriority() {
        return priority;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public int compareTo(Analyzer otherAnalyzer) {
        return this.getPriority() - otherAnalyzer.getPriority();
    }

    public abstract boolean belongsToAnalyzer(String textToAnalyze);

    public abstract AnalyzerResponse analyze(String textToAnalyze);
}
