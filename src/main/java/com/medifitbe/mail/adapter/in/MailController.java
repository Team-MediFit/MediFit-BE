package com.medifitbe.mail.adapter.in;

import com.medifitbe.mail.adapter.in.request.BroadcastMailRequest;
import com.medifitbe.mail.application.service.EmailBroadcastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@Tag(name = "메일", description = "메일 전송 API")
public class MailController {

    private final EmailBroadcastService emailBroadcastService;

    @PostMapping("/broadcast")
    @Operation(summary = "전체 이메일 발송", description = "구독자 전체에게 이메일을 전송합니다.")
    public void sendBroadcast(@RequestBody BroadcastMailRequest request) {
        emailBroadcastService.sendToAll(request.getSubject(), request.getContent());
    }
}