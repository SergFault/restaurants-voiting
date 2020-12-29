package ru.fsw.revo;

import ru.fsw.revo.domain.model.Restaurant;
import ru.fsw.revo.domain.model.Vote;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.fsw.revo.domain.model.AbstractBaseEntity.START_SEQ;

public class RestaurantsTestData {
    public static final long REST1_ID = START_SEQ + 20;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "Гавана");
    public static final Restaurant rest2 = new Restaurant(REST1_ID+1, "Триполи");
    public static final Restaurant rest3 = new Restaurant(REST1_ID+2, "Гараж");
    public static final Restaurant rest4 = new Restaurant(REST1_ID+3, "Пляж");
}
