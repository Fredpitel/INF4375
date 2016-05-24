package ca.uqam.projet.tasks;

import ca.uqam.projet.schema.*;

import java.util.*;

import ca.uqam.projet.repositories.*;

import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.*;

@Component
public class FetchFoodtrucksTask {

    private static final Logger log = LoggerFactory.getLogger(FetchFoodtrucksTask.class);
    private static final String URL = "http://camionderue.com/donneesouvertes/geojson";

    @Autowired private FoodtruckRepository repository;

    @Scheduled(cron="*/10 * * * * ?")
    public void execute() {

        RestTemplate restTemplate = new RestTemplate();
        CuisineDeRueSchema foodtrucks = restTemplate.getForObject("http://camionderue.com/donneesouvertes/geojson",CuisineDeRueSchema.class);

        foodtrucks.getFeatures().stream().forEach(repository::insert);
    }
}