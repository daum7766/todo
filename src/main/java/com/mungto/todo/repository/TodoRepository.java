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

    public Todo create(Todo todo) {
        String sql = "INSERT INTO TODO (content, clear, create_at, update_at) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, todo.getContent());
            preparedStatement.setBoolean(2,todo.isClear());
            preparedStatement.setObject(3, todo.getCreateAt());
            preparedStatement.setObject(4, todo.getUpdateAt());
            return preparedStatement;
        }, keyHolder);

        return new Todo(
            Objects.requireNonNull(keyHolder.getKey()).longValue(),
            todo.getContent(),
            todo.isClear(),
            todo.getCreateAt(),
            todo.getUpdateAt()
        );
    }

    public Todo findById(long id) {
        String sql = "SELECT * FROM TODO WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM TODO";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
