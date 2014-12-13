package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.SentimentCount;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface SentimentCountRepository {

    public SentimentCount saveOrUpdate(SentimentCount count);

    public List<SentimentCount> loadAll();
}
