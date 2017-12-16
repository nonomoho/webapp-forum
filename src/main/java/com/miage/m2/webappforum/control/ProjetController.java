package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjetController {

    @Autowired
    ProjetRepository pr;

    @GetMapping(value = "/projets")
    public String getAllProjet(Model model) {
        model.addAttribute("projets", pr.findAll());
        return "projet/projet";
    }

}
