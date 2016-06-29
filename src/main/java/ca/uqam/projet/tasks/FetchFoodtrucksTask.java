package ca.uqam.projet.tasks;

import ca.uqam.projet.schema.*;
import ca.uqam.projet.repositories.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class FetchFoodtrucksTask {

    private static final Logger log = LoggerFactory.getLogger(FetchFoodtrucksTask.class);
    private static final String URL = "http://camionderue.com/donneesouvertes/geojson";

    @Autowired private FoodtruckRepository repository;

    @Scheduled(cron="0 0 0,12 * * ?")
    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        CuisineDeRueSchema foodtrucks = restTemplate.getForObject(URL, CuisineDeRueSchema.class);
        repository.truncate();
        foodtrucks.getFeatures().stream().forEach(repository::insert);
    }
}