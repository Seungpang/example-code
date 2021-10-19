package com.seungpang.testspringbatch.core.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seungpang.testspringbatch.dto.PlayerDto;
import com.seungpang.testspringbatch.dto.PlayerSalaryDto;
import java.time.Year;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;
    
    @BeforeEach
    public void setup() {
        playerSalaryService = new PlayerSalaryService();
    }

    @Test
    public void calcSalary() {
        //given
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2021);
        Mockito.mockStatic(Year.class).when(Year::now).thenReturn(mockYear);

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        //when
        PlayerSalaryDto result = playerSalaryService.calcSalary(mockPlayer);

        //then
        Assertions.assertEquals(result.getSalary(), 41000000);
    }
}