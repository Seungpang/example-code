package com.example.Dmaker.dto;

import com.example.Dmaker.entitiy.Developer;
import com.example.Dmaker.type.DeveloperLevel;
import com.example.Dmaker.type.DeveloperSkillType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
            .developerLevel(developer.getDeveloperLevel())
            .developerSkillType(developer.getDeveloperSkillType())
            .memberId(developer.getMemberId())
            .build();
    }
}
