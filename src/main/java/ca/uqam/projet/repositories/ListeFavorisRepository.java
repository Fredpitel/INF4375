package ca.uqam.projet.repositories;

import ca.uqam.projet.schema.ListeFavorisSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Frederic.Pitel on 23/6/16.
 */

@Component
public class ListeFavorisRepository {

    private static final String SELECT_STMT =
            "select camion from listefavoris where idusager = ?;";

    private static final String INSERT_STMT =
            " insert into listefavoris (idusager, camion)"
                    + " values (?, ?)"
                    + " on conflict do nothing"
            ;

    private static final String DELETE_STMT =
            " delete from listefavoris"
                    + " where idusager = ? and camion = ?"
            ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ListeFavorisSchema selectFavoritesByUserID(String userId) {
        List<String> liste = jdbcTemplate.query(SELECT_STMT, ps ->
                ps.setInt(1, Integer.parseInt(userId)), (rs, rowNum) ->
                rs.getString("camion")
        );

        return new ListeFavorisSchema(liste);
    }

    public void insertFavoriteByUserID(String userId, String camion) {
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setInt(1, Integer.parseInt(userId));
            ps.setString(2, camion);
            return ps;
        });
    }

    public int deleteFavoriteByUserID(String userId, String camion) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(DELETE_STMT);
            ps.setInt(1, Integer.parseInt(userId));
            ps.setString(2, camion);
            return ps;
        });
    }
}
