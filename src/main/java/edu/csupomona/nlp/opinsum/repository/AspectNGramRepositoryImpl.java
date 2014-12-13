package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.AspectNGram;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
@Repository
public class AspectNGramRepositoryImpl implements AspectNGramRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public AspectNGram saveOrUpdate(AspectNGram ngram) {

        // currently only implements save
        em.persist(ngram);
        em.flush();

        return ngram;
    }

    @Override
    public List<AspectNGram> loadAll() {
        try {
            String qString = "select g from AspectNGram g";
            Query query = em.createQuery(qString);
            List ngrams = query.getResultList();

            return ngrams;
        } catch (NoResultException e) {
            return null;
        }
    }
}
