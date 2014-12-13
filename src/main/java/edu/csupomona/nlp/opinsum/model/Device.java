package edu.csupomona.nlp.opinsum.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xing HU on 11/6/14.
 */
@Entity
@XmlRootElement
@JsonIgnoreProperties({"sentences"})
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String thumbnailUrl;

    private String imgUrl;

    private String productId;   // for Amazon product

    // TODO: JsonIgnore not working

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private transient List<Sentence> sentences = new ArrayList<>();

    public Device() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonIgnore
    public List<Sentence> getSentences() {
        return sentences;
    }

    @JsonProperty
    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }


}
