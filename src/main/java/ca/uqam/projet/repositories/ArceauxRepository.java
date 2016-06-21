package ca.uqam.projet.repositories;

import ca.uqam.projet.schema.ArceauxSchema;
import ca.uqam.projet.schema.CollectionArceauxSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
public class ArceauxRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_STMT =
            " insert into arceau (coord)"
                    + " values (ST_GeomFromText(?, 4326))"
                    + " on conflict do nothing"
            ;

    public void truncate(){
        jdbcTemplate.execute("ALTER SEQUENCE arceau_idarceau_seq RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE arceau;");
    }

    public int insert(ArceauxSchema arceau){
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setString(1, "POINT(" + arceau.getLo() + " " + arceau.getLa() + ")");
            return ps;
        });
    }

    public CollectionArceauxSchema findAll(){
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM arceau;");
        return makeJavaObject(rows);
    }

    public CollectionArceauxSchema selectByCoord(double lat, double lon){
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM arceau WHERE st_distance_sphere(coord, ST_MakePoint(" + lon + ", " + lat + ", 4326)) <= 200;");
        return makeJavaObject(rows);
    }

    private CollectionArceauxSchema makeJavaObject(List<Map<String, Object>> rows){
        CollectionArceauxSchema arceaux = new CollectionArceauxSchema();

        for(Map<String, Object> row : rows) {
            Map<String, Object> geometry = jdbcTemplate.queryForMap("SELECT ST_X(coord), ST_Y(coord) FROM arceau WHERE idArceau = " + row.get("idArceau") + ";");

            double[] coordDouble = new double[2];
            coordDouble[0] = Double.parseDouble("" + geometry.get("st_x"));
            coordDouble[1] = Double.parseDouble("" + geometry.get("st_y"));

            arceaux.addArceau(new ArceauxSchema(coordDouble[1],coordDouble[0]));
        }

        return arceaux;
    }
}
