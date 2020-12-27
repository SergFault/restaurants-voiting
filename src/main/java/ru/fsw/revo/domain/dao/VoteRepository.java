package ru.fsw.revo.domain.dao;

import ru.fsw.revo.domain.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote meal, long userId);

    // false if entity do not belong to userId
    boolean delete(long id, long userId);

    // null if entity do not belong to userId
    Vote get(long id, long userId);

    // ORDERED dateTime desc
    List<Vote> getAll(long userId);
}
