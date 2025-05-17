package com.medifitbe.jobdata.adapter.out.persistence.entity;

import com.medifitbe.core.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Table(name = "validated_job_data")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ValidatedJobDataEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;
    private String hospitalName;
    private String jobTitle;
    private String location;
    private String salary;
    private String experience;
    private String deadline;
    private String workingDays;
    @Column(length = 1000)
    private String welfare;
    private String responsibilities;
    private String link;
}
