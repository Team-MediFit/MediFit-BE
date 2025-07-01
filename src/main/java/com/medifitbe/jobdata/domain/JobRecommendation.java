package com.medifitbe.jobdata.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobRecommendation {
    private Long jobId;
    private String hospitalName;
    private String jobTitle;
    private String jobType;
    private String location;
    private String responsibilities;
    private String link;
    private double similarityScore;
    private String imageUrl;
    private String career;
    private String salary;
    private String deadline;

    @Builder
    public JobRecommendation(Long jobId, String hospitalName, String jobTitle, String jobType, String location,
                             String responsibilities, String link, double similarityScore,
                             String imageUrl, String career, String salary, String deadline) {
        this.jobId = jobId;
        this.hospitalName = hospitalName;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.location = location;
        this.responsibilities = responsibilities;
        this.link = link;
        this.similarityScore = similarityScore;
        this.imageUrl = imageUrl;
        this.career = career;
        this.salary = salary;
        this.deadline = deadline;
    }
}