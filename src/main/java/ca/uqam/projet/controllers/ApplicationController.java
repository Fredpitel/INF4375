package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.ArceauxRepository;
import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.repositories.ListeFavorisRepository;
import ca.uqam.projet.schema.CollectionArceauxSchema;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import ca.uqam.projet.schema.ListeFavorisSchema;
import ca.uqam.projet.schema.StationsSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    FoodtruckRepository foodtruckRepository;

    @Autowired
    BixiRepository bixiRepository;

    @Autowired
    ArceauxRepository arceauxRepository;

    @Autowired
    ListeFavorisRepository listeFavorisRepository;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
      }

    @RequestMapping("/horaires-camions")
    public @ResponseBody CuisineDeRueSchema horaire(@RequestParam(value ="du", required = false) String firstDate, @RequestParam(value = "au", required = false) String lastDate) {
        if(firstDate == null || lastDate == null){
            return foodtruckRepository.findAll();
        } else {
            return foodtruckRepository.selectByDate(firstDate, lastDate);
        }
    }

    @RequestMapping(path = "/{userId}/favoris", method = RequestMethod.GET)
    public @ResponseBody
        ListeFavorisSchema getFavoris(@PathVariable String userId) {
        return listeFavorisRepository.selectFavoritesByUserID(userId);
    }

    @RequestMapping(path = "/{userId}/favoris", method = RequestMethod.POST)
    public @ResponseBody
        void postFavoris(@PathVariable String userId, @RequestParam("camion") String camion) {
        listeFavorisRepository.insertFavoriteByUserID(userId, camion);
    }

    @RequestMapping(path = "/{userId}/favoris", method = RequestMethod.DELETE)
    public @ResponseBody
        void deleteFavoris(@PathVariable String userId, @RequestParam("camion") String camion) {
        listeFavorisRepository.deleteFavoriteByUserID(userId, camion);
    }

    @RequestMapping("/bixis")
    public @ResponseBody StationsSchema bixi(@RequestParam(value = "lat", required = false) Double lat, @RequestParam(value = "lon", required = false) Double lon) {
        if(lat == null || lon == null){
            return bixiRepository.findAll();
        } else {
            return bixiRepository.selectByCoord(lat, lon);
        }
    }

    @RequestMapping("/arceaux")
    public @ResponseBody CollectionArceauxSchema arceaux(@RequestParam(value = "lat", required = false) Double lat, @RequestParam(value = "lon", required = false) Double lon) {
        if(lat == null || lon == null){
            return arceauxRepository.findAll();
        } else {
            return arceauxRepository.selectByCoord(lat, lon);
        }
    }




}
