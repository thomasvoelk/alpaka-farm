package farm.prototype.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PROTOTYPE — plain JDBC against the real `farm` Postgres db, same throwaway
 * approach as the dashboard prototype (ticket 04). The real jOOQ scaffold is
 * ticket 02's job, not needed for this prototype.
 */
public class FarmDataService {

    private static final String URL = "jdbc:postgresql://localhost:5432/farm";
    private static final String USER = "fiona";
    private static final String PASSWORD = "fiona";

    public static List<AnimalRow> fetchHerdAnimals(String herdName) throws SQLException {
        String sql = """
            SELECT a.id, a.name, a.species, a.birth_date, a.weight_kg, a.fleece_color, p.label
            FROM herds h
            JOIN pens p ON p.herd_id = h.id
            JOIN animals a ON a.pen_id = p.id
            WHERE h.name = ?
            ORDER BY p.label, a.id
            """;

        List<AnimalRow> rows = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, herdName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new AnimalRow(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getDate("birth_date").toLocalDate().toString(),
                        rs.getDouble("weight_kg"),
                        rs.getString("fleece_color"),
                        rs.getString("label")
                    ));
                }
            }
        }
        return rows;
    }
}
