package ca.uqam.projet.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.uqam.projet.schema.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class BixiRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_STMT =
            " insert into bixi (coord, nbbikes, nbemptydocks)"
                    + " values (ST_GeomFromText(?, 4326), ?, ?)"
                    + " on conflict do nothing"
            ;

    private static final String SELECTALL_STMT =
            "SELECT nbBikes, nbEmptyDocks, ST_X(coord) AS lon, ST_Y(coord) AS lat FROM bixi;";

    private static final String SELECT_STMT =
            "SELECT nbBikes, nbEmptyDocks, ST_X(coord) AS lon, ST_Y(coord) AS lat FROM bixi "
                    + "WHERE st_distance_sphere(coord, ST_MakePoint(?, ?, 4326)) <= 200;";

    public void truncate(){
        jdbcTemplate.execute("ALTER SEQUENCE bixi_idbixi_seq RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE bixi;");
    }

    public int insert(BixiSchema bixi){
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setString(1, "POINT(" + bixi.getLo() + " " + bixi.getLa() + ")");
            ps.setInt(2, bixi.getBa());
            ps.setInt(3, bixi.getDa());
            return ps;
        });
    }

    public StationsSchema findAll(){
        List<BixiSchema> stations = jdbcTemplate.query(SELECTALL_STMT,(rs, rowNum) ->
                new BixiSchema(rs.getInt("nbBikes"), rs.getInt("nbEmptyDocks"), Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon")))
        );

        return new StationsSchema(stations);
    }

    public StationsSchema selectByCoord(double lat, double lon){
        List<BixiSchema> stations = jdbcTemplate.query(SELECT_STMT, ps -> {
                    ps.setDouble(1, lon);
                    ps.setDouble(2, lat);
                }, (rs, rowNum) ->
                        new BixiSchema(rs.getInt("nbBikes"), rs.getInt("nbEmptyDocks"), Double.parseDouble(rs.getString("lat")), Double.parseDouble(rs.getString("lon")))
        );

        return new StationsSchema(stations);
    }
}
