package com.mungto.todo.dto;

import com.mungto.todo.domain.Todo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoResponse {

    private long id;
    private String content;
    private boolean clear;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public TodoResponse() {
    }

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

    public static List<TodoResponse> listOf(List<Todo> todos) {
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
