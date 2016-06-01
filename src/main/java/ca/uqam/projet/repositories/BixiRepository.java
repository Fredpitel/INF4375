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

    public void truncate(){
        jdbcTemplate.execute("ALTER SEQUENCE bixi_idbixi_seq RESTART WITH 1");
        jdbcTemplate.execute("TRUNCATE TABLE bixi;");
    }

    public int insert(BixiSchema bixi){
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setString(1, "POINT(" + bixi.getLa() + " " + bixi.getLo() + ")");
            ps.setInt(2, bixi.getBa());
            ps.setInt(3, bixi.getDa());
            return ps;
        });
    }

    public StationsSchema select(double lat, double lon){
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM bixi WHERE GeometryType(ST_Centroid(coord)) = 'POINT' AND ST_Distance_Sphere( ST_Point(ST_X(ST_Centroid(coord)), ST_Y(ST_Centroid(coord))), (ST_MakePoint(" + lat + ", " + lon + "))) <= 200;");
        StationsSchema stations = new StationsSchema();
        ArrayList<BixiSchema> bixis = new ArrayList<BixiSchema>();

        for(Map<String, Object> row : rows) {
            BixiSchema bixi = new BixiSchema();
            bixi.setBa((int)row.get("nbBikes"));
            bixi.setDa((int)row.get("nbEmptyDocks"));

            Map<String, Object> geometry = jdbcTemplate.queryForMap("SELECT ST_X(coord), ST_Y(coord) FROM bixi WHERE idBixi = " + row.get("idBixi") + ";");

            double[] coordDouble = new double[2];
            coordDouble[0] = Double.parseDouble("" + geometry.get("st_x"));
            coordDouble[1] = Double.parseDouble("" + geometry.get("st_y"));

            bixi.setLa(coordDouble[0]);
            bixi.setLo(coordDouble[1]);

            bixis.add(bixi);
        }

        stations.setStations(bixis);

        return stations;
    }
}
