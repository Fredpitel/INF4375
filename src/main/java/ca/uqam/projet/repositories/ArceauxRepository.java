package ca.uqam.projet.repositories;

import ca.uqam.projet.schema.ArceauxSchema;
import ca.uqam.projet.schema.CollectionArceauxSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ArceauxRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_STMT =
            " insert into arceau (coord)"
                    + " values (ST_GeomFromText(?, 4326))"
                    + " on conflict do nothing"
            ;

    private static final String SELECTALL_STMT =
            "SELECT ST_X(coord) AS lon, ST_Y(coord) AS lat FROM arceau;";

    private static final String SELECT_STMT =
            "SELECT ST_X(coord) AS lon, ST_Y(coord) AS lat FROM arceau "
                    + "WHERE st_distance_sphere(coord, ST_MakePoint(?, ?, 4326)) <= 200;";

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
        List<ArceauxSchema> liste = jdbcTemplate.query(SELECTALL_STMT,(rs, rowNum) ->
            new ArceauxSchema(Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon")))
        );

        return new CollectionArceauxSchema(liste);
    }

    public CollectionArceauxSchema selectByCoord(double lat, double lon){
        List<ArceauxSchema> liste = jdbcTemplate.query(SELECT_STMT, ps -> {
            ps.setDouble(1, lon);
            ps.setDouble(2, lat);
            }, (rs, rowNum) ->
            new ArceauxSchema(Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon")))
        );

        return new CollectionArceauxSchema(liste);
    }
}
