package com.mungto.todo.dto;

import com.mungto.todo.domain.Todo;
import java.time.LocalDateTime;

public class TodoRequest {

    private String content;

    public TodoRequest() {
    }

    public TodoRequest(String content) {
        this.content = content;
    }

    public Todo toTodo() {
        return new Todo(content, LocalDateTime.now());
    }

    public String getContent() {
        return content;
    }
}
