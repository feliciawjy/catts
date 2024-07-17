package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class TaskRepository {
    private final JdbcTemplate template;

    public TaskRepository(JdbcTemplate template) {
        this.template = template;
    }

    public LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime() : null;
    }

    private LocalDateTime parseLocalDateTime(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime) : null;
    }

    public List<Task> findAllByTodoListId(Long todoListId) {
        String sql = "SELECT * FROM tasks WHERE todo_list_id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, todoListId);
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTodoListId(rs.getLong("todo_list_id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setDueDate(rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null);
            task.setStartTime(parseLocalDateTime(rs.getString("start_time")));
            task.setEndTime(parseLocalDateTime(rs.getString("end_time")));
            task.setGoogleCalendarEventId(rs.getString("google_calendar_event_id"));
            task.setCompleted(rs.getBoolean("is_completed"));
            task.setCreatedAt(convertToLocalDateTime(rs.getTimestamp("created_at")));
            tasks.add(task);
        }
        return tasks;
    }

    // CRUD
    public Task create(Task task) {
        String sql = "INSERT INTO tasks (todo_list_id, name, description, due_date, start_time, end_time, google_calendar_event_id, is_completed, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, task.getTodoListId());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setObject(4, task.getDueDate());
            ps.setObject(5, task.getStartTime());
            ps.setObject(6, task.getEndTime());
            ps.setString(7, task.getGoogleCalendarEventId());
            ps.setBoolean(8, task.isCompleted());
            ps.setObject(9, task.getCreatedAt());
            return ps;
        }, keyHolder);

        task.setId(keyHolder.getKey().longValue());
        return task;
    }

    public Optional<Task> update(Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, due_date = ?, start_time = ?, end_time = ?, is_completed = ? WHERE id = ?";
        int updatedRows = template.update(sql,
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getStartTime(),
                task.getEndTime(),
                task.isCompleted(),
                task.getId());
        return updatedRows > 0 ? Optional.of(task) : Optional.empty();
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        int deletedRows = template.update(sql, id);
        return deletedRows > 0;
    }

    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, id);
        if (rs.next()) {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTodoListId(rs.getLong("todo_list_id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));   
            task.setDueDate(rs.getDate("due_date").toLocalDate());
            task.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
            task.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
            task.setGoogleCalendarEventId(rs.getString("google_calendar_event_id"));
            task.setCompleted(rs.getBoolean("is_completed"));
            task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return Optional.of(task);
        }
        return Optional.empty();
    }
}