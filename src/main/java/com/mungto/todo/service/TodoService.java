package com.mungto.todo.service;

import com.mungto.todo.domain.Todo;
import com.mungto.todo.dto.TodoRequest;
import com.mungto.todo.dto.TodoResponse;
import com.mungto.todo.repository.TodoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public long create(final TodoRequest todoRequest) {
        final Todo todoInfo = todoRequest.toTodo();
        return todoRepository.create(todoInfo);
    }

    public TodoResponse findById(final long id) {
        final Todo todo = todoRepository.findById(id);
        return TodoResponse.of(todo);
    }

    public List<TodoResponse> findAll() {
        final List<Todo> todos = todoRepository.findAll();
        return TodoResponse.ListOf(todos);
    }

    public TodoResponse updateById(final long id, final TodoRequest todoRequest) {
        final Todo todoInfo = todoRequest.toTodo();
        todoRepository.updateById(id, todoInfo);
        return findById(id);
    }

    public void deleteById(final long id) {
        todoRepository.deleteById(id);
    }
}
