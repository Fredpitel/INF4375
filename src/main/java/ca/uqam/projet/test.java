package ca.uqam.projet;

import ca.uqam.projet.schema.StationsSchema;
import org.springframework.web.client.RestTemplate;

/**
 * Created by deasel on 2016-05-19.
 */
public class test {
    public static void main(String args[]){
        RestTemplate restTemplate=new RestTemplate();
        StationsSchema stations=restTemplate.getForObject("https://secure.bixi.com/data/stations.json", StationsSchema.class);
        System.out.println(stations.toString());
    }
}
