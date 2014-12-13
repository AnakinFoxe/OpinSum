package edu.csupomona.nlp.opinsum.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Xing HU on 12/9/14.
 */
@XmlRootElement
public class AspectSentiment {
    private String name;

    private List<Integer> sentiment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSentiment() {
        return sentiment;
    }

    public void setSentiment(List<Integer> sentiment) {
        this.sentiment = sentiment;
    }
}
