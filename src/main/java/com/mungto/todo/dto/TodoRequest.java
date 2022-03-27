package com.mungto.todo.dto;

import com.mungto.todo.domain.Todo;
import java.time.LocalDateTime;

public class TodoRequest {

    private String content;
    private boolean clear;

    public TodoRequest() {
        this(null);
    }

    public TodoRequest(String content) {
        this(content, false);
    }

    public TodoRequest(String content, boolean clear) {
        this.content = content;
        this.clear = clear;
    }

    public Todo toTodo() {
        return new Todo(content, clear, LocalDateTime.now());
    }

    public String getContent() {
        return content;
    }

    public boolean isClear() {
        return clear;
    }
}
