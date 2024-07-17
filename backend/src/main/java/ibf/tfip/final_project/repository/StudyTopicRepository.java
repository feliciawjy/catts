package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.StudyTopic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudyTopicRepository {
    private final JdbcTemplate template;

    public StudyTopicRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<StudyTopic> findAllByUserId(Long userId) {
        String sql = "SELECT * FROM study_topics WHERE user_id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, userId);
        List<StudyTopic> topics = new ArrayList<>();
        while (rs.next()) {
            StudyTopic topic = new StudyTopic();
            topic.setId(rs.getLong("id"));
            topic.setUserId(rs.getLong("user_id"));
            topic.setName(rs.getString("name"));
            topic.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            topics.add(topic);
        }
        return topics;
    }

    public StudyTopic create(StudyTopic studyTopic) {
        String sql = "INSERT INTO study_topics (user_id, name, created_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, studyTopic.getUserId());
            ps.setString(2, studyTopic.getName());
            ps.setObject(3, studyTopic.getCreatedAt());
            return ps;
        }, keyHolder);

        studyTopic.setId(keyHolder.getKey().longValue());
        return studyTopic;
    }

    public StudyTopic update(StudyTopic studyTopic) {
        String sql = "UPDATE study_topics SET name = ? WHERE id = ?";
        int updatedRows = template.update(sql, studyTopic.getName(), studyTopic.getId());
        return updatedRows > 0 ? studyTopic : null;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM study_topics WHERE id = ?";
        int deletedRows = template.update(sql, id);
        return deletedRows > 0;
    }
}