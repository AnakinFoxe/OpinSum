package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.AspectNGram;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface AspectNGramRepository {

    public AspectNGram saveOrUpdate(AspectNGram ngram);

    public List<AspectNGram> loadAll();

    public int deleteAll();
}
