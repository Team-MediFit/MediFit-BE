package com.medifitbe.user.mapper;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import com.medifitbe.user.adapter.out.persistence.entity.RegionGroupEntity;
import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.domain.RegionGroup;
import com.medifitbe.user.domain.Subscriber;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscriberMapper {

    public static SubscriberEntity toEntity(Subscriber subscriber) {
        List<RegionGroupEntity> regionGroupEntities = subscriber.getRegionGroups().stream()
                .map(group -> RegionGroupEntity.builder()
                        .major(group.getMajor())
                        .minors(group.getMinors())
                        .build())
                .collect(Collectors.toList());

        return SubscriberEntity.builder()
                .id(subscriber.getId())
                .email(subscriber.getEmail())
                .gender(subscriber.getGender())
                .regionGroups(regionGroupEntities)
                .departments(subscriber.getDepartments())
                .workTypes(subscriber.getWorkTypes())
                .workdays(subscriber.getWorkdays())
                .weekendWork(subscriber.getWeekendWork())
                .welfarePreferences(subscriber.getWelfarePreferences())
                .wageUnit(subscriber.getWageUnit())
                .salaryMin(subscriber.getSalaryMin())
                .careerType(subscriber.getCareerType())
                .experienceMonths(subscriber.getExperienceMonths())
                .build();
    }

    public static Subscriber toDomain(CreateSubscriberRequest request) {
        List<RegionGroup> regionGroups = request.regionGroups();

        return Subscriber.builder()
                .email(request.email())
                .gender(request.gender())
                .regionGroups(regionGroups)
                .departments(request.departments())
                .workTypes(request.workTypes())
                .workdays(request.workdays())
                .weekendWork(request.weekendWork())
                .welfarePreferences(request.welfarePreferences())
                .wageUnit(request.wageUnit())
                .salaryMin(request.salaryMin())
                .careerType(request.careerType())
                .experienceMonths(request.experienceMonths())
                .build();
    }
    public Subscriber toDomain(SubscriberEntity entity) {
        List<RegionGroup> regionGroups = entity.getRegionGroups().stream()
                .map(groupEntity -> new RegionGroup(groupEntity.getMajor(), groupEntity.getMinors()))
                .collect(Collectors.toList());

        return Subscriber.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .regionGroups(regionGroups)
                .departments(entity.getDepartments())
                .workTypes(entity.getWorkTypes())
                .workdays(entity.getWorkdays())
                .weekendWork(entity.getWeekendWork())
                .welfarePreferences(entity.getWelfarePreferences())
                .wageUnit(entity.getWageUnit())
                .salaryMin(entity.getSalaryMin())
                .salaryMax(entity.getSalaryMax())
                .careerType(entity.getCareerType())
                .experienceMonths(entity.getExperienceMonths())
                .build();
    }
}