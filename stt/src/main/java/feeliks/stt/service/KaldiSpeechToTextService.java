package feeliks.stt.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feeliks.stt.model.Text;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

public class KaldiSpeechToTextService implements SpeechToTextService {
    private static final int BUFFER_SIZE = 1024;
    private final String host;
    private final int port;
    private static final Logger logger = LoggerFactory.getLogger(KaldiSpeechToTextService.class);

    public KaldiSpeechToTextService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Text batch(InputStream inputStream) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/client/dynamic/recognize");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod(PUT.name());
            OutputStream httpURLConnectionOutputStream = httpURLConnection.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesInBuffer;
            while ((bytesInBuffer = inputStream.read(buffer)) != -1) {
                httpURLConnectionOutputStream.write(buffer, 0, bytesInBuffer);
            }
            httpURLConnectionOutputStream.close();

            StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(httpURLConnection.getInputStream(), StandardCharsets.UTF_8.name())) {
                response.append(scanner.useDelimiter("\\A").next());
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(response.toString());

            if (responseJson.has("hypotheses") &&
                responseJson.get("hypotheses").has(0) &&
                responseJson.get("hypotheses").get(0).has("utterance")) {
                return new Text(responseJson.get("hypotheses").get(0).get("utterance").asText());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new Text();
    }
}
