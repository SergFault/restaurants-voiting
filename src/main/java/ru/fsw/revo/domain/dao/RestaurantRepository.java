package ru.fsw.revo.domain.dao;

import ru.fsw.revo.domain.model.Restaurant;

import java.util.Map;

public interface RestaurantRepository {
    Restaurant get(long rId);
    Restaurant save(Restaurant restaurant);
}
