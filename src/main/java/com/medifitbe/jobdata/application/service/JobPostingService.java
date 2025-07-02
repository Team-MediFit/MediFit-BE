package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobPostingService {

    private final JobDataRepository jobDataRepository;

    public void save(JobDataRequest request) {
      
        if (jobDataRepository.existsByLink(request.link())) {
            return; // Skip saving if link already exists
        }

        JobDataEntity jobDataEntity = JobDataEntity.builder()
                .siteName(request.siteName())
                .hospitalName(request.hospitalName())
                .jobTitle(request.jobTitle())
                .jobType(request.jobType())
                .location(request.location())
                .salary(request.salary())
                .experience(request.experience())
                .deadline(request.deadline())
                .workingDays(request.workingDays())
                .welfare(request.welfare())
                .responsibilities(request.responsibilities())
                .link(request.link())
                .build();

        jobDataRepository.save(jobDataEntity);
    }
}
