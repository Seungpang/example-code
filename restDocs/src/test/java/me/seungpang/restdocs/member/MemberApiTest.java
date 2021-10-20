package me.seungpang.restdocs.member;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.seungpang.restdocs.TestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberApiTest extends TestSupport {

    @Test
    void member_page_test() throws Exception {
        mockMvc.perform(
                get("/api/members")
                    .param("size", "10")
                    .param("page", "0")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestParameters(
                        parameterWithName("size").optional().description("size"),
                        parameterWithName("page").optional().description("page")
                    )
                )
            );
    }

    @Test
    void member_get() throws Exception {
        mockMvc.perform(
                get("/api/members/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("Member Id")
                    ),
                    responseFields(
                        fieldWithPath("id").description("ID"),
                        fieldWithPath("name").description("name"),
                        fieldWithPath("email").description("email")
                    )
                )
            );
    }

    @Test
    void member_create() throws Exception {
        mockMvc.perform(
                post("/api/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson("/json/member-api/member-create.json"))
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("name").description("name"),
                        fieldWithPath("email").description("email")
                    )
                )
            );
    }

    @Test
    void member_modify() throws Exception {
        mockMvc.perform(
                put("/api/members/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson("/json/member-api/member-modify.json"))
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("Member Id")
                    ),
                    requestFields(
                        fieldWithPath("name").description("name")
                    )
                )
            );
    }
}