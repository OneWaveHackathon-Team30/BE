package com.onewave.careerquest.scenarios.repository;

import com.onewave.careerquest.scenarios.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findAllByOrderByCreatedAtDesc();
    List<Scenario> findByCompanyAccountIdOrderByCreatedAtDesc(Long companyAccountId);
}
