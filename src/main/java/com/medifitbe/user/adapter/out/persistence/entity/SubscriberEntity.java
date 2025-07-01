package com.medifitbe.user.adapter.out.persistence.entity;

import com.medifitbe.core.entity.TimeBaseEntity;
import com.medifitbe.user.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Table(name = "subscriber")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SubscriberEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subscriber_id")
    private List<RegionGroupEntity> regionGroups;

    @ElementCollection
    @CollectionTable(name = "subscriber_departments")
    @Enumerated(EnumType.STRING)
    private List<Department> departments;

    @ElementCollection
    @CollectionTable(name = "subscriber_work_types")
    @Enumerated(EnumType.STRING)
    private List<WorkType> workTypes;

    @ElementCollection
    @CollectionTable(name = "subscriber_workdays")
    @Enumerated(EnumType.STRING)
    private List<Workday> workdays;

    @Enumerated(EnumType.STRING)
    private WeekendWork weekendWork;

    @ElementCollection
    @CollectionTable(name = "subscriber_welfare_preferences")
    @Enumerated(EnumType.STRING)
    private List<Welfare> welfarePreferences;

    @Enumerated(EnumType.STRING)
    private WageUnit wageUnit;

    private Integer salaryMin;

    @Enumerated(EnumType.STRING)
    private CareerType careerType;

    private Integer experienceMonths;

}
