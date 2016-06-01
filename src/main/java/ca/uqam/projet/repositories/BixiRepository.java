package ca.uqam.projet.repositories;

import java.util.*;
import java.sql.*;

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
}
