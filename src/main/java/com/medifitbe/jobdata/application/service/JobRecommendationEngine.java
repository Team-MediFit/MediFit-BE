package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import com.medifitbe.jobdata.domain.JobRecommendation;
import com.medifitbe.user.domain.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.medifitbe.jobdata.adapter.out.persistence.repository.JobRecommendationHistoryRepository;

@Service
public class JobRecommendationEngine {

    private final JobRecommendationHistoryRepository jobRecommendationHistoryRepository;

    @Autowired
    public JobRecommendationEngine(JobRecommendationHistoryRepository jobRecommendationHistoryRepository) {
        this.jobRecommendationHistoryRepository = jobRecommendationHistoryRepository;
    }

    public List<JobRecommendation> recommend(List<JobDataEntity> jobs, Subscriber subscriber) {
        List<JobRecommendation> results = new ArrayList<>();

        // Find the most recent recommendation time for the subscriber (as Instant)
        Instant latestRecommendationTime = jobRecommendationHistoryRepository
                .findTopBySubscriberIdOrderByCreatedAtDesc(subscriber.getId())
                .map(rec -> rec.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant())
                .orElse(Instant.EPOCH); // fallback to a reasonable default

        // Only include jobs created after the latest recommendation time
        List<JobDataEntity> newJobs = jobs.stream()
                .filter(job -> job.getCreatedAt() != null &&
                        job.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().isAfter(latestRecommendationTime))
                .toList();

        for (JobDataEntity job : newJobs) {
            double score = computeSimilarity(subscriber, job);
            if (score >= 0.4) {
                results.add(JobRecommendation.builder()
                        .jobId(job.getId())
                        .hospitalName(job.getHospitalName())
                        .jobTitle(job.getJobTitle())
                        .location(job.getLocation())
                        .responsibilities(job.getResponsibilities())
                        .imageUrl(null)
                        .career(job.getExperience())
                        .link(job.getLink())
                        .salary(job.getSalary())
                        .deadline(job.getDeadline())
                        .similarityScore(score)
                        .build());
            }
        }

        return results.stream()
                .sorted(Comparator.comparing(JobRecommendation::getSimilarityScore).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private double computeSimilarity(Subscriber subscriber, JobDataEntity job) {
        int matched = 0;
        int total = 5;

        // 지역
        if (subscriber.getRegionGroups().stream().anyMatch(g ->
                job.getLocation() != null &&
                        (job.getLocation().contains(g.getMajor().name()) ||
                                g.getMinors().stream().anyMatch(job.getLocation()::contains)))) {
            matched++;
        }

        // 진료과
        if (subscriber.getDepartments().stream()
                .anyMatch(d -> job.getResponsibilities() != null && job.getResponsibilities().contains(d.name()))) {
            matched++;
        }

        // 급여
        if (job.getSalary() != null) {
            try {
                String numeric = job.getSalary().replaceAll("[^0-9]", "");
                if (!numeric.isEmpty()) {
                    int parsedSalary = Integer.parseInt(numeric);
                  
                    if (subscriber.getSalaryMin() <= parsedSalary) {
                        matched++;
                    }
                }
            } catch (Exception ignored) {}
        }

        // 근무요일
        if (job.getWorkingDays() != null &&
                subscriber.getWorkdays().stream().anyMatch(day -> job.getWorkingDays().contains(day.name()))) {
            matched++;
        }

        // 복지
        if (job.getWelfare() != null &&
                subscriber.getWelfarePreferences().stream().anyMatch(w -> job.getWelfare().contains(w.name()))) {
            matched++;
        }

        return (double) matched / total;
    }
}