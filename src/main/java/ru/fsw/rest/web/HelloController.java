package ru.fsw.rest.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/rest")
public class HelloController {

    @GetMapping("/voting")
    public String sayHello() {
        return "votings";
    }
}
