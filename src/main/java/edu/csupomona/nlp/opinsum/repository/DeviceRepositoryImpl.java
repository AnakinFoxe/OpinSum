package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.Device;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Device save(Device device) {

        em.persist(device);
        em.flush();

        return device;
    }

    @Override
    public Device loadById(Long id) {

        try {
            String qString = "select g from Device g where g.id = :id";
            Query query = em.createQuery(qString, Device.class);
            query.setParameter("id", id);

            // do I need to cast?
            return (Device) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Device loadByProductId(String productId) {
        try {
            String qString = "select g from Device g where g.productId = :productId";
            Query query = em.createQuery(qString, Device.class);
            query.setParameter("productId", productId);

            // do I need to cast?
            return (Device) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Device> loadAll() {
        try {
            String qString = "select g from Device g";
            Query query = em.createQuery(qString);
            List devices = query.getResultList();

            return devices;
        } catch (NoResultException e) {
            return null;
        }
    }
}
