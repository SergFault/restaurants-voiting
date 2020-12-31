package ru.fsw.revo;

import ru.fsw.revo.domain.model.Role;
import ru.fsw.revo.domain.model.User;
import ru.fsw.revo.domain.model.Vote;

import java.time.LocalDateTime;

import static ru.fsw.revo.domain.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final TestMatcher<User> PROFILE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class);

    public static final long USER_ID = START_SEQ+16;
    public static final long ADMIN_ID = START_SEQ + 17;

    public static final User user = new User(USER_ID, "frank", "frank@mail.com", "password", LocalDateTime.of(2020, 11, 11, 10,00,00), Role.USER);
    public static final User admin = new User(ADMIN_ID, "eugene", "eugene@mail.com", "password", LocalDateTime.of(2020, 11, 05, 20,00), Role.ADMIN, Role.USER);
    public static final User user_profile = new User(USER_ID, "frank", "frank@mail.com", "{noop}password", LocalDateTime.of(2020, 11, 11, 10,00,00), Role.USER);


    public final static String USER_NAME = "user";
    public final static String USER_PASSWORD = "password";
    public final static String ADMIN_NAME = "admin";
    public final static String ADMIN_PASSWORD = "root";
}
