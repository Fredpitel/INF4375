
package ca.uqam.projet;

import ca.uqam.projet.repositories.FoodtruckRepository;

import ca.uqam.projet.tasks.FetchFoodtrucksTask;
import org.slf4j.*;
import ca.uqam.projet.schema.CuisineDeRueSchema;
import ca.uqam.projet.schema.StationsSchema;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by deasel on 2016-05-19.
 */

@Component
public class test {

    public static void main(String args[]){
        FetchFoodtrucksTask fetch = new FetchFoodtrucksTask();
        fetch.execute();

    }
}
