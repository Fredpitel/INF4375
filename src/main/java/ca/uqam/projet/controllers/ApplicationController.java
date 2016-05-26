package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodtruckRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.sql.Date;

@Controller
public class ApplicationController {

  @Autowired
  FoodtruckRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    return "index";
  }


  @RequestMapping("/horaires-camions")
  public String horaire(@RequestParam("firstDate") String firstDate, @RequestParam("lastDate") String lastDate, Model model) {
    model.addAttribute("cuisine", repository.select(firstDate, lastDate));
    return "horaires-camions";
  }





}
