package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.in.response.JobDataResponse;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobDataRepository;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobRecommendationHistoryRepository;
import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class JobRecommendationHistoryService {

    private final SubscriberRepository subscriberRepository;
    private final JobRecommendationHistoryRepository jobRecommendationHistoryRepository;
    private final JobDataRepository jobDataRepository; // 가정: jobId로 job 정보 가져올 수 있음

    public List<JobDataResponse> getRecommendationsByEmail(String email) {
        SubscriberEntity subscriber = subscriberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입된 사용자가 없습니다."));

        return jobRecommendationHistoryRepository.findAllBySubscriberId(subscriber.getId())
                .stream()
                .map(history -> jobDataRepository.findById(history.getJobId())
                        .map(JobDataResponse::from)
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }
}