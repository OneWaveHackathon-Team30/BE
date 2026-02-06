package com.onewave.careerquest.submissions.repository;

import com.onewave.careerquest.submissions.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByScenarioId(Long scenarioId);
}
