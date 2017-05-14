package feeliks.stt;

import feeliks.stt.service.KaldiSpeechToTextService;
import feeliks.stt.service.SpeechToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class DependencyInjection {
    @Autowired
    Properties properties;

    @Bean
    SpeechToTextService getSpeechToTextService() {
        if (properties.isKaldiEngine()) {
            return new KaldiSpeechToTextService(properties.getKaldi().getHost(), properties.getKaldi().getPort());
        }
        return null;
    }
}
