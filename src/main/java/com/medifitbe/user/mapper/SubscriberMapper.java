package com.medifitbe.user.mapper;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import org.springframework.stereotype.Component;
import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.domain.Subscriber;


@Component
public class SubscriberMapper {

    public static SubscriberEntity toEntity(Subscriber subscriber) {
        return SubscriberEntity.builder()
                .id(subscriber.getId())
                .email(subscriber.getEmail())
                .gender(subscriber.getGender())
                .regionMajor(subscriber.getRegionMajor())
                .regionMinors(subscriber.getRegionMinors())
                .departments(subscriber.getDepartments())
                .workTypes(subscriber.getWorkTypes())
                .workdays(subscriber.getWorkdays())
                .weekendWork(subscriber.getWeekendWork())
                .welfarePreferences(subscriber.getWelfarePreferences())
                .wageUnit(subscriber.getWageUnit())
                .salaryMin(subscriber.getSalaryMin())
                .salaryMax(subscriber.getSalaryMax())
                .careerType(subscriber.getCareerType())
                .experienceMonths(subscriber.getExperienceMonths())
                .build();
    }

    public static Subscriber toDomain(CreateSubscriberRequest request) {
        return Subscriber.builder()
                .email(request.email())
                .gender(request.gender())
                .regionMajor(request.regionMajor())
                .regionMinors(request.regionMinors())
                .departments(request.departments())
                .workTypes(request.workTypes())
                .workdays(request.workdays())
                .weekendWork(request.weekendWork())
                .welfarePreferences(request.welfarePreferences())
                .wageUnit(request.wageUnit())
                .salaryMin(request.salaryMin())
                .salaryMax(request.salaryMax())
                .careerType(request.careerType())
                .experienceMonths(request.experienceMonths())
                .build();
    }
}
