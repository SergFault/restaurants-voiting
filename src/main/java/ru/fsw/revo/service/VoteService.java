package ru.fsw.revo.service;

import org.springframework.stereotype.Service;
import ru.fsw.revo.domain.dao.VoteRepository;
import ru.fsw.revo.domain.model.Vote;

import java.util.List;

import static ru.fsw.revo.utils.ValidationUtil.*;

@Service
public class VoteService {
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote get (long id, long userId){
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Vote> getAll(long userId) {
        return repository.getAll(userId);
    }

    public void delete(long id, long userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Vote create(Vote vote, long userId) {
        return repository.save(vote, userId);
    }

    public void update(Vote vote, long id, long userId) {
        assureIdConsistent(vote, id);
        Vote voteSaved = repository.save(vote, userId);
        checkNotFoundWithId(voteSaved, id);
    }
}
