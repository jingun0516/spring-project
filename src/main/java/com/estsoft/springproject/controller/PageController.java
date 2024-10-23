package com.estsoft.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
public class PageController {
    // Person       GET /thymeleaf/example
    @GetMapping("/thymeleaf/example")
    public String show(Model model) {
        Person person = new Person();

        person.setId(1L);
        person.setName("징겅");
        person.setAge(29);
        person.setHobbies(Arrays.asList("달리기", "운동", "복싱", "..."));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDateTime.now());

        return "examplePage";      // html 페이지
    }
}
