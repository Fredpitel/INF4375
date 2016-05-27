package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
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
    CuisineDeRueSchema cuisine = repository.select(firstDate, lastDate);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = "";

    if(cuisine.getFeatures().size() > 0) {
      try {
        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cuisine);
      } catch (JsonGenerationException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    model.addAttribute("jsonString", jsonString);
    model.addAttribute("foodtrucks", cuisine.getFeatures());

    return "horaires-camions";
  }





}
