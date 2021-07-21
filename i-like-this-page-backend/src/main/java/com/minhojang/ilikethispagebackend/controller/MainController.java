package com.minhojang.ilikethispagebackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

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
