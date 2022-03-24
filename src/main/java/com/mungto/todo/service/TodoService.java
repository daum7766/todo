package com.mungto.todo.service;

import com.mungto.todo.domain.Todo;
import com.mungto.todo.dto.TodoRequest;
import com.mungto.todo.dto.TodoResponse;
import com.mungto.todo.repository.TodoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponse create(TodoRequest todoRequest) {
        Todo todoInfo = todoRequest.toTodo();
        Todo todo = todoRepository.create(todoInfo);
        return TodoResponse.of(todo);
    }

    public TodoResponse findById(long id) {
        Todo todo = todoRepository.findById(id);
        return TodoResponse.of(todo);
    }

    public List<TodoResponse> findAll() {
        List<Todo> todos = todoRepository.findAll();
        return TodoResponse.ListOf(todos);
    }


    // 수정

    // 삭제

}
