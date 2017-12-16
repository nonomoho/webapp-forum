package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProjetController {

    @Autowired
    ProjetRepository pr;
    @Autowired
    UtilisateurRepository ur;


    @GetMapping(value = "/projets")
    public String getAllProjet(Model model) {
        model.addAttribute("projets", pr.findAll());
        return "projet/projet";
    }

    @GetMapping(value = "/addProject")
    public String addProjectForm(Model model) {
        model.addAttribute("users", ur.findAll());
        model.addAttribute("project", new Projet());
        return "projet/singleProject";
    }

    @PostMapping(value = "/addProject")
    public String addProject(Model model, @ModelAttribute("project") @Valid Projet project, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            return "projet/singleProject";
        } else {
            pr.save(project);
            return "redirect:/login";
        }
    }

}
