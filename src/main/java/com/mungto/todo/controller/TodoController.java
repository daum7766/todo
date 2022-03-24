package com.mungto.todo.controller;

import com.mungto.todo.dto.TodoRequest;
import com.mungto.todo.dto.TodoResponse;
import com.mungto.todo.service.TodoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> write(@RequestBody TodoRequest todoRequest) {
        TodoResponse todoResponse = todoService.create(todoRequest);
        return ResponseEntity.ok(todoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> read(@PathVariable long id) {
        TodoResponse todoResponse = todoService.findById(id);
        return ResponseEntity.ok(todoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        List<TodoResponse> todoResponses = todoService.findAll();
        return ResponseEntity.ok(todoResponses);
    }

    // 수정하기


    // 삭제하기

}
