package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
@Repository
public class SentenceRepositoryImpl implements SentenceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Sentence save(Sentence sentence) {

        em.persist(sentence);
        em.flush();

        return sentence;
    }

    @Override
    public Sentence update(Sentence sentence) {

        em.merge(sentence);
        em.flush();

        return sentence;
    }

    @Override
    public List<Sentence> loadAllByDevice(Device device) {
        try {
            String qString = "select g from Sentence g where g.device = :device";
            Query query = em.createQuery(qString, Sentence.class);
            query.setParameter("device", device);

            return query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Sentence> loadAll() {
        Query query = em.createQuery("select g from Sentence g");
        List sentences = query.getResultList();

        return sentences;
    }
}
