package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodtruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

  @Autowired
  FoodtruckRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    model.addAttribute("foodtruck", repository.findAll());
    return "index";
  }







}
