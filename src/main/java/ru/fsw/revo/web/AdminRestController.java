package ru.fsw.revo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.fsw.revo.domain.model.Restaurant;
import ru.fsw.revo.service.RestaurantService;

import java.util.Map;

import static ru.fsw.revo.web.AdminRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminRestController {
    public final static String REST_URL = "/rest/admin/restaurant";

    @Autowired
    private final RestaurantService service;

    public AdminRestController(RestaurantService service) {
        this.service = service;
    }

    @PutMapping(value = "/{rId}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenu(@PathVariable long rId, @RequestBody Map<String, Integer> menu) {
        service.updateMenu(rId, menu);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@RequestBody Restaurant r) {
        return service.create(r);
    }

    @PutMapping(value = "/{rId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable long rId, @RequestBody Restaurant r) {
        service.update(r, rId);
    }
}
