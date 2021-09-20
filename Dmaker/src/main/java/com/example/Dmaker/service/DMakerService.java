package com.example.Dmaker.service;

import static com.example.Dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.Dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;
import static com.example.Dmaker.exception.DMakerErrorCode.NO_DEVELOPER;

import com.example.Dmaker.dto.CreateDeveloper;
import com.example.Dmaker.dto.CreateDeveloper.Request;
import com.example.Dmaker.dto.DeveloperDetailDto;
import com.example.Dmaker.dto.DeveloperDto;
import com.example.Dmaker.dto.EditDeveloper;
import com.example.Dmaker.entitiy.Developer;
import com.example.Dmaker.exception.DMakerException;
import com.example.Dmaker.repository.DeveloperRepository;
import com.example.Dmaker.type.DeveloperLevel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
            .developerLevel(request.getDeveloperLevel())
            .developerSkillType(request.getDeveloperSkillType())
            .experienceYears(request.getExperienceYears())
            .memberId(request.getMemberId())
            .name(request.getName())
            .age(request.getAge())
            .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
            .ifPresent((developer -> {
                throw new DMakerException(DUPLICATED_MEMBER_ID);
            }));
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR
            && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
            && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
            .stream().map(DeveloperDto::fromEntity)
            .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
            .map(DeveloperDetailDto::fromEntity)
            .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
            () -> new DMakerException(NO_DEVELOPER)
        );

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

}
