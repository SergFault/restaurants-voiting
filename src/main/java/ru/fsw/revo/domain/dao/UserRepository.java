package ru.fsw.revo.domain.dao;

import ru.fsw.revo.domain.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(long id);

    // null if not found
    User get(long id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}
