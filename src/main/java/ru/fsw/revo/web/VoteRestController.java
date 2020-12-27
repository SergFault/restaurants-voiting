package ru.fsw.revo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.fsw.revo.domain.model.Vote;
import ru.fsw.revo.service.VoteService;
import ru.fsw.revo.utils.SecurityUtil;

import java.net.URI;
import java.util.List;

import static ru.fsw.revo.utils.ValidationUtil.checkNew;

@RestController
@RequestMapping(value ="/rest/voting", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    private final VoteService service;

    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable long id) {
        long userId = SecurityUtil.authUserId();
        service.update(vote, id , userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote create(@RequestBody Vote vote) {
        long userId = SecurityUtil.authUserId();
        return service.create(vote, userId);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable long id) {
        long userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }


    @GetMapping
    public List<Vote> getAll() {
        long userId = SecurityUtil.authUserId();
        return service.getAll(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        long userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

}
