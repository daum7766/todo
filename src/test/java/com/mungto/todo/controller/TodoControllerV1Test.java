package com.mungto.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mungto.todo.AcceptanceTest;
import com.mungto.todo.TestMethod;
import com.mungto.todo.dto.TodoRequest;
import com.mungto.todo.dto.TodoResponse;
import com.mungto.todo.service.TodoService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

class TodoControllerV1Test extends AcceptanceTest {

    @Autowired
    private TodoService todoService;
    private final String defaultMessage = "Todo를 생성한다.";

    @Test
    @DisplayName("Todo 아이템을 생성한다.")
    void create() {
        //given
        final String identifier = "todo/v1-create";
        final TodoRequest todoRequest = new TodoRequest(defaultMessage);

        //when
        ExtractableResponse<Response> response = makeResponse(
            "/api/v1/",
            TestMethod.POST,
            identifier,
            todoRequest
            );

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public long createdTodo() {
        return todoService.create(new TodoRequest(defaultMessage));
    }

    @Test
    @DisplayName("Todo 아이템을 읽어온다.")
    void read() {
        //given
        final String identifier = "todo/v1-read";
        final long id = createdTodo();

        //when
        final ExtractableResponse<Response> response = makeResponse(
            "/api/v1/" + id,
            TestMethod.GET,
            identifier
        );
        final TodoResponse todoResponse = response.as(TodoResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(todoResponse.getContent()).isEqualTo(defaultMessage);
        assertThat(todoResponse.isClear()).isFalse();
    }

    @Test
    @DisplayName("모든 Todo 아이템을 가져온다.")
    void readAll() {
        //given
        final String identifier = "todo/v1-read-all";
        final List<Long> expectedIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expectedIds.add(createdTodo());
        }

        //when
        final ExtractableResponse<Response> response = makeResponse(
            "/api/v1/",
            TestMethod.GET,
            identifier
        );
        final List<TodoResponse> responses = response.body()
            .jsonPath()
            .getList(".", TodoResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responses).hasSizeGreaterThanOrEqualTo(expectedIds.size());
    }

    @Test
    @DisplayName("Todo 아이템을 수정한다.")
    void update() {
        //given
        final String identifier = "todo/v1-update";
        final long id = createdTodo();
        final TodoRequest todoRequest = new TodoRequest("Todo 수정", true);

        // when
        final ExtractableResponse<Response> response = makeResponse(
            "/api/v1/" + id,
            TestMethod.PUT,
            identifier,
            todoRequest
        );
        final TodoResponse todoResponse = response.as(TodoResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(todoResponse.getId()).isEqualTo(id);
        assertThat(todoResponse.getContent()).isEqualTo(todoRequest.getContent());
        assertThat(todoResponse.isClear()).isTrue();
        assertThat(todoResponse.getCreateAt()).isNotEqualTo(todoResponse.getUpdateAt());
    }

    @Test
    @DisplayName("Todo 아이템을 삭제한다.")
    void delete() {
        //given
        final String identifier = "todo/v1-delete";
        final long id = createdTodo();

        //when
        final ExtractableResponse<Response> response = makeResponse(
            "api/v1/" + id,
            TestMethod.DELETE,
            identifier
        );

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThatThrownBy(() -> todoService.findById(id))
            .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("존재하지 않는 id로 Todo 아이템을 조회하면 400 에러가 발생한다.")
    void findTodoByIdWhenNonexistentTodo() {
        //given
        final String identifier = "todo/v1-read-error";
        final long id = -1L;

        //when
        final ExtractableResponse<Response> response = makeResponse(
            "/api/v1/" + id,
            TestMethod.GET,
            identifier
        );

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("존재하지 않는 id로 Todo 아이템을 수정하면 400 에러가 발생한다.")
    void updateTodoByIdWhenNonexistentTodo() {
        //given
        final String identifier = "todo/v1-update-error";
        final long id = -1L;
        final TodoRequest todoRequest = new TodoRequest("에러가 나겠지", true);

        //when
        final ExtractableResponse<Response> response = makeResponse(
            "/api/v1/" + id,
            TestMethod.PUT,
            identifier,
            todoRequest
        );

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
