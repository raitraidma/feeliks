package feeliks.analyzer.web;

import feeliks.analyzer.AnalyzerResponse;
import feeliks.analyzer.service.AnalyzerCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Analyze {
    private AnalyzerCollection analyzer;

    @Autowired
    public Analyze(AnalyzerCollection analyzer) {
        this.analyzer = analyzer;
    }

    @RequestMapping(method = {GET}, produces = {APPLICATION_JSON_VALUE})
    public AnalyzerResponse analyze(@RequestParam("text") String textToAnalyze) {
        return analyzer.analyze(textToAnalyze);
    }
}
