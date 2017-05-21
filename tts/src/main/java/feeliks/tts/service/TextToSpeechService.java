package feeliks.tts.service;

import java.io.Closeable;
import java.io.InputStream;

public interface TextToSpeechService extends Closeable {
    InputStream textToSpeech(String text) throws Exception;
}
