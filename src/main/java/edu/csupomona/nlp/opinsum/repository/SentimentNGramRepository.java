package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.SentimentNGram;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface SentimentNGramRepository {

    public SentimentNGram saveOrUpdate(SentimentNGram nGram);

    public List<SentimentNGram> loadAll();
}
