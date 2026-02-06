package com.onewave.careerquest.scenarios.repository;

import com.onewave.careerquest.scenarios.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
