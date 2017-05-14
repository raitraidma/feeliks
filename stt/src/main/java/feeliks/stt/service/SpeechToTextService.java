package feeliks.stt.service;

import feeliks.stt.model.Text;
import java.io.InputStream;

public interface SpeechToTextService {
    Text batch(InputStream inputStream);
}
