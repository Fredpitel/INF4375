package ca.uqam.projet.repositories;

import ca.uqam.projet.schema.ListeFavorisSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Frederic.Pitel on 23/6/16.
 */

@Component
public class ListeFavorisRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ListeFavorisSchema selectFavoritesByUserID(String userId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT camion FROM listefavoris WHERE idusager = " + userId + ";");
        ListeFavorisSchema favoris = makeJavaObject(rows);

        return favoris;
    }

    public void insertFavoriteByUserID(String userId, String camion) {
        jdbcTemplate.execute("INSERT INTO listefavoris VALUES (DEFAULT, " + userId + ", '" + camion.replace("'", "''") + "');");
    }

    public void deleteFavoriteByUserID(String userId, String camion) {
        jdbcTemplate.execute("DELETE FROM listefavoris WHERE idusager = " + userId + " AND camion = '" + camion.replace("'", "''") +"';");
    }



    public ListeFavorisSchema makeJavaObject(List<Map<String, Object>> rows){
        ListeFavorisSchema favoris = new ListeFavorisSchema();
        ArrayList<String> liste = new ArrayList();

        for(Map<String, Object> row : rows){
            liste.add("" + row.get("camion"));
        }

        favoris.setFavoris(liste);

        return  favoris;
    }

}
