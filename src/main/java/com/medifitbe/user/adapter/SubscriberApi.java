package com.medifitbe.user.adapter;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Subscriber", description = "구독자 관련 API")
public interface SubscriberApi {

    @Operation(summary = "구독자 등록")
    @PostMapping("/api/v1/subscribers")
    ResponseEntity<Void> createSubscriber(@RequestBody @Valid CreateSubscriberRequest request);
}
