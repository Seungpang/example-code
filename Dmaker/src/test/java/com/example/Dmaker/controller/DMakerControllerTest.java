package com.example.Dmaker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Dmaker.dto.DeveloperDto;
import com.example.Dmaker.service.DMakerService;
import com.example.Dmaker.type.DeveloperLevel;
import com.example.Dmaker.type.DeveloperSkillType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DMakerController.class) //해당컨트롤러 관련된 빈만 올려서 테스트
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType =
        new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .memberId("memberId1")
            .build();

        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
            .developerLevel(DeveloperLevel.JUNIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .memberId("memberId2")
            .build();

        given(dMakerService.getAllEmployedDevelopers())
            .willReturn(Arrays.asList(seniorDeveloperDto, juniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(
                jsonPath("$.[0].developerSkillType",
                    is(DeveloperSkillType.BACK_END.name()))
            )
            .andExpect(
                jsonPath("$.[0].developerLevel",
                    is(DeveloperLevel.SENIOR.name()))
            )
            .andExpect(
                jsonPath("$.[1].developerSkillType",
                    is(DeveloperSkillType.FRONT_END.name()))
            )
            .andExpect(
                jsonPath("$.[1].developerLevel",
                    is(DeveloperLevel.JUNIOR.name()))
            );


    }
}