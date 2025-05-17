package com.medifitbe.jobdata.adapter;


import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.adapter.in.response.JobDataResponse;
import com.medifitbe.jobdata.application.service.JobValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
@Tag(name = "Job Validation", description = "채용 공고 검증 관련 API")
public class JobValidationController {

    private final JobValidationService validationService;

    @Operation(summary = "검증되지 않은 채용 공고 목록 조회")
    @GetMapping("/unvalidated")
    public List<JobDataResponse> getUnvalidated() {
        return validationService.getAllUnvalidated()
                .stream()
                .map(JobDataResponse::from)
                .toList();
    }

    @Operation(summary = "채용 공고 검증 및 저장")
    @PostMapping("/validate")
    public ResponseEntity<Void> validateJob(
            @RequestBody JobDataRequest modifiedData) {
        validationService.validateAndSave(modifiedData);
        return ResponseEntity.ok().build();
    }
}