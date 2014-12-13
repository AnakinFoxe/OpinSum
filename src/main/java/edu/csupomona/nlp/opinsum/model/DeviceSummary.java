package edu.csupomona.nlp.opinsum.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xing HU on 12/7/14.
 */
@XmlRootElement
public class DeviceSummary {

    Long id;

    private String name;

    private String thumbnailUrl;

    private String imgUrl;

    private String productId;

    HashMap<String, List<Integer>> aspectSentiment;

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

    public HashMap<String, List<Integer>> getAspectSentiment() {
        return aspectSentiment;
    }

    public void setAspectSentiment(HashMap<String, List<Integer>> aspectSentiment) {
        this.aspectSentiment = aspectSentiment;
    }
}
