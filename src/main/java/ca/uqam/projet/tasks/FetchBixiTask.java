package ca.uqam.projet.tasks;

import ca.uqam.projet.schema.*;
import ca.uqam.projet.repositories.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchBixiTask {

    private static final Logger log = LoggerFactory.getLogger(FetchBixiTask.class);
    private static final String URL = "https://secure.bixi.com/data/stations.json";

    @Autowired private BixiRepository repository;

    @Scheduled(cron="0/5 * * * * ?")
    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        StationsSchema stations = restTemplate.getForObject(URL, StationsSchema.class);
        repository.truncate();
        stations.getStations().stream().forEach(repository::insert);
    }
}