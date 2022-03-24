package com.mungto.todo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Todo {

    private final long id;
    private final String content;
    private final boolean isClear;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public Todo(String content, LocalDateTime now) {
        this(0L, content, false, now, now);
    }

    public Todo(long id, String content, boolean isClear, LocalDateTime createAt,
        LocalDateTime updateAt) {
        this.id = id;
        this.content = content;
        this.isClear = isClear;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isClear() {
        return isClear;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
