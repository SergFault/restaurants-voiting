package ru.fsw.revo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.fsw.revo.domain.dao.RestaurantRepository;
import ru.fsw.revo.domain.model.Restaurant;
import ru.fsw.revo.domain.model.Vote;
import ru.fsw.revo.domain.to.RestaurantTo;

import java.util.List;
import java.util.Map;

import static ru.fsw.revo.utils.ValidationUtil.assureIdConsistent;
import static ru.fsw.revo.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;
    private final VoteService voteService;

    public RestaurantService(RestaurantRepository repository, VoteService service) {
        this.repository = repository;
        this.voteService = service;
    }

    public Restaurant get(long rId) {
        return checkNotFoundWithId(repository.get(rId), rId);
    }

    public void updateMenu(long rId, Map<String, Integer> menu) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = get(rId);
        restaurant.setMenu(menu);
        checkNotFoundWithId(repository.save(restaurant), rId);
    }

    public void update(Restaurant rest, long rId) {
        Assert.notNull(rest, "restaurant must not be null");
        assureIdConsistent(rest, rId);
        checkNotFoundWithId(repository.save(rest), rId);
    }

    public Restaurant create(Restaurant rest) {
        Assert.notNull(rest, "restaurant must not be null");
        return repository.save(rest);
    }

    private RestaurantTo setRating(RestaurantTo restaurant){
        List<Vote> votes = voteService.getAllForRestaurant(restaurant.getId());
        int countVotes = votes.size();
        restaurant.setVotes(countVotes);
        restaurant.setRating(votes.stream()
                .mapToDouble(a -> a.getRating()
                )
                .average()
                .orElse(0));
        return restaurant;
    }

    public RestaurantTo getRestaurantWithRating(long rId) {
        return setRating(new RestaurantTo(repository.get(rId)));
    }

    public List<Restaurant> getRestaurants() {
        return this.repository.getAll();
    }
}
