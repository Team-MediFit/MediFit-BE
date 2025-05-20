package com.medifitbe.mail.application.service;

import com.medifitbe.jobdata.application.service.JobRecommendationService;
import com.medifitbe.jobdata.domain.JobRecommendation;
import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
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
    //@Scheduled(cron = "0 0 */3 * * *")
    @Scheduled(cron = "0 */10 * * * *")
    public void sendRecommendations() {
        Map<String, List<JobRecommendation>> recommendationsMap = jobRecommendationService.recommendForAllUsers();

        for (Map.Entry<String, List<JobRecommendation>> entry : recommendationsMap.entrySet()) {
            String email = entry.getKey();
            List<JobRecommendation> recs = entry.getValue();

            if (recs.isEmpty()) continue;

            StringBuilder body = new StringBuilder("추천 채용 공고 목록입니다:\n\n");
            for (JobRecommendation rec : recs) {
                body.append("- ").append(rec.getJobTitle()).append(" (")
                        .append(rec.getHospitalName()).append(")\n")
                        .append("  링크: ").append(rec.getLink()).append("\n\n");
            }

            mailService.sendTo(email, "맞춤 채용 추천", body.toString());
        }
    }
}
