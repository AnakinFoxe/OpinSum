package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.AspectSentenceCount;

import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public interface AspectSCRepository {

    public AspectSentenceCount saveOrUpdate(AspectSentenceCount asc);

    public List<AspectSentenceCount> loadAll();

    public int deleteAll();
}
