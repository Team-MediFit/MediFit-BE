package com.medifitbe.jobdata.adapter.in.response;

import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;

public record JobDataResponse(
        Long id,
        String siteName,
        String hospitalName,
        String jobTitle,
        String location,
        String salary,
        String experience,
        String deadline,
        String workingDays,
        String welfare,
        String responsibilities,
        String link
) {
    public static JobDataResponse from(JobDataEntity jobDataEntity) {
        return new JobDataResponse(
            jobDataEntity.getId(),
            jobDataEntity.getSiteName(),
            jobDataEntity.getHospitalName(),
            jobDataEntity.getJobTitle(),
            jobDataEntity.getLocation(),
            jobDataEntity.getSalary(),
            jobDataEntity.getExperience(),
            jobDataEntity.getDeadline(),
            jobDataEntity.getWorkingDays(),
            jobDataEntity.getWelfare(),
            jobDataEntity.getResponsibilities(),
            jobDataEntity.getLink()
        );
    }
}