package edu.csupomona.nlp.opinsum.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Xing HU on 11/24/14.
 */
@Entity
@XmlRootElement
@JsonIgnoreProperties({"device"})
public class Sentence {

    @Id
    @GeneratedValue
    private Long id;

    private String aspect;

    private String sentiment;

    @NotNull
    @Size(min=1, max=2048)
    private String origSent;

    @Size(min=1, max=2048)
    private String procSent;

    private Integer rank;

    @ManyToOne
    private Device device;


    public Sentence() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getOrigSent() {
        return origSent;
    }

    public void setOrigSent(String origSent) {
        this.origSent = origSent;
    }

    public String getProcSent() {
        return procSent;
    }

    public void setProcSent(String sentence) {
        this.procSent = sentence;
    }

    @JsonIgnore
    public Device getDevice() {
        return device;
    }

    @JsonProperty
    public void setDevice(Device device) {
        this.device = device;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
