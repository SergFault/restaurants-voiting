package ru.fsw.revo;

import ru.fsw.revo.domain.model.Restaurant;
import ru.fsw.revo.domain.model.Vote;
import ru.fsw.revo.domain.to.RestaurantTo;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.of;
import static ru.fsw.revo.domain.model.AbstractBaseEntity.START_SEQ;

public class RestaurantsTestData {
    public static final TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final long REST1_ID = START_SEQ + 20;
    public static final double rating_rest1 = (3 + 8 + 8) / 3d;
    public static final int votes_rest1 = 3;
    public static final Map<String, Integer> rest1_menu = new HashMap() {{
        put("Беляш", 10000);
        put("Бигос", 12000);
        put("Бисквит", 5000);
        put("Винегрет", 7000);
    }};

    public static final Map<String, Integer> rest1_updated_menu = new HashMap() {{
        put("Латте", 12000);
        put("Эспрессо", 12000);
    }};

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "Гавана");
    public static final Restaurant rest1_updated = new Restaurant(REST1_ID, "Гавана - кофе", rest1_updated_menu);
    public static final Restaurant rest1_with_updated_menu = new Restaurant(REST1_ID, "Гавана", rest1_updated_menu);
    public static final Restaurant rest2 = new Restaurant(REST1_ID + 1, "Триполи");
    public static final Restaurant rest3 = new Restaurant(REST1_ID + 2, "Гараж");
    public static final Restaurant rest4 = new Restaurant(REST1_ID + 3, "Пляж");

    public static final RestaurantTo rest1To = new RestaurantTo(new Restaurant(REST1_ID, "Гавана"), rating_rest1, 3, rest1_menu);
    public static final Restaurant newRestaurant = new Restaurant("МакКинг", rest1_menu);
    public static final Restaurant created = new Restaurant(REST1_ID+16,"МакКинг", rest1_menu);
    public static final RestaurantTo createdTo = new RestaurantTo(new Restaurant(REST1_ID+16, "МакКинг"), 0d, 0, rest1_menu);
    public static final RestaurantTo rest1_updatedTo = new RestaurantTo(rest1_updated, rating_rest1, votes_rest1);
    public static final RestaurantTo rest1To_with_updated_menu = new RestaurantTo(rest1_with_updated_menu, rating_rest1, votes_rest1);

    public static Restaurant getNew(){
        return created;
    }
}
