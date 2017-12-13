package com.miage.m2.webappforum.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class ControllerLogin {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login/login";
    }
}
