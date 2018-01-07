package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.TargetPermission;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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

      //delete all existing permissions for this project
      permr.deleteByTargetPermission(saveProjet);

      //create new permission
      Set<Permission> permissions = new HashSet<>();
      permissions.addAll(createProjetPermission(project.getReadUsers(), TypePermissionEnum.READ,
          saveProjet));
      permissions.addAll(createProjetPermission(project.getWriteUsers(), TypePermissionEnum.WRITE,
          saveProjet));
      permr.save(permissions);

      return "redirect:/projets";
    }
  }

  @PreAuthorize("hasPermission(#idProjet, 'Projet', T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  @GetMapping(value = "/projets/edit/{idProjet}")
  public String editProjetForm(Model model, @PathVariable("idProjet") String idProjet) {
    model.addAttribute("users", ur.findAll());
    Projet project = pr.findOne(idProjet);

    project.setReadUsers(
        permr.findAllByTargetPermissionAndType(project, TypePermissionEnum.READ).stream()
            .map(p -> p.getUtilisateur().getId())
            .collect(Collectors.toSet()));

    project.setWriteUsers(
        permr.findAllByTargetPermissionAndType(project, TypePermissionEnum.WRITE).stream()
            .map(p -> p.getUtilisateur().getId())
            .collect(Collectors.toSet()));

    model.addAttribute("project", project);
    return "projet/singleProject";
  }


  private Set<Permission> createProjetPermission(Set<String> listIdUser,
      TypePermissionEnum typePermissionEnum, TargetPermission projet) {
    Set<Permission> permissions = new HashSet<>();
    if (listIdUser != null) {
      listIdUser.forEach(ru -> permissions.add(
          new Permission(TypePermissionEnum.READ, ur.findOne(ru), projet)));
    }
    return permissions;
  }

}
