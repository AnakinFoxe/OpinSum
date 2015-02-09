package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.AspectSentenceCount;
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
public class AspectSCRepositoryImpl implements AspectSCRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AspectSentenceCount saveOrUpdate(AspectSentenceCount asc) {

//        if (asc.getId() != null) {
//            // if exist, update old entry
//            AspectSentenceCount oldAsc;
//            try {
//
//                String qString = "select g from AspectSentenceCount g where g.id = :id";
//                Query query = em.createQuery(qString, AspectSentenceCount.class);
//                query.setParameter("id", asc.getId());
//
//                // do I need to cast?
//                oldAsc =  (AspectSentenceCount) query.getSingleResult();
//            } catch (NoResultException e) {
//                oldAsc = null;
//            }
//
//            // old entry match, update it
//            if (oldAsc != null) {
//
//                return asc;
//            }
//        }

        // if not exist, create new entry
        em.persist(asc);
        em.flush();

        return asc;
    }

    @Override
    public List<AspectSentenceCount> loadAll() {
        try {
            String qString = "select g from AspectSentenceCount g";
            Query query = em.createQuery(qString);
            List ascs = query.getResultList();

            return ascs;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public int deleteAll() {

        String qString = "delete from AspectSentenceCount";
        Query query = em.createQuery(qString);

        return query.executeUpdate();
    }
}
