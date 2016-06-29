package ca.uqam.projet.repositories;

import java.util.*;
import java.sql.*;

import ca.uqam.projet.schema.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class FoodtruckRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    private static final String INSERT_STMT =
            " insert into foodtruck (camion, lieu, heure_debut, heure_fin, jour, coord)"
                    + " values (?, ?, ?, ?, ?, ST_GeomFromText(?, 4326))"
                    + " on conflict do nothing"
            ;

    private static final String SELECT_STMT =
            "SELECT camion, lieu, heure_debut, heure_fin, jour, ST_X(coord) AS lon, ST_Y(coord) AS lat FROM foodtruck"
                    + " WHERE jour >= ? AND jour <= ?;";

    private static final String SELECTALL_STMT =
            "SELECT camion, lieu, heure_debut, heure_fin, jour, ST_X(coord) AS lon, ST_Y(coord) AS lat FROM foodtruck;";

    public void truncate(){
        jdbcTemplate.execute("ALTER SEQUENCE foodtruck_idfoodtruck_seq RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE foodtruck;");
    }

    public int insert(FeaturesCollectionSchema foodtruck) {
        FoodTruckPropertiesSchema propreties = foodtruck.getProperties();

        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setString(1, propreties.getCamion().replace("&#039;", "'").replace("&lt;", "<").replace("&gt;", ">").replace("&amp;","&"));
            ps.setString(2, propreties.getLieu().replace("&#039;", "'"));
            ps.setString(3, propreties.getHeure_debut());
            ps.setString(4, propreties.getHeure_fin());
            ps.setString(5, propreties.getDate());
            ps.setString(6, "POINT(" + foodtruck.getGeometry() + ")");
            return ps;
        });
    }

    public CuisineDeRueSchema findAll() {
        List<FeaturesCollectionSchema> features = jdbcTemplate.query(SELECTALL_STMT, (rs, rowNum) ->
                new FeaturesCollectionSchema(
                        new FoodTruckPropertiesSchema(rs.getString("camion"), rs.getString("lieu"), rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getString("jour")),
                        new FoodTruckCoordSchema(Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon"))))
        );

        return new CuisineDeRueSchema(features);
    }

    public CuisineDeRueSchema selectByDate(String firstDate, String lastDate) {
        List<FeaturesCollectionSchema> features = jdbcTemplate.query(SELECT_STMT, ps -> {
                    ps.setString(1, firstDate);
                    ps.setString(2, lastDate);
                },(rs, rowNum) ->
                new FeaturesCollectionSchema(
                        new FoodTruckPropertiesSchema(rs.getString("camion"), rs.getString("lieu"), rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getString("jour")),
                        new FoodTruckCoordSchema(Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon"))))
        );

        return new CuisineDeRueSchema(features);
    }
}