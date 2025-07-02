package com.medifitbe.jobdata.adapter;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.medifitbe.jobdata.adapter.in.response.JobDataResponse;
import com.medifitbe.jobdata.application.service.JobRecommendationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Job Recommendation", description = "추천 공고 조회 API")
public class JobRecommendationHistoryController {
    private final JobRecommendationHistoryService historyService;

    @GetMapping
    @Operation(summary = "추천 공고 목록 조회", description = "사용자 이메일을 기반으로 추천된 공고 목록을 조회합니다.")
    public ResponseEntity<List<JobDataResponse>> getRecommendations(
            @Parameter(name = "email", description = "조회할 사용자의 이메일", example = "user@example.com")
            @RequestParam("email") String email) {
        List<JobDataResponse> recommendations = historyService.getRecommendationsByEmail(email);
        return ResponseEntity.ok(recommendations);
    }
}
