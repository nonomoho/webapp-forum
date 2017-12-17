package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class RegisterController {

    @Autowired
    UtilisateurRepository ur;

    @GetMapping(value = "/register")
    public String askAddUser(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "login/register";
    }


    @PostMapping(value = "/register")
    public String addUser(Model model, @ModelAttribute("user") @Valid Utilisateur user, BindingResult result,
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
            return "login/register";
        } else {
            user.setInscription(new Date());
            String md5Hex = DigestUtils.md5Hex(user.getPassword()).toUpperCase();
            user.setPassword(md5Hex);
            ur.save(user);
            return "redirect:/login";
        }
    }

}
