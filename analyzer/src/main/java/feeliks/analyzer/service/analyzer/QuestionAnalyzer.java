package feeliks.analyzer.service.analyzer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feeliks.analyzer.AnalyzerResponse;
import feeliks.analyzer.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QuestionAnalyzer extends Analyzer {
    private static final Logger logger = LoggerFactory.getLogger(QuestionAnalyzer.class);
    private static final String PATTERN = "(.*?)(mis|kes) on(.*?)";
    public QuestionAnalyzer(int priority, Properties properties) {
        super(priority, properties);
    }

    @Override
    public boolean belongsToAnalyzer(String textToAnalyze) {
        return textToAnalyze.toLowerCase().matches(PATTERN);
    }

    @Override
    public AnalyzerResponse analyze(String textToAnalyze) {
        AnalyzerResponse analyzerResponse = new AnalyzerResponse();
        try {
            String text = textToAnalyze.toLowerCase();
            String[] textParts = text.split("(mis|kes) on", 2);
            if (textParts.length == 2) {
                String title = Arrays.stream(textParts[1].trim().split(" "))
                        .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                        .collect(Collectors.joining("_"));
                String response = query(getProperties().getWikiUrl() + title);
                JsonNode jsonResponse = new ObjectMapper().readTree(response);
                analyzerResponse.setResponse(getFirstSentence(jsonResponse));
            }
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return analyzerResponse;
    }

    private String getFirstSentence(JsonNode jsonResponse) {
        String firstSentence = "";

        try {
            String xml = jsonResponse.get("query").get("export").get("*").textValue();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/mediawiki/page/revision/text/text()");
            String text = (String) expr.evaluate(document, XPathConstants.STRING);
            text = stripWikiTags(text);
            firstSentence = text.split("\\. ")[0];
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            logger.warn(e.getMessage(), e);
        }
        return firstSentence;
    }

    String stripWikiTags(String text) {
        return text.replaceAll("\\{\\{.+\\}\\}", "")
                   .replaceAll("('''|'')", "")
                   .replaceAll("\\[\\[[^\\]]+\\|([^\\]]+)\\]\\]", "$1")
                   .replaceAll("(\\[\\[|\\]\\])", "")
                   .replaceAll("\\(.+?\\)", "")
                   .replaceAll("\\s+", " ")
                   .trim();
    }

    private String query(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
}
