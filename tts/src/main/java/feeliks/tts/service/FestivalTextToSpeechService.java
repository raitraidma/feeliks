package feeliks.tts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

import static java.lang.ProcessBuilder.Redirect.INHERIT;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.util.concurrent.TimeUnit.SECONDS;

public class FestivalTextToSpeechService implements TextToSpeechService {
    private static final Logger logger = LoggerFactory.getLogger(FestivalTextToSpeechService.class);
    private static final String FILE_EXTENSION_TEXT = ".text";
    private static final String FILE_EXTENSION_WAV = ".wav";
    private String textToWaveCommand;
    private final int maxProcessTimeInSeconds;
    private final String voice;
    private File textFile;
    private File wavFile;
    private ByteArrayInputStream wavFileStream;

    public FestivalTextToSpeechService(String textToWaveCommand, String voice, int maxProcessTimeInSeconds) {
        this.textToWaveCommand = textToWaveCommand;
        this.maxProcessTimeInSeconds = maxProcessTimeInSeconds;
        this.voice = voice;
    }

    @Override
    public InputStream textToSpeech(String text) throws IOException, InterruptedException {
        String fileName = getRandomString();
        textFile = new File(fileName + FILE_EXTENSION_TEXT);
        wavFile = new File(fileName + FILE_EXTENSION_WAV);
        logger.debug("textFile: " + textFile.getAbsolutePath());
        logger.debug("wavFile: " + wavFile.getAbsolutePath());
        writeTextToFile(text);
        convertTextToWave();
        wavFileStream = new ByteArrayInputStream(Files.readAllBytes(wavFile.toPath()));
        return wavFileStream;
    }

    private void writeTextToFile(String text) throws IOException {
        textFile.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(textFile, false)) {
            fos.write(text.getBytes(ISO_8859_1));
        }
    }

    private boolean convertTextToWave() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(textToWaveCommand, textFile.getAbsolutePath(), "-o", wavFile.getAbsolutePath(), "-eval", voice);
        processBuilder.redirectOutput(INHERIT);
        processBuilder.redirectError(INHERIT);
        logger.info("Text to wave process: " + processBuilder.command());
        Process text2waveProcess = processBuilder.start();
        return text2waveProcess.waitFor(maxProcessTimeInSeconds, SECONDS);
    }

    private String getRandomString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void close() throws IOException {
        if (wavFileStream != null) {
            try {
                wavFileStream.close();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        if (textFile != null) {
            try {
                textFile.delete();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        if (wavFile != null) {
            try {
                wavFile.delete();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
