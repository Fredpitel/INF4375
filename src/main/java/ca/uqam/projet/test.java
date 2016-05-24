package ca.uqam.projet;

import org.json.JSONObject;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import ca.uqam.projet.schema.StationsSchema;
import org.springframework.web.client.RestTemplate;

/**
 * Created by deasel on 2016-05-19.
 */
public class test {
    public static void main(String args[]){
        RestTemplate restTemplate=new RestTemplate();
        //StationsSchema stations=restTemplate.getForObject("https://secure.bixi.com/data/stations.json", StationsSchema.class);
        //System.out.println(stations.getStations().get(8).toString());

        restTemplate=new RestTemplate();
        CuisineDeRueSchema foodtruck=restTemplate.getForObject("http://camionderue.com/donneesouvertes/geojson",CuisineDeRueSchema.class);

        System.out.println(foodtruck);
    }
}
