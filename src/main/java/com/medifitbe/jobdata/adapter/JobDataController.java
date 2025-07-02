package com.medifitbe.jobdata.adapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.medifitbe.jobdata.adapter.in.response.JobDataResponse;
import com.medifitbe.jobdata.adapter.in.request.JobDataRequest;
import com.medifitbe.jobdata.application.service.JobDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "JobData", description = "채용 데이터 관련 API")
@RestController
@RequestMapping("/api/jobdata")
@RequiredArgsConstructor
public class JobDataController {

    private final JobDataService jobDataService;

    @Operation(summary = "전체 채용 데이터 조회", description = "모든 채용 데이터를 리스트 형태로 반환합니다.")
    @GetMapping
    public ResponseEntity<List<JobDataResponse>> getAll() {
        return ResponseEntity.ok(jobDataService.getAll());
    }

    @Operation(summary = "단일 채용 데이터 조회", description = "ID를 기준으로 특정 채용 데이터를 반환합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<JobDataResponse> getById(
            @Parameter(description = "조회할 채용 데이터 ID", example = "1", required = true)
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(jobDataService.getById(id));
    }

    @Operation(summary = "채용 데이터 생성", description = "요청된 내용을 바탕으로 새로운 채용 데이터를 생성합니다.")
    @PostMapping
    public ResponseEntity<JobDataResponse> create(@RequestBody @Valid JobDataRequest request) {
        JobDataResponse entity = jobDataService.create(request);
        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "채용 데이터 수정", description = "ID에 해당하는 채용 데이터를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "수정 성공"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobDataResponse> update(
            @Parameter(description = "수정할 채용 데이터 ID", example = "1", required = true)
            @PathVariable("id") Long id,
            @RequestBody @Valid JobDataRequest request) {
        JobDataResponse entity = jobDataService.update(id, request);
        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "채용 데이터 삭제", description = "ID에 해당하는 채용 데이터를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 채용 데이터 ID", example = "1", required = true)
            @PathVariable("id") Long id) {
        jobDataService.delete(id);
        return ResponseEntity.noContent().build();
    }
}