package ru.fsw.revo.domain.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.fsw.revo.domain.model.User;
import ru.fsw.revo.domain.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUser().getId() == userId ? vote : null;
    }

    @Override
    public List<Vote> getAll(long userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, long userId) {
        return null;
    }
}
