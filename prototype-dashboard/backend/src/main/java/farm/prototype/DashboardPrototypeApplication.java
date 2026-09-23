package farm.prototype;

// PROTOTYPE — wayfinder ticket 04 (Spring+Angular dashboard slice). Throwaway.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DashboardPrototypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardPrototypeApplication.class, args);
    }
}

@RestController
@CrossOrigin(origins = "http://localhost:4200")
class FarmController {

    private final DataSource dataSource;

    FarmController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // One rich nested blob — herds -> pens -> animals, each animal carrying
    // its latest shearing + sale (if any). Frontend variants slice this differently.
    @GetMapping("/api/farm-overview")
    List<Map<String, Object>> farmOverview() throws Exception {
        try (Connection c = dataSource.getConnection()) {
            List<Map<String, Object>> herds = queryList(c, """
                SELECT id, name, farm_section FROM herds ORDER BY name
                """);

            for (Map<String, Object> herd : herds) {
                List<Map<String, Object>> pens = queryList(c, """
                    SELECT id, label, capacity FROM pens WHERE herd_id = ? ORDER BY label
                    """, herd.get("id"));
                herd.put("pens", pens);

                for (Map<String, Object> pen : pens) {
                    List<Map<String, Object>> animals = queryList(c, """
                        SELECT id, name, species, birth_date, weight_kg, fleece_color
                        FROM animals WHERE pen_id = ? ORDER BY name
                        """, pen.get("id"));
                    pen.put("animals", animals);

                    for (Map<String, Object> animal : animals) {
                        List<Map<String, Object>> shearings = queryList(c, """
                            SELECT sr.id, sr.sheared_on, sr.fleece_weight_kg,
                                   fs.buyer, fs.sale_date, fs.price
                            FROM shearing_records sr
                            LEFT JOIN fleece_sales fs ON fs.shearing_record_id = sr.id
                            WHERE sr.animal_id = ?
                            ORDER BY sr.sheared_on DESC
                            LIMIT 1
                            """, animal.get("id"));
                        animal.put("latestShearing", shearings.isEmpty() ? null : shearings.get(0));
                    }
                }
            }
            return herds;
        }
    }

    private List<Map<String, Object>> queryList(Connection c, String sql, Object... params) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                var meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= cols; i++) {
                        row.put(meta.getColumnLabel(i).toLowerCase(), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}
