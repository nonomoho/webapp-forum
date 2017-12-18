package com.miage.m2.webappforum.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping(value = "/")
  public String getHome() {
    return "index";
  }
}
