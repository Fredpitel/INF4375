package ca.uqam.projet.tasks;

import ca.uqam.projet.schema.*;
import ca.uqam.projet.repositories.*;
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

    @Scheduled(cron="0/5 * * * * ?")
    public void execute() {

        RestTemplate restTemplate = new RestTemplate();
        CuisineDeRueSchema foodtrucks = restTemplate.getForObject("http://camionderue.com/donneesouvertes/geojson",CuisineDeRueSchema.class);
        repository.truncate();
        foodtrucks.getFeatures().stream().forEach(repository::insert);
    }
}