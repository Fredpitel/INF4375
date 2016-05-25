package ca.uqam.projet.repositories;

import java.util.*;
import java.sql.*;

import ca.uqam.projet.schema.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

import java.lang.Object.*;

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
}