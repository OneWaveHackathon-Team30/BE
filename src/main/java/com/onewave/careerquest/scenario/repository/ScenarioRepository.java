package com.onewave.careerquest.scenario.repository;

import com.onewave.careerquest.scenario.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    // 최신순으로 모든 시나리오를 조회하는 메소드
    List<Scenario> findAllByOrderByCreatedAtDesc();
}
