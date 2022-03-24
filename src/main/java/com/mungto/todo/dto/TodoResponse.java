package com.mungto.todo.dto;

import com.mungto.todo.domain.Todo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoResponse {

    private final long id;
    private final String content;
    private final boolean clear;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public TodoResponse(long id, String content, boolean clear, LocalDateTime createAt,
        LocalDateTime updateAt) {
        this.id = id;
        this.content = content;
        this.clear = clear;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static TodoResponse of(Todo todo) {
        return new TodoResponse(
            todo.getId(),
            todo.getContent(),
            todo.isClear(),
            todo.getCreateAt(),
            todo.getUpdateAt());
    }

    public static List<TodoResponse> ListOf(List<Todo> todos) {
        List<TodoResponse> todoResponses = new ArrayList<>();
        for (Todo todo : todos) {
            todoResponses.add(TodoResponse.of(todo));
        }
        return todoResponses;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isClear() {
        return clear;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
