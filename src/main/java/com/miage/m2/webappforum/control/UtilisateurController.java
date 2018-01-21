package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.RoleRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {

  @Autowired
  UtilisateurRepository ur;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  UserService us;

  @Autowired
  RoleRepository rr;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(value = "/users")
  public String getAllUsers(Model model) {
    model.addAttribute("utilisateurs", ur.findAll());
    return "user/users";
  }

  @PreAuthorize("hasAuthority('USER')")
  @GetMapping(value = "/account")
  public String getUser(Model model) {
    model.addAttribute("user", us.getLoggedUser());
    return "user/singleUser";
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping(value = "/account/save")
  public String addUser(Model model, @ModelAttribute("user") @Valid Utilisateur user,
      BindingResult result,
      @RequestParam(value = "password_confirm") String confirm) {

    Utilisateur currentUser = us.getLoggedUser();
    //if current password is not correct
    if (!passwordEncoder.matches(user.getPassword(), currentUser.getPassword())) {
      result.rejectValue("password", "wrongPassword");
    }
    //if user chose to change password
    if (!user.getNewPassword().isEmpty() && !confirm.equals(user.getNewPassword())) {
      result.rejectValue("newPassword", "notSamePassword");
    }
    //if user chose to change email adress
    if (ur.existsByEmail(user.getEmail()) && !user.getEmail().equals(currentUser.getEmail())) {
      result.rejectValue("email", "register.mailAlreadyExist");
    }
    if (result.hasErrors()) {
      System.out.println("error");
      System.out.println(result.getAllErrors().toString());
      model.addAttribute("user", user);
      return "user/singleUser";
    } else {
      if (!user.getNewPassword().isEmpty()) {
        user.setPassword(passwordEncoder.encode(user.getNewPassword()));
      } else {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      }
      user.setInscription(currentUser.getInscription());
      ur.save(user);

      return "redirect:/";
    }
  }
}
