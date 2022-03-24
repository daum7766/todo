package com.mungto.todo.dto;

import com.mungto.todo.domain.Todo;
import java.time.LocalDateTime;

public class TodoRequest {

    private String content;
    private boolean clear = false;

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
