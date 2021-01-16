package ru.fsw.revo.domain.to;

import lombok.Data;
import ru.fsw.revo.domain.model.MenuItem;
import ru.fsw.revo.domain.model.Restaurant;

import java.util.List;
import java.util.Map;

@Data
public class RestaurantTo {

    public long id;
    public String name;
    public double rating;
    public int votes;
    private List<MenuItem> menu;

    public RestaurantTo(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menu = restaurant.getMenu();
    }

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant restaurant, double rating, int votes, List<MenuItem> menu) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.rating = rating;
        this.votes = votes;
        this.menu = menu;
    }

    public RestaurantTo(Restaurant restaurant, double rating, int votes) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menu = restaurant.getMenu();
        this.rating = rating;
        this.votes = votes;
    }

}
