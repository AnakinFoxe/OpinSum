package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.SentimentNGram;
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
public class SentimentNGramRepositoryImpl implements SentimentNGramRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public SentimentNGram saveOrUpdate(SentimentNGram nGram) {

        em.persist(nGram);
        em.flush();

        return nGram;
    }

    @Override
    public List<SentimentNGram> loadAll() {
        try {
            String qString = "select g from SentimentNGram g";
            Query query = em.createQuery(qString);
            List ngrams = query.getResultList();

            return ngrams;
        } catch (NoResultException e) {
            return null;
        }    }
}
