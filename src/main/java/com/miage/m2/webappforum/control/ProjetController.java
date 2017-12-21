package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProjetController {

  @Autowired
  ProjetRepository pr;
  @Autowired
  UtilisateurRepository ur;

  @Autowired
  UserService us;

  @GetMapping(value = "/projets")
  public String getAllProjet(Model model) {
    model.addAttribute("projets", pr.findAll());
    return "projet/projet";
  }

  @GetMapping(value = "/projets/add")
  public String addProjectForm(Model model) {
    model.addAttribute("users", ur.findAll());
    model.addAttribute("project", new Projet());
    return "projet/singleProject";
  }

  @PostMapping(value = "/projets/save")
  public String addProject(Model model, @ModelAttribute("project") @Valid Projet project,
      BindingResult result,
      @RequestParam(name = "readUsers", required = false) List<String> readUsers,
      @RequestParam(name = "readUsers", required = false) List<String> writeUsers) {
    project.setNom(project.getNom().trim().toUpperCase());
    project.setReadUsers(new HashSet<>((Collection) ur.findAll(readUsers)));
    project.setWriteUsers(new HashSet<>((Collection) ur.findAll(writeUsers)));
    if (project.getId().isEmpty() && pr.existsByNom(project.getNom())) {
      result.rejectValue("nom", "project.nameAlreadyExist");
    }
    if (result.hasErrors()) {
      model.addAttribute("project", project);
      return "projet/singleProject";
    } else {
      pr.save(project);
      return "redirect:/projets";
    }
  }

  //@PreAuthorize("hasPermission(#idProjet, 'Projet', 'READ')")
  @GetMapping(value = "/projets/edit/{idProjet}")
  public String editProjetForm(Model model, @PathVariable("idProjet") String idProjet) {
    model.addAttribute("users", ur.findAll());
    model.addAttribute("project", pr.findOne(idProjet));
    return "projet/singleProject";
  }

}
