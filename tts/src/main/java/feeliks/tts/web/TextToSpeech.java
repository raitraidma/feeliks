package feeliks.tts.web;

import feeliks.tts.service.FestivalTextToSpeechService;
import feeliks.tts.service.TextToSpeechService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping
public class TextToSpeech {
    private static final int BUFFER_SIZE = 1024;
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String AUDIO_WAV_MEDIA_TYPE = "audio/wav";
    private static final String TEXT_TO_WAVE_COMMAND = "/festival/bin/text2wave";
    private static final String VOICE = "(voice_eki_et_riina_clunits)";
    private static final int MAX_PROCESS_TIME_IN_SECONDS = 60;

    @RequestMapping(method = {GET})
    public void batch(@RequestParam("text") String text,
                      HttpServletResponse response
    ) throws Exception {
        try(TextToSpeechService textToSpeechService = new FestivalTextToSpeechService(TEXT_TO_WAVE_COMMAND, VOICE, MAX_PROCESS_TIME_IN_SECONDS)) {
            response.setHeader(CONTENT_TYPE_HEADER, AUDIO_WAV_MEDIA_TYPE);

            InputStream wavStream = textToSpeechService.textToSpeech(text);
            try(ServletOutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesInBuffer;
                while ((bytesInBuffer = wavStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesInBuffer);
                }
            }
        }
    }
}
