package ru.fsw.revo.domain.dao;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.fsw.revo.domain.model.User;
import ru.fsw.revo.domain.model.Vote;
import ru.fsw.revo.utils.exception.VotePerDayException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

import static ru.fsw.revo.utils.DateTimeUtil.*;

@Repository
@Transactional(readOnly = true)
public class JpaVoteRepository implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Vote save(Vote vote, long userId) {
        vote.setUser(em.find(User.class, userId));
        if (vote.isNew()) {
//            check vote today for restaurant already exists (extra 1 query to db)
            LocalDateTime now = LocalDateTime.now();
            if (!em.createNamedQuery(Vote.GET_FOR_REST_BETWEEN_DATES)
                    .setParameter("userId", userId)
                    .setParameter("rId", vote.getRestaurant().getId())
                    .setParameter("startDate", atStartOfDayOrMin(now))
                    .setParameter("endDate", atStartOfNextDayOrMax(now))
                    .getResultList()
                    .isEmpty()) {
                throw new VotePerDayException("Can`t vote same day for same restaurant");
            }
            vote.setDate(LocalDateTime.now());
            em.persist(vote);
            return vote;
        } else if (vote.getUser().getId() == null) {
            return null;
        }
        Vote voteToBeChecked = em.find(Vote.class, vote.getId());
        LocalDateTime voteDateTime = voteToBeChecked.getDate();
        restrictionCheck(voteDateTime);
        return em.merge(vote);
    }

    @Override
    @Transactional
            (
                    propagation = Propagation.REQUIRED,
                    readOnly = false,
                    rollbackFor = Throwable.class
            )
    public boolean delete(long id, long userId) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Vote get(long id, long userId) {
        Query query = em.createNamedQuery(Vote.GET)
                .setParameter("id", id)
                .setParameter("userId", userId);
        Vote vote = (Vote) DataAccessUtils.singleResult(query.getResultList());
        return vote;
    }

    @Override
    public List<Vote> getAll(long userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Vote> getAllForRestaurant(long rId) {
        return em.createNamedQuery(Vote.ALL_FOR_REST, Vote.class)
                .setParameter("rId", rId)
                .getResultList();
    }
}
