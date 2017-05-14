package feeliks.stt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class Properties {
    private String engine;
    private Kaldi kaldi;

    public boolean isKaldiEngine() {
        return "kaldi".equalsIgnoreCase(engine);
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Kaldi getKaldi() {
        return kaldi;
    }

    public void setKaldi(Kaldi kaldi) {
        this.kaldi = kaldi;
    }

    public static class Kaldi {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
