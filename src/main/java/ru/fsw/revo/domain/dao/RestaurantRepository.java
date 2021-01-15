package ru.fsw.revo.domain.dao;

import ru.fsw.revo.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant get(long rId);

    Restaurant save(Restaurant restaurant);

    List<Restaurant> getAll();
}
