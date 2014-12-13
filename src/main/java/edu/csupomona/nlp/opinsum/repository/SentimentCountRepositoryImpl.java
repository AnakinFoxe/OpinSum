package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.SentimentCount;
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
public class SentimentCountRepositoryImpl implements SentimentCountRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public SentimentCount saveOrUpdate(SentimentCount count) {
        em.persist(count);
        em.flush();

        return count;
    }

    @Override
    public List<SentimentCount> loadAll() {
        try {
            String qString = "select g from SentimentCount g";
            Query query = em.createQuery(qString);
            List counts = query.getResultList();

            return counts;
        } catch (NoResultException e) {
            return null;
        }    }
}
