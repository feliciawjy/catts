package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, email);
        
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setGoogleCalendarToken(rs.getString("google_calendar_token"));
            return Optional.of(user);
        }
        
        return Optional.empty();
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password, google_calendar_token) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getGoogleCalendarToken());
    }
}