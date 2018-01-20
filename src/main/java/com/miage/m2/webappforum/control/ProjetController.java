package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.PermissionService;
import com.miage.m2.webappforum.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProjetController {

  @Autowired
  ProjetRepository pr;
  @Autowired
  UtilisateurRepository ur;
  @Autowired
  PermissionRepository permr;

  @Autowired
  UserService us;
  @Autowired
  PermissionService ps;

  @GetMapping(value = "/projets")
  public String getAllProjet(Model model) {
    model.addAttribute("projets", pr.findAll());
    return "projet/projet";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/projets/add")
  public String addProjectForm(Model model) {
    model.addAttribute("users", ur.findAll());
    model.addAttribute("project", new Projet());
    return "projet/singleProject";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(value = "/projets/save")
  public String addProject(Model model, @ModelAttribute("project") @Valid Projet project,
      BindingResult result) {
    project.setNom(project.getNom().trim().toUpperCase());

    if (project.getId().isEmpty() && pr.existsByNom(project.getNom())) {
      result.rejectValue("nom", "project.nameAlreadyExist");
    }
    if (result.hasErrors()) {
      model.addAttribute("project", project);
      model.addAttribute("users", ur.findAll());
      return "projet/singleProject";
    } else {
      Projet saveProjet = pr.save(project);
      saveProjet.setReadUsers(project.getReadUsers());
      saveProjet.setWriteUsers(project.getWriteUsers());

      ps.setPermission(saveProjet);

      return "redirect:/projets";
    }
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/projets/edit/{idProjet}")
  public String editProjetForm(Model model, @PathVariable("idProjet") String idProjet) {
    model.addAttribute("users", ur.findAll());
    Projet project = pr.findOne(idProjet);

    ps.getPermission(project);

    model.addAttribute("project", project);
    return "projet/singleProject";
  }

}
