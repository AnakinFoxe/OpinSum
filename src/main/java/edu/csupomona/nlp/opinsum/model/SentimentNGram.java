package edu.csupomona.nlp.opinsum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Xing HU on 11/30/14.
 */
@Entity
public class SentimentNGram {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String ngram;

    private Integer posCount;

    private Integer negCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNgram() {
        return ngram;
    }

    public void setNgram(String ngram) {
        this.ngram = ngram;
    }

    public Integer getPosCount() {
        return posCount;
    }

    public void setPosCount(Integer posCount) {
        this.posCount = posCount;
    }

    public Integer getNegCount() {
        return negCount;
    }

    public void setNegCount(Integer negCount) {
        this.negCount = negCount;
    }
}
