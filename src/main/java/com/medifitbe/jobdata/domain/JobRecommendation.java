package com.medifitbe.jobdata.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobRecommendation {
    private Long jobId;
    private String hospitalName;
    private String jobTitle;
    private String location;
    private String responsibilities;
    private String link;
    private double similarityScore;
}