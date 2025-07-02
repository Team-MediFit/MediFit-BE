package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import com.medifitbe.jobdata.adapter.out.persistence.entity.ValidatedJobDataEntity;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobDataRepository;
import com.medifitbe.jobdata.adapter.out.persistence.repository.ValidatedJobDataRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JobValidationService {

    private final JobDataRepository jobDataRepository;
    private final ValidatedJobDataRepository validatedRepository;

    public List<JobDataEntity> getAllUnvalidated() {
        return jobDataRepository.findAll();
    }

    public void validateAndSave(JobDataRequest modifiedData) {
        ValidatedJobDataEntity validated = ValidatedJobDataEntity.builder()
                .siteName(modifiedData.siteName())
                .hospitalName(modifiedData.hospitalName())
                .jobTitle(modifiedData.jobTitle())
                .location(modifiedData.location())
                .salary(modifiedData.salary())
                .experience(modifiedData.experience())
                .deadline(modifiedData.deadline())
                .workingDays(modifiedData.workingDays())
                .welfare(modifiedData.welfare())
                .responsibilities(modifiedData.responsibilities())
                .link(modifiedData.link())
                .build();

        validatedRepository.save(validated);
    }
}