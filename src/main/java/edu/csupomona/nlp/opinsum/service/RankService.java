package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Sentence;

import java.util.List;

/**
 * Created by Xing HU on 12/1/14.
 */
public interface RankService {

    /**
     * Rank the list of sentences using SubSum
     * @param sentences
     */
    public void rank(List<Sentence> sentences);
}
