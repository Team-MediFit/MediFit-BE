package com.medifitbe.jobdata.adapter;

import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.application.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-data")
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService jobPostingServiceservice;

    @PostMapping
    public ResponseEntity<Void> saveJob(@RequestBody JobDataRequest request) {
        jobPostingServiceservice.save(request);
        return ResponseEntity.ok().build();
    }
}