package me.seungpang.bank;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import me.seungpang.bank.application.web.DepositRequest;
import me.seungpang.bank.application.web.DepositResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BankIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 입금_요청() throws Exception {
        //given
        BigDecimal depositAmount = BigDecimal.valueOf(1000);
        DepositRequest request = new DepositRequest(depositAmount);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/api/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andReturn().getResponse();

        //then
        DepositResponse depositResponse = objectMapper.readValue(response.getContentAsString(), DepositResponse.class);
        assertAll(() -> {
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(depositResponse.getDepositAmount()).isEqualByComparingTo(request.getDepositAmount());
        });
    }
}
