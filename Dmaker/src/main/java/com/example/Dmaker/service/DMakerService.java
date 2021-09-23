package com.example.Dmaker.service;

import static com.example.Dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.Dmaker.exception.DMakerErrorCode.NO_DEVELOPER;

import com.example.Dmaker.code.StatusCode;
import com.example.Dmaker.dto.CreateDeveloper;
import com.example.Dmaker.dto.DeveloperDetailDto;
import com.example.Dmaker.dto.DeveloperDto;
import com.example.Dmaker.dto.EditDeveloper;
import com.example.Dmaker.entitiy.Developer;
import com.example.Dmaker.entitiy.RetiredDeveloper;
import com.example.Dmaker.exception.DMakerException;
import com.example.Dmaker.repository.DeveloperRepository;
import com.example.Dmaker.repository.RetiredDeveloperRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        return CreateDeveloper.Response.fromEntity(
            developerRepository.save(createDeveloperFromRequest(request))
        );
    }

    private Developer createDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
            .developerLevel(request.getDeveloperLevel())
            .developerSkillType(request.getDeveloperSkillType())
            .experienceYears(request.getExperienceYears())
            .memberId(request.getMemberId())
            .statusCode(StatusCode.EMPLOYED)
            .name(request.getName())
            .age(request.getAge())
            .build();
    }

    private void validateCreateDeveloperRequest(@NonNull CreateDeveloper.Request request) {
        request.getDeveloperLevel().validateExperienceYears(
            request.getExperienceYears()
        );

        developerRepository.findByMemberId(request.getMemberId())
            .ifPresent((developer -> {
                throw new DMakerException(DUPLICATED_MEMBER_ID);
            }));
    }

    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDeveloperByStatusCodeEquals(StatusCode.EMPLOYED)
            .stream().map(DeveloperDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return DeveloperDetailDto.fromEntity(getDeveloperByMemberId(memberId));
    }

    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findByMemberId(memberId).orElseThrow(
            () -> new DMakerException(NO_DEVELOPER)
        );
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        request.getDeveloperLevel().validateExperienceYears(
            request.getExperienceYears()
        );

        return DeveloperDetailDto.fromEntity(
            setDeveloperFromRequest(request, getDeveloperByMemberId(memberId))
        );
    }

    private Developer setDeveloperFromRequest(EditDeveloper.Request request, Developer developer) {
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return developer;
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 1. EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
            .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
            .memberId(memberId)
            .name(developer.getName())
            .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }
}
