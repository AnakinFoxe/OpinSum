package edu.csupomona.nlp.opinsum.service;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface CrawlerService {

    /**
     * Crawl the device using its product id
     * @param productId
     */
    public void crawl(String productId);
}
