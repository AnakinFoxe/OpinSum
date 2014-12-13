package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Sentence;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface AspectService {

    /**
     * Train the aspect identification classifier
     * @param aspects
     */
    public void train(List<String> aspects);

    /**
     * Classify the list of sentences using aspect identification classifier
     * @param sentences
     */
    public void batchClassify(List<Sentence> sentences);
}
