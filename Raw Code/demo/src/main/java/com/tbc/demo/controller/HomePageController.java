package com.tbc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class HomePageController {
    @RequestMapping(value={"", "/", "welcome"})
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView("swagger-ui.html#/data-rest-api");
    }
}

