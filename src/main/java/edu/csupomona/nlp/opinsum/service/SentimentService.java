package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Sentence;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface SentimentService {

    /**
     * Train the sentiment analyzer
     */
    public void train();

    /**
     * Classify the list of sentences using sentiment analyzer
     * @param sentences
     */
    public void batchClassify(List<Sentence> sentences);
}
