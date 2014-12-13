package edu.csupomona.nlp.opinsum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Xing HU on 11/30/14.
 */
@Entity
public class AspectSentenceCount {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String aspect;

    @NotNull
    private Long count;

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
