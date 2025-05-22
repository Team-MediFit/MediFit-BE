package com.medifitbe.jobdata.adapter.out.persistence.repository;

import com.medifitbe.jobdata.adapter.out.persistence.entity.JobRecommendationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRecommendationHistoryRepository extends JpaRepository<JobRecommendationHistoryEntity, Long> {
    boolean existsBySubscriberIdAndJobId(Long subscriberId, Long jobId);
    Optional<JobRecommendationHistoryEntity> findTopBySubscriberIdOrderByCreatedAtDesc(Long subscriberId);
}