package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.CitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

  @Autowired CitationRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    model.addAttribute("citations", repository.findAll());
    return "index";
  }







}
