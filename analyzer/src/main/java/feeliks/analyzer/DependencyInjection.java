package feeliks.analyzer;

import feeliks.analyzer.service.AnalyzerCollection;
import feeliks.analyzer.service.analyzer.QuestionAnalyzer;
import feeliks.analyzer.service.analyzer.TimeAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class DependencyInjection {
    private Properties properties;

    @Autowired
    public DependencyInjection(Properties properties) {
        this.properties = properties;
    }

    @Bean
    AnalyzerCollection getAnalyzerCollection() {
        AnalyzerCollection analyzerCollection = new AnalyzerCollection();
        analyzerCollection.addAnalyzer(new TimeAnalyzer(10, properties));
        analyzerCollection.addAnalyzer(new QuestionAnalyzer(20, properties));
        return analyzerCollection;
    }
}
