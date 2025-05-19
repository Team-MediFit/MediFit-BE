package com.medifitbe.user.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
public class Subscriber {
    private final Long id;
    private final String email;
    private final Boolean gender;
    private final List<RegionGroup> regionGroups;
    private final List<Department> departments;
    private final List<WorkType> workTypes;
    private final List<Workday> workdays;
    private final WeekendWork weekendWork;
    private final List<Welfare> welfarePreferences;
    private final WageUnit wageUnit;
    private final Integer salaryMin;
    private final Integer salaryMax;
    private final CareerType careerType;
    private final Integer experienceMonths;

    @Builder
    public Subscriber(
            Long id,
            String email,
            Boolean gender,
            List<RegionGroup> regionGroups,
            List<Department> departments,
            List<WorkType> workTypes,
            List<Workday> workdays,
            WeekendWork weekendWork,
            List<Welfare> welfarePreferences,
            WageUnit wageUnit,
            Integer salaryMin,
            Integer salaryMax,
            CareerType careerType,
            Integer experienceMonths
    ) {
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.regionGroups = regionGroups;
        this.departments = departments;
        this.workTypes = workTypes;
        this.workdays = workdays;
        this.weekendWork = weekendWork;
        this.welfarePreferences = welfarePreferences;
        this.wageUnit = wageUnit;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.careerType = careerType;
        this.experienceMonths = experienceMonths;
    }

    public List<String> getRegionMinors() {
        return regionGroups.stream()
                .flatMap(group -> group.getMinors().stream())
                .toList();
    }
}