package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Role;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.RoleRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurRepository ur;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository rr;

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        model.addAttribute("utilisateurs", ur.findAll());
        return "user/users";
    }

    @GetMapping(value = "/users/{userId}")
    public String getUser(Model model, @PathVariable("userId") String userId) {

        model.addAttribute("users", ur.findOne(userId));
        return "user/user";
    }
    @PostMapping(value = "/utilisateur")
    public String addUser(Model model, @ModelAttribute("user") @Valid Utilisateur user,
                          BindingResult result,
                          @RequestParam(value = "password_confirm") String confirm) {
        if (!confirm.equals(user.getPassword())) {
            result.rejectValue("password", "notSamePassword");
        }
        if (ur.existsByPseudo(user.getPseudo())) {
            result.rejectValue("pseudo", "register.userNameAlreadyExist");
        }
        if (ur.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "register.mailAlreadyExist");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/user";
        } else {
            user.setInscription(new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            ur.save(user);

            Role role = rr.getRoleByName("USER");
            role.getUtilisateurs().add(user);
            rr.save(role);

            return "redirect:/user/user";
        }
    }
}
