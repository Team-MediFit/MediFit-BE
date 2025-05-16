package com.medifitbe.mail.adapter.in.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BroadcastMailRequest {

    @Schema(description = "메일 제목", example = "메일 제목")
    private String subject;

    @Schema(description = "메일 내용", example = "메일 내용")
    private String content;
}