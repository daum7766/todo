package com.mungto.todo.repository;

import com.mungto.todo.domain.Todo;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Todo> rowMapper = (rs, rowNum) -> {
      return new Todo(
          rs.getLong(1),
          rs.getString(2),
          rs.getBoolean(3),
          rs.getObject(4, LocalDateTime.class),
          rs.getObject(5, LocalDateTime.class)
      );
    };

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long create(final Todo todo) {
        final String sql = "INSERT INTO TODO (content, clear, create_at, update_at) VALUES(?, ?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, todo.getContent());
            preparedStatement.setBoolean(2, false);
            preparedStatement.setObject(3, todo.getCreateAt());
            preparedStatement.setObject(4, todo.getUpdateAt());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Todo findById(final long id) {
        final String sql = "SELECT * FROM TODO WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Todo> findAll() {
        final String sql = "SELECT * FROM TODO";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void updateById(final long id, Todo todo) {
        final String sql = "UPDATE TODO SET content = ?, clear = ?, update_at = ? WHERE id = ?";
        jdbcTemplate.update(
            sql,
            todo.getContent(),
            todo.isClear(),
            LocalDateTime.now(),
            id);
    }

    public void deleteById(final long id) {
        final String sql = "DELETE FROM TODO WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
