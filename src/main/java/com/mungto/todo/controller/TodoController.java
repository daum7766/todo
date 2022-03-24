package com.mungto.todo.controller;

import com.mungto.todo.dto.TodoRequest;
import com.mungto.todo.dto.TodoResponse;
import com.mungto.todo.service.TodoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(final TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.create(todoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> read(@PathVariable long id) {
        return ResponseEntity.ok(todoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable long id, @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.updateById(id, todoRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TodoResponse> delete(@PathVariable long id) {
        todoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
