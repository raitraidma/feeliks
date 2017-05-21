package feeliks.analyzer.service.analyzer;

import feeliks.analyzer.AnalyzerResponse;
import feeliks.analyzer.Properties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAnalyzer extends Analyzer {
    private static final String PATTERN = "(.*?)mis (kell|(kuu)?päev|aasta)\\s?(praegu|täna|homme)? on(.*?)";
    private static final String TIME_FORMAT = "HH mm";
    private static final String YEAR_FORMAT = "YYYY";

    public TimeAnalyzer(int priority, Properties properties) {
        super(priority, properties);
    }

    @Override
    public boolean belongsToAnalyzer(String textToAnalyze) {
        return textToAnalyze.toLowerCase().matches(PATTERN);
    }

    @Override
    public AnalyzerResponse analyze(String textToAnalyze) {
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        StringBuilder response = new StringBuilder();
        response.append("Kell on ");
        response.append(timeFormat.format(new Date()));
        return new AnalyzerResponse().setResponse(response.toString());
    }
}
