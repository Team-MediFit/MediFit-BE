package com.medifitbe.user.adapter.in.request;

import com.medifitbe.user.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "지원자 정보 등록 요청")
public record CreateSubscriberRequest(

        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank
        String email,

        @Schema(description = "성별 (true: 남성, false: 여성)")
        @NotNull
        Boolean gender,

        @Schema(description = "주요 지역 그룹", example = "[{\"major\": \"서울\", \"minors\": [\"강남구\", \"서초구\"]}, {\"major\": \"경기\", \"minors\": [\"수원시\", \"성남시\"]}]")
        List<RegionGroup> regionGroups,

        @Schema(description = "희망 진료과", example = "[\"내과\", \"정신건강의학과\"]")
        List<Department> departments,

        @Schema(description = "희망 근무 형태", example = "[\"풀타임\"]")
        List<WorkType> workTypes,

        @Schema(description = "근무 가능 요일", example = "[\"월\", \"화\", \"수\"]")
        List<Workday> workdays,

        @Schema(description = "주말 근무 가능 여부", example = "가급적 쉬고 싶음")
        WeekendWork weekendWork,

        @Schema(description = "복지 선호", example = "[\"식대\", \"연차 보장\"]")
        List<Welfare> welfarePreferences,

        @Schema(description = "급여 단위", example = "월급")
        WageUnit wageUnit,

        @Schema(description = "희망 최소 급여", example = "2500000")
        Integer salaryMin,

        @Schema(description = "경력 유형", example = "무관")
        CareerType careerType,

        @Schema(description = "경력 개월 수", example = "0")
        Integer experienceMonths
) {
}