package com.example.Dmaker.dto;

import com.example.Dmaker.code.StatusCode;
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
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private StatusCode statusCode;
    private String name;
    private Integer age;

    public static DeveloperDetailDto fromEntity(Developer developer) {
        return DeveloperDetailDto.builder()
            .developerLevel(developer.getDeveloperLevel())
            .developerSkillType(developer.getDeveloperSkillType())
            .experienceYears(developer.getExperienceYears())
            .memberId(developer.getMemberId())
            .statusCode(developer.getStatusCode())
            .name(developer.getName())
            .age(developer.getAge())
            .build();
    }
}
