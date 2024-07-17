package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.StudyTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudyTaskRepository {
    private final JdbcTemplate template;

    public StudyTaskRepository(JdbcTemplate template) {
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

    public List<StudyTask> findAllByStudyTopicId(Long studyTopicId) {
        String sql = "SELECT * FROM study_tasks WHERE study_topic_id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, studyTopicId);
        List<StudyTask> tasks = new ArrayList<>();
        while (rs.next()) {
            tasks.add(mapRowToStudyTask(rs));
        }
        return tasks;
    }

    public StudyTask create(StudyTask task) {
        String sql = "INSERT INTO study_tasks (study_topic_id, name, description, due_date, start_time, end_time, google_calendar_event_id, is_completed, revision_count, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, task.getStudyTopicId());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setObject(4, task.getDueDate());
            ps.setObject(5, task.getStartTime());
            ps.setObject(6, task.getEndTime());
            ps.setString(7, task.getGoogleCalendarEventId());
            ps.setBoolean(8, task.isCompleted());
            ps.setInt(9, task.getRevisionCount());
            ps.setObject(10, task.getCreatedAt());
            return ps;
        }, keyHolder);

        task.setId(keyHolder.getKey().longValue());
        return task;
    }

    public Optional<StudyTask> update(StudyTask task) {
        String sql = "UPDATE study_tasks SET name = ?, description = ?, due_date = ?, start_time = ?, end_time = ?, is_completed = ?, revision_count = ? WHERE id = ?";
        int updatedRows = template.update(sql,
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getStartTime(),
                task.getEndTime(),
                task.isCompleted(),
                task.getRevisionCount(),
                task.getId());
        return updatedRows > 0 ? Optional.of(task) : Optional.empty();
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM study_tasks WHERE id = ?";
        int deletedRows = template.update(sql, id);
        return deletedRows > 0;
    }

    public Optional<StudyTask> findById(Long id) {
        String sql = "SELECT * FROM study_tasks WHERE id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, id);
        if (rs.next()) {
            return Optional.of(mapRowToStudyTask(rs));
        }
        return Optional.empty();
    }

    private StudyTask mapRowToStudyTask(SqlRowSet rs) {
        StudyTask task = new StudyTask();
        task.setId(rs.getLong("id"));
        task.setStudyTopicId(rs.getLong("study_topic_id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        task.setDueDate(rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null);
        task.setStartTime(parseLocalDateTime(rs.getString("start_time")));
        task.setEndTime(parseLocalDateTime(rs.getString("end_time")));
        task.setGoogleCalendarEventId(rs.getString("google_calendar_event_id"));
        task.setCompleted(rs.getBoolean("is_completed"));
        task.setRevisionCount(rs.getInt("revision_count"));
        task.setCreatedAt(convertToLocalDateTime(rs.getTimestamp("created_at")));
        return task;
    }
}