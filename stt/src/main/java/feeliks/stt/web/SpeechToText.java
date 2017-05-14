package feeliks.stt.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import feeliks.stt.Properties;
import feeliks.stt.model.Text;
import feeliks.stt.service.SpeechToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping
public class SpeechToText {
    @Autowired
    SpeechToTextService speechToTextService;

    @RequestMapping(method = {POST, PUT}, path = "/batch", produces = {APPLICATION_JSON_VALUE})
    public Text batch(HttpServletRequest request) {
        try {
            return speechToTextService.batch(request.getInputStream());
        } catch (IOException e) {}
        return new Text("");
    }
}
