package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobDataRepository;
import com.medifitbe.jobdata.domain.JobRecommendation;

import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import com.medifitbe.user.domain.Subscriber;
import com.medifitbe.user.mapper.SubscriberMapper;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobRecommendationHistoryRepository;
import com.medifitbe.jobdata.adapter.out.persistence.entity.JobRecommendationHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobRecommendationService {

    private final JobDataRepository jobDataRepository;
    private final SubscriberRepository subscriberRepository;
    private final JobRecommendationEngine recommendationEngine;
    private final SubscriberMapper subscriberMapper;
    private final JobRecommendationHistoryRepository historyRepository;

    @Transactional
    public Map<String, List<JobRecommendation>> recommendForAllUsers() {
        List<JobDataEntity> allJobs = jobDataRepository.findAll();
        List<Subscriber> allSubscribers = subscriberRepository.findAll()
                .stream()
                .map(subscriberMapper::toDomain)
                .toList();

        Map<String, List<JobRecommendation>> result = new HashMap<>();
        allSubscribers.forEach(subscriber -> {
            List<JobRecommendation> recommendations = recommendationEngine.recommend(allJobs, subscriber);

            // 중복 제거
            List<JobRecommendation> filtered = recommendations.stream()
                    .filter(rec -> !historyRepository.existsBySubscriberIdAndJobId(subscriber.getId(), rec.getJobId()))
                    .toList();

            // 추천 이력 저장
            filtered.stream()
                .map(rec -> JobRecommendationHistoryEntity.builder()
                    .subscriberId(subscriber.getId())
                    .jobId(rec.getJobId())
                    .build())
                .forEach(historyRepository::save);

            result.put(subscriber.getEmail(), filtered);
        });

        return result;
    }
}