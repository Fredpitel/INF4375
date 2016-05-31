package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {

  @Autowired
  FoodtruckRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    return "index";
  }


  @RequestMapping("/horaires-camions")
  public @ResponseBody CuisineDeRueSchema horaire(@RequestParam("du") String firstDate, @RequestParam("au") String lastDate) {
    return repository.select(firstDate, lastDate);
  }





}
