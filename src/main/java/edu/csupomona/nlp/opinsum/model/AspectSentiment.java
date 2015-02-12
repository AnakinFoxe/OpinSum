package edu.csupomona.nlp.opinsum.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Xing HU on 12/9/14.
 */
@XmlRootElement
public class AspectSentiment {
    private String name;

    // counts of polarities
    private List<Integer> sentimentCounts;

    // percentage of polarities
    private List<Integer> sentiment;

    // summaries
    private List<List<String>> summaries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSentimentCounts() {
        return sentimentCounts;
    }

    public void setSentimentCounts(List<Integer> sentimentCounts) {
        this.sentimentCounts = sentimentCounts;
    }

    public List<Integer> getSentiment() {
        return sentiment;
    }

    public void setSentiment(List<Integer> sentiment) {
        this.sentiment = sentiment;
    }

    public List<List<String>> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<List<String>> summaries) {
        this.summaries = summaries;
    }
}
