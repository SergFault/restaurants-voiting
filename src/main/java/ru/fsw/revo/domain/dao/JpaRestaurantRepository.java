package ru.fsw.revo.domain.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fsw.revo.domain.model.MenuItem;
import ru.fsw.revo.domain.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

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

    @Transactional
    public boolean updateMenu(List<MenuItem> menuItems, long rId) {
        Restaurant restaurantReference = em.find(Restaurant.class, rId);
        em.createNamedQuery(MenuItem.CLEAN_FOR_TODAY).setParameter("today", new Date()).executeUpdate(); //if menu already exists for today
        for (MenuItem menuItem : menuItems) {
            menuItem.setRestaurant(restaurantReference);
            em.persist(menuItem);
        }
        return true;
    }


    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.GET_ALL).getResultList();
    }
}
