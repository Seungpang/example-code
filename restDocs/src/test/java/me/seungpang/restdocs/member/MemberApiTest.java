package me.seungpang.restdocs.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
class MemberApiTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(
        final WebApplicationContext context,
        final RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .build();
    }

    @Test
    void member_page_test() throws Exception {
        mockMvc.perform(
                get("/api/members")
                    .param("size", "10")
                    .param("page", "0")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk());
    }

    @Test
    void member_get() throws Exception {
        mockMvc.perform(
                get("/api/members/{id}",1L)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk());
    }

    @Test
    void member_create() throws Exception {
        mockMvc.perform(
                post("/api/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                        + "  \"email\": \"obey1342@gmail.com\",\n"
                        + "  \"name\": \"seungpang\"\n"
                        + "}")
            )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk());
    }

    @Test
    void member_modify() throws Exception {
        mockMvc.perform(
                put("/api/members/{id}",1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n"
                        + "  \"name\": \"seung\"\n"
                        + "}")
            )
            .andDo(print())
            .andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"))
            .andExpect(status().isOk());
    }
}