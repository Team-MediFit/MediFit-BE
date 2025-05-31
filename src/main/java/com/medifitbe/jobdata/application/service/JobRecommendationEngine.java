package com.medifitbe.jobdata.application.service;
import java.util.HashSet;
import java.util.Set;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // 지역 (Region) Jaccard similarity - 필수 조건 + 마이너 포함 필수
        boolean regionMatched = false;

        if (job.getLocation() != null) {
            String[] locationTokens = job.getLocation().split(" ");
            Set<String> jobLocationSet = new HashSet<>(List.of(locationTokens));

            for (var g : subscriber.getRegionGroups()) {
                // 마이너 지역 필수 포함
                boolean minorMatched = g.getMinors().stream().anyMatch(jobLocationSet::contains);
                if (!minorMatched) continue;

                // 메이저 + 마이너 기준으로 유사도 검사
                Set<String> subscriberRegionSet = new HashSet<>();
                subscriberRegionSet.add(g.getMajor().name());
                subscriberRegionSet.addAll(g.getMinors());

                Set<String> intersection = new HashSet<>(jobLocationSet);
                intersection.retainAll(subscriberRegionSet);

                Set<String> union = new HashSet<>(jobLocationSet);
                union.addAll(subscriberRegionSet);

                double jaccard = (double) intersection.size() / union.size();
                if (jaccard >= 0.3) {
                    regionMatched = true;
                    break;
                }
            }
        }

        if (!regionMatched) return 0.0;
        matched++;

        // 진료과
        if (subscriber.getDepartments().stream()
                .anyMatch(d -> job.getResponsibilities() != null && job.getResponsibilities().contains(d.name()))) {
            matched++;
        }

        // 급여
        if (job.getSalary() != null) {
            try {
                String salaryStr = job.getSalary();
                Pattern pattern = Pattern.compile("(\\d+[,.]?\\d*)");
                Matcher matcher = pattern.matcher(salaryStr.replace(",", ""));

                if (matcher.find()) {
                    String firstNumericStr = matcher.group(1);
                    int parsedSalary = (int)(Double.parseDouble(firstNumericStr) * 10000); // 만 단위 기준 환산

                    // 공고 급여 단위 추정
                    boolean isJobHourly = salaryStr.contains("시") || salaryStr.contains("시급");
                    boolean isJobMonthly = salaryStr.contains("월") || salaryStr.contains("월급");
                    boolean isJobYearly = salaryStr.contains("연") || salaryStr.contains("연봉");

                    // 구독자 희망 급여 단위
                    String subscriberUnit = String.valueOf(subscriber.getWageUnit()); // "월급", "연봉", or "시급"
                    int subscriberMin = subscriber.getSalaryMin();

                    // 단위 환산: 모두 subscriber 단위로 맞추기
                    if ("월급".equals(subscriberUnit)) {
                        if (isJobYearly) parsedSalary = parsedSalary / 12;
                        else if (isJobHourly) parsedSalary = parsedSalary * 209; // 시급 → 월급 (기준 근무시간 209h)
                    } else if ("연봉".equals(subscriberUnit)) {
                        if (isJobMonthly) parsedSalary = parsedSalary * 12;
                        else if (isJobHourly) parsedSalary = parsedSalary * 209 * 12;
                    } else if ("시급".equals(subscriberUnit)) {
                        if (isJobMonthly) parsedSalary = parsedSalary / 209;
                        else if (isJobYearly) parsedSalary = parsedSalary / (209 * 12);
                    }

                    if (subscriberMin <= parsedSalary) {
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