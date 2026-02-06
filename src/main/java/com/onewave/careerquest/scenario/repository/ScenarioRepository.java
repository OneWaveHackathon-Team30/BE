package com.onewave.careerquest.scenario.repository;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ScenarioRepository extends FirestoreReactiveRepository<Scenario> {
    Flux<Scenario> findAllByOrderByCreatedAtDesc();
}
