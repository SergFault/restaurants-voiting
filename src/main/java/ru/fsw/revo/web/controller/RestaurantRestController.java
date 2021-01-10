package ru.fsw.revo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.fsw.revo.domain.to.RestaurantTo;
import ru.fsw.revo.service.RestaurantService;
import ru.fsw.revo.service.VoteService;

import static ru.fsw.revo.web.controller.RestaurantRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public final static String  REST_URL = "/rest/restaurant";

    @Autowired
    private final VoteService voteService;
    private final RestaurantService resService;

    public RestaurantRestController(RestaurantService service, VoteService voteService) {
        this.resService = service;
        this.voteService = voteService;
    }

    @GetMapping("/{rId}")
    public RestaurantTo checkRestaurant(@PathVariable long rId) {
        return resService.getRestaurantWithRating(rId);
    }


}
