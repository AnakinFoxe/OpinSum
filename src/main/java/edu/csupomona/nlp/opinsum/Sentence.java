package edu.csupomona.nlp.opinsum;

/**
 * Created by Xing HU on 10/28/14.
 */
public class Sentence {
    private String aspect_;
    private String sentiment_;
    private String sentence_;

    public Sentence(String sentence_) {
        this.sentence_ = sentence_;
    }

    public Sentence(String aspect_, String sentence_) {
        this.aspect_ = aspect_;
        this.sentence_ = sentence_;
    }

    public Sentence(String aspect, String sentiment, String sentence) {
        this.aspect_ = aspect;
        this.sentiment_ = sentiment;
        this.sentence_ = sentence;
    }

    public String getAspect_() {
        return aspect_;
    }

    public void setAspect_(String aspect_) {
        this.aspect_ = aspect_;
    }

    public String getSentiment_() {
        return sentiment_;
    }

    public void setSentiment_(String sentiment_) {
        this.sentiment_ = sentiment_;
    }

    public String getSentence_() {
        return sentence_;
    }

    public void setSentence_(String sentence_) {
        this.sentence_ = sentence_;
    }
}
