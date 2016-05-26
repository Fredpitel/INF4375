package ca.uqam.projet.repositories;

import java.util.*;
import java.sql.*;

import ca.uqam.projet.schema.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class FoodtruckRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    private static List<FeaturesCollectionSchema> FeaturesCollectionSchemas;

    public List<FeaturesCollectionSchema> findAll() {
        return FeaturesCollectionSchemas;
    }

    public FeaturesCollectionSchema findById(int id) {
        return FeaturesCollectionSchemas.get(id-1);
    }

    private static final String INSERT_STMT =
            " insert into foodtruck (camion, lieu, heure_debut, heure_fin, jour, coord)"
                    + " values (?, ?, ?, ?, ?, ST_GeomFromText(?, 4326))"
                    + " on conflict do nothing"
            ;

    public int insert(FeaturesCollectionSchema foodtruck) {
        FoodTruckPropertiesSchema propreties = foodtruck.getProperties();

        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setString(1, propreties.getCamion());
            ps.setString(2, propreties.getLieu());
            ps.setString(3, propreties.getHeure_debut());
            ps.setString(4, propreties.getHeure_fin());
            ps.setString(5, propreties.getDate());
            ps.setString(6, "POINT(" + foodtruck.getGeometry() + ")");
            return ps;
        });
    }

    public String select(String firstDate, String lastDate){
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM foodtruck WHERE (jour >= '" + firstDate + "' AND jour <= '" + lastDate + "') ORDER BY jour;");
        CuisineDeRueSchema cuisine = new CuisineDeRueSchema();
        ArrayList<FeaturesCollectionSchema> foodtrucks = new ArrayList<FeaturesCollectionSchema>();

        for(Map<String, Object> row : rows) {
            FeaturesCollectionSchema foodtruck = new FeaturesCollectionSchema();
            FoodTruckPropertiesSchema properties = new FoodTruckPropertiesSchema();
            FoodTruckCoordSchema coord = new FoodTruckCoordSchema();

            properties.setCamion("" + row.get("camion"));
            properties.setLieu("" + row.get("lieu"));
            properties.setHeure_debut("" + row.get("heure_debut"));
            properties.setHeure_fin("" + row.get("heure_debut"));
            properties.setDate("" + row.get("jour"));

            foodtruck.setProperties(properties);

            Map<String, Object> geometry = jdbcTemplate.queryForMap("SELECT ST_X(coord), ST_Y(coord) FROM foodtruck WHERE idFoodtruck = " + row.get("idFoodtruck") + ";");

            double[] coordDouble = new double[2];
            coordDouble[0] = Double.parseDouble("" + geometry.get("st_x"));
            coordDouble[1] = Double.parseDouble("" +geometry.get("st_y"));

            coord.setCoordinates(coordDouble);
            foodtruck.setGeometry(coord);
            foodtrucks.add(foodtruck);
        }

        cuisine.setFeatures(foodtrucks);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(cuisine);

        return jsonString;
    }
}