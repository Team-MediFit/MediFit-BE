package com.medifitbe.jobdata.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채용 데이터 요청 DTO")
@Builder
public record JobDataRequest(
    @Schema(description = "사이트 이름", example = "인크루트") @NotBlank String siteName,
    @Schema(description = "병원 이름", example = "서울삼성안과의원") @NotBlank String hospitalName,
    @Schema(description = "공고 제목", example = "정규직 간호조무사 모집") @NotBlank String jobTitle,
    @Schema(description = "직종", example ="간호사") String jobType,
    @Schema(description = "지역", example = "서울특별시 강남구") String location,
    @Schema(description = "급여", example = "월급 220만원") String salary,
    @Schema(description = "경력", example = "신입") String experience,
    @Schema(description = "마감일", example = "상시모집") String deadline,
    @Schema(description = "근무 요일", example = "월~금") String workingDays,
    @Schema(description = "복지", example = "4대 보험, 중식 제공") String welfare,
    @Schema(description = "담당 업무", example = "진료 보조, 환자 응대") String responsibilities,
    @Schema(description = "상세 링크", example = "https://example.com/job/1234") String link
) {}
