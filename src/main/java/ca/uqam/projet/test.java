
package ca.uqam.projet;

import ca.uqam.projet.schema.StationsSchema;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by deasel on 2016-05-19.
 */

@Component
public class test {

    public static void main(String args[]){

        RestTemplate restTemplate=new RestTemplate();
        StationsSchema stations=restTemplate.getForObject("https://secure.bixi.com/data/stations.json", StationsSchema.class);
        System.out.println(stations.getStations().get(8).toString());

/*
        FetchFoodtrucksTask fetch = new FetchFoodtrucksTask();
        fetch.execute();*/

    }
}
