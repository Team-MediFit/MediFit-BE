package com.medifitbe.jobdata.application.service;

import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.adapter.in.response.JobDataResponse;
import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import com.medifitbe.jobdata.adapter.out.persistence.repository.JobDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class JobDataService {

    private final JobDataRepository jobDataRepository;

    public List<JobDataResponse> getAll() {
        return jobDataRepository.findAll()
                .stream()
                .map(JobDataResponse::from)
                .collect(Collectors.toList());
    }

    public JobDataResponse getById(Long id) {
        JobDataEntity entity = jobDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 채용 정보를 찾을 수 없습니다: " + id));
        return JobDataResponse.from(entity);
    }

    public JobDataResponse create(JobDataRequest request) {
        JobDataEntity entity = JobDataEntity.builder()
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
        return JobDataResponse.from(jobDataRepository.save(entity));
    }

    public JobDataResponse update(Long id, JobDataRequest request) {
        if (!jobDataRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID의 채용 정보를 찾을 수 없습니다: " + id);
        }

        JobDataEntity entity = JobDataEntity.builder()
                .id(id)
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

        return JobDataResponse.from(jobDataRepository.save(entity));
    }

    public void delete(Long id) {
        jobDataRepository.deleteById(id);
    }
}