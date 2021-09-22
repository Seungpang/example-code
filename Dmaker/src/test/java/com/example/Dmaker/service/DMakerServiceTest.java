package com.example.Dmaker.service;

import static com.example.Dmaker.type.DeveloperLevel.JUNIOR;
import static com.example.Dmaker.type.DeveloperSkillType.BACK_END;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.Dmaker.code.StatusCode;
import com.example.Dmaker.dto.CreateDeveloper;
import com.example.Dmaker.dto.DeveloperDetailDto;
import com.example.Dmaker.entitiy.Developer;
import com.example.Dmaker.exception.DMakerErrorCode;
import com.example.Dmaker.exception.DMakerException;
import com.example.Dmaker.repository.DeveloperRepository;
import com.example.Dmaker.repository.RetiredDeveloperRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//@SpringBootTest //실제 동작하는 환경과 똑같이 테스트 모든 빈들을 올려놓고 테스트함
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    private final Developer defaultDeveloper = Developer.builder()
        .developerLevel(JUNIOR)
        .developerSkillType(BACK_END)
        .experienceYears(2)
        .statusCode(StatusCode.EMPLOYED)
        .name("name")
        .age(20)
        .build();

    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
        .developerLevel(JUNIOR)
        .developerSkillType(BACK_END)
        .experienceYears(2)
        .memberId("memberId")
        .name("name")
        .age(20)
        .build();

    @Test
    void testSomething() {

        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.of(defaultDeveloper));

        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(JUNIOR, developerDetail.getDeveloperLevel());
        assertEquals(BACK_END, developerDetail.getDeveloperSkillType());
        assertEquals(2, developerDetail.getExperienceYears());
    }

    @Test
    void createDeveloperTest_success() {
        //given
        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
            ArgumentCaptor.forClass(Developer.class);

        //when
        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);

        //then
        verify(developerRepository, times(1))
            .save(captor.capture());
        Developer savedDeveloper = captor.getValue();
        assertEquals(JUNIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(BACK_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(2, savedDeveloper.getExperienceYears());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        //given

        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.of(defaultDeveloper));

        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class,
            () -> dMakerService.createDeveloper(defaultCreateRequest));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }
}