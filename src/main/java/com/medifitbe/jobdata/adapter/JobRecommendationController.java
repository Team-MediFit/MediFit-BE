package com.medifitbe.jobdata.adapter;

import com.medifitbe.jobdata.application.service.JobRecommendationService;
import com.medifitbe.jobdata.domain.JobRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class JobRecommendationController {

    private final JobRecommendationService jobRecommendationService;

    @GetMapping("/all")
    public Map<String, List<JobRecommendation>> getAllRecommendations() {
        return jobRecommendationService.recommendForAllUsers();
    }
}