package com.onewave.careerquest.mypage.service;

import com.onewave.careerquest.mypage.dto.AdoptedSubmissionsResponseDto;
import com.onewave.careerquest.scenarios.dto.ScenarioResponseDto;
import com.onewave.careerquest.scenarios.repository.ScenarioRepository;
import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import com.onewave.careerquest.submissions.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final SubmissionRepository submissionRepository;
    private final ScenarioRepository scenarioRepository;

    public List<SubmissionResponseDto> getMySubmissions(Long userId) {
        return submissionRepository.findByUserAccountIdOrderBySubmittedAtDesc(userId)
                .stream()
                .map(SubmissionResponseDto::new)
                .collect(Collectors.toList());
    }

    public AdoptedSubmissionsResponseDto getAdoptedSubmissions(Long userId) {
        var adoptedSubmissions = submissionRepository.findByUserAccountIdAndIsAdoptedTrueOrderByAdoptedAtDesc(userId);
        
        List<SubmissionResponseDto> submissionDtos = adoptedSubmissions.stream()
                .map(SubmissionResponseDto::new)
                .collect(Collectors.toList());
        
        return new AdoptedSubmissionsResponseDto(adoptedSubmissions.size(), submissionDtos);
    }

    public List<ScenarioResponseDto> getMyScenarios(Long userId) {
        return scenarioRepository.findByCompanyAccountIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ScenarioResponseDto::from)
                .collect(Collectors.toList());
    }
}
