package com.medifitbe.mail.application.service;

import com.medifitbe.jobdata.application.service.JobRecommendationService;
import com.medifitbe.jobdata.domain.JobRecommendation;
import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailBroadcastService {
    private final SubscriberRepository subscriberRepository;
    private final MailService mailService;
    private final JobRecommendationService jobRecommendationService;

    public void sendToAll(String subject, String content) {
        List<SubscriberEntity> subscribers = subscriberRepository.findAll();

        for (SubscriberEntity subscriber : subscribers) {
            mailService.sendTo(subscriber.getEmail(), subject, content);
        }
    }
    @Scheduled(cron = "0 0 9,12,15,18,21 * * *", zone = "Asia/Seoul")
    public void sendRecommendations() {
        System.out.println("스케줄링 실행");
        Map<String, List<JobRecommendation>> recommendationsMap = jobRecommendationService.recommendForAllUsers();

        for (Map.Entry<String, List<JobRecommendation>> entry : recommendationsMap.entrySet()) {
            String email = entry.getKey();
            List<JobRecommendation> recs = entry.getValue();

            if (recs.isEmpty()) continue;

            mailService.sendRecommendationsWithHtml(email, recs);
        }
    }
}
