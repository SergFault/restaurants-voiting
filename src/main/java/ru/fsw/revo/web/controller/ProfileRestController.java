package ru.fsw.revo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.fsw.revo.domain.model.User;
import ru.fsw.revo.domain.to.UserTo;
import ru.fsw.revo.service.UserService;
import ru.fsw.revo.utils.UserUtil;

import java.net.URI;

import static ru.fsw.revo.utils.SecurityUtil.authUserId;
import static ru.fsw.revo.utils.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController{
    public static final String REST_URL = "/rest/profile";

    @Autowired
    private UserService service;

    @GetMapping
    public User get() {
        return service.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        service.delete(authUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        User created = service.create(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        assureIdConsistent(userTo, authUserId());
        service.update(userTo);
    }

    @GetMapping("/text")
    public String testUTF() {
        return "Русский текст";
    }
}
