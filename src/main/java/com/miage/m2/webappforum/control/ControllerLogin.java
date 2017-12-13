package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerLogin {

    @GetMapping(value = "/login")
    public String login() {
        return "login/login";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "login/register";
    }
}
