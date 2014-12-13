package edu.csupomona.nlp.opinsum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Xing HU on 11/30/14.
 */
@Entity
public class AspectNGram {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String ngram;

    @NotNull
    private Integer aspect1;
    private Integer aspect2;
    private Integer aspect3;
    private Integer aspect4;
    private Integer aspect5;
    private Integer aspect6;
    private Integer aspect7;
    private Integer aspect8;
    private Integer aspect9;

    private Integer other;

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

    public Integer getAspect1() {
        return aspect1;
    }

    public void setAspect1(Integer aspect1) {
        this.aspect1 = aspect1;
    }

    public Integer getAspect2() {
        return aspect2;
    }

    public void setAspect2(Integer aspect2) {
        this.aspect2 = aspect2;
    }

    public Integer getAspect3() {
        return aspect3;
    }

    public void setAspect3(Integer aspect3) {
        this.aspect3 = aspect3;
    }

    public Integer getAspect4() {
        return aspect4;
    }

    public void setAspect4(Integer aspect4) {
        this.aspect4 = aspect4;
    }

    public Integer getAspect5() {
        return aspect5;
    }

    public void setAspect5(Integer aspect5) {
        this.aspect5 = aspect5;
    }

    public Integer getAspect6() {
        return aspect6;
    }

    public void setAspect6(Integer aspect6) {
        this.aspect6 = aspect6;
    }

    public Integer getAspect7() {
        return aspect7;
    }

    public void setAspect7(Integer aspect7) {
        this.aspect7 = aspect7;
    }

    public Integer getAspect8() {
        return aspect8;
    }

    public void setAspect8(Integer aspect8) {
        this.aspect8 = aspect8;
    }

    public Integer getAspect9() {
        return aspect9;
    }

    public void setAspect9(Integer aspect9) {
        this.aspect9 = aspect9;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
