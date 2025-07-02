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
@Transactional
@RequiredArgsConstructor
public class JobRecommendationService {

    private final JobDataRepository jobDataRepository;
    private final SubscriberRepository subscriberRepository;
    private final JobRecommendationEngine recommendationEngine;
    private final SubscriberMapper subscriberMapper;
    private final JobRecommendationHistoryRepository historyRepository;


    public Map<String, List<JobRecommendation>> recommendForAllUsers() {
        List<JobDataEntity> allJobs = jobDataRepository.findAll();
        List<Subscriber> allSubscribers = subscriberRepository.findAll()
                .stream()
                .map(subscriberMapper::toDomain)
                .toList();
        // System.out.println("총 구독자 수: " + allSubscribers.size());
        // System.out.println("총 채용 공고 수: " + allJobs.size());

        Map<String, List<JobRecommendation>> result = new HashMap<>();
        allSubscribers.forEach(subscriber -> {
            List<JobRecommendation> recommendations = recommendationEngine.recommend(allJobs, subscriber);
            // System.out.println("[" + subscriber.getEmail() + "] 추천된 개수: " + recommendations.size());

            // 중복 제거
            List<JobRecommendation> filtered = recommendations.stream()
                    .filter(rec -> {
                        boolean alreadyExists = historyRepository.existsBySubscriberIdAndJobId(subscriber.getId(), rec.getJobId());
                        // if (alreadyExists) System.out.println("🚫 이미 추천된 jobId: " + rec.getJobId());
                        return !alreadyExists;
                    })
                    .toList();
            // System.out.println("[" + subscriber.getEmail() + "] 신규 추천 개수: " + filtered.size());

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