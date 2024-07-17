package ibf.tfip.final_project.repository;

import ibf.tfip.final_project.model.TodoList;
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
public class TodoListRepository {
    private final JdbcTemplate template;

    public TodoListRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<TodoList> findAllByUserId(Long userId) {
        String sql = "SELECT * FROM todo_lists WHERE user_id = ?";
        SqlRowSet rs = template.queryForRowSet(sql, userId);
        List<TodoList> lists = new ArrayList<>();
        while (rs.next()) {
            TodoList list = new TodoList();
            list.setId(rs.getLong("id"));
            list.setUserId(rs.getLong("user_id"));
            list.setName(rs.getString("name"));
            list.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            lists.add(list);
        }
        return lists;
    }

    // CRUD
       public TodoList create(TodoList todoList) {
        String sql = "INSERT INTO todo_lists (user_id, name, created_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, todoList.getUserId());
            ps.setString(2, todoList.getName());
            ps.setObject(3, todoList.getCreatedAt());
            return ps;
        }, keyHolder);

        todoList.setId(keyHolder.getKey().longValue());
        return todoList;
    }

    public TodoList update(TodoList todoList) {
        String sql = "UPDATE todo_lists SET name = ? WHERE id = ?";
        int updatedRows = template.update(sql, todoList.getName(), todoList.getId());
        return updatedRows > 0 ? todoList : null;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM todo_lists WHERE id = ?";
        int deletedRows = template.update(sql, id);
        return deletedRows > 0;
    }

}