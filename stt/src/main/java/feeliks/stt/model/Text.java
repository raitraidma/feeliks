package feeliks.stt.model;

public class Text {
    private String utterance;

    public Text() {
        this("");
    }

    public Text(String utterance) {
        this.utterance = utterance;
    }

    public String getUtterance() {
        return utterance;
    }
}