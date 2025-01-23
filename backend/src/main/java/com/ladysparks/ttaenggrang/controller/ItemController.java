package com.ladysparks.ttaenggrang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping("{itemId}")
    public String getItems() {
        return "ok";
    }

}
