package ru.fsw.revo.domain.dao;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.fsw.revo.domain.model.User;
import ru.fsw.revo.domain.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

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
            em.persist(vote);
            return vote;
        } else if (vote.getUser().getId() == null) {
            return null;
        }
        Vote merged = em.merge(vote);
        return merged;
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
                if (vote ==null) {
                    return null;
                }
                return vote;
    }

    @Override
    public List<Vote> getAll(long userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
