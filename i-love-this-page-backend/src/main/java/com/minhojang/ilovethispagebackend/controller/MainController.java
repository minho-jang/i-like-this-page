package com.minhojang.ilovethispagebackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin
public class MainController {

  static int likes = 30;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("likes", likes++);
    return "index";
  }

}
