package com.medifitbe.jobdata.adapter.out.persistence.entity;

import com.medifitbe.core.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "job_data")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class JobDataEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;         // 사이트 이름
    private String hospitalName;     // 병원명
    private String jobTitle;         // 공고 제목
    private String location;         // 지역
    private String salary;           // 급여
    private String experience;       // 경력
    private String deadline;         // 마감일
    private String workingDays;      // 근무요일
    private String welfare;          // 복지
    private String responsibilities; // 담당업무

    @Column(length = 1000)
    private String link;             // 공고 상세 링크
}
