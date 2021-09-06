package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TodoController.class) //해당 controller쪽 관련된 bean들만 올려서 테스트 진행할 때 (Controller Advice, Filter들도 올라감)
class TodoControllerTest {

    @Autowired
    MockMvc mvc; //메서드의 호출을 가상으로 만들어 줄때

    @MockBean
    TodoService todoService;

    private TodoEntity expected;

    @BeforeEach
    void setup() {
        this.expected = new TodoEntity();
        this.expected.setId(1L);
        this.expected.setTitle("TEST");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
    }

    @Test
    void create() throws Exception {
        when(this.todoService.add(any(TodoRequest.class)))
            .then((i) -> {
                TodoRequest reqeust = i.getArgument(0, TodoRequest.class);
                return new TodoEntity(this.expected.getId(), reqeust.getTitle(),
                    this.expected.getOrder(), this.expected.getCompleted());
            });

        TodoRequest request = new TodoRequest();
        request.setTitle("title");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void readOne() {
    }
    
}