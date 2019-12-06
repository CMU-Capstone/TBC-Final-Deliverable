package com.tbc.slackanalyser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class HomePageController {
    @RequestMapping(value={"", "/", "welcome", "index"})
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView("swagger-ui.html#/slack-controller");
    }
}
