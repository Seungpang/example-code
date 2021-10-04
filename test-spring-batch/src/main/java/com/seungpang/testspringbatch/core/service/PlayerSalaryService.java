package com.seungpang.testspringbatch.core.service;

import com.seungpang.testspringbatch.dto.PlayerDto;
import com.seungpang.testspringbatch.dto.PlayerSalaryDto;
import java.time.Year;
import org.springframework.stereotype.Service;

@Service
public class PlayerSalaryService {

    public PlayerSalaryDto calcSalary(PlayerDto player) {
        int salary = (Year.now().getValue() - player.getBirthYear()) * 1000000;
        return PlayerSalaryDto.of(player, salary);
    }
}
