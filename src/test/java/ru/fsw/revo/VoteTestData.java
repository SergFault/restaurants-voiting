package ru.fsw.revo;

import ru.fsw.revo.domain.model.Vote;

import java.util.GregorianCalendar;
import java.util.List;

import static ru.fsw.revo.RestaurantsTestData.*;
import static ru.fsw.revo.domain.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant.menu");
    public static final TestMatcher<Vote> VOTE_CREATED_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant.menu", "restaurant.name", "id", "date");
    public static final int RANK_4 = 4;

    public static final long VOTE_NOT_FOUND_ID = 99999;
    public static final long ADMIN_VOTE_ID = 10028;
    public static final long VOTE1_ID = START_SEQ + 24;

    public static final Vote vote1 = new Vote(VOTE1_ID, 3, rest1, new GregorianCalendar(2020, 10, 12).getTime());
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, 6, rest2, new GregorianCalendar(2020, 10, 13).getTime());
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, 4, rest3, new GregorianCalendar(2020, 10, 14).getTime());
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, 10, rest4, new GregorianCalendar(2020, 10, 15).getTime());
    public static final List<Vote> all_votes = List.of(vote1, vote2, vote3, vote4);

    public static Vote getNew() {
        return new Vote(null, 4, rest1, new GregorianCalendar(2020, 8, 20).getTime());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, 10, rest1, new GregorianCalendar(2020, 8, 20).getTime());
    }
}

