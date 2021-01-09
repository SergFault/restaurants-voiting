package ru.fsw.revo.domain.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fsw.revo.domain.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public Restaurant get(long rId) {
        Query query = em.createNamedQuery(Restaurant.GET)
                .setParameter("rId", rId);
        return (Restaurant) DataAccessUtils.singleResult(query.getResultList());
    }

    @Transactional
    public Restaurant save(Restaurant rest) {
        if (rest.isNew()) {
            em.persist(rest);
            return rest;
        }
        return em.merge(rest);
    }
}
