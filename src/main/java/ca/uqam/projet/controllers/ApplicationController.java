package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import ca.uqam.projet.schema.StationsSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {

    @Autowired
    FoodtruckRepository foodtruckRepository;

    @Autowired
    BixiRepository bixiRepository;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
      }

    @RequestMapping("/horaires-camions")
    public @ResponseBody CuisineDeRueSchema horaire(@RequestParam("du") String firstDate, @RequestParam("au") String lastDate) {
        return foodtruckRepository.select(firstDate, lastDate);
    }

    @RequestMapping("/bixis")
    public @ResponseBody StationsSchema bixi(@RequestParam("lat") double lat, @RequestParam("lon") double lon) {
        return bixiRepository.select(lat, lon);
    }







}
