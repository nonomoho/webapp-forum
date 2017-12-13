package com.miage.m2.webappforum.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerLogin {

    @GetMapping(value = "/login")
    public String login() {
        return "login/login";
    }

}
