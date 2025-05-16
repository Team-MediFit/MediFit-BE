package com.medifitbe.user.adapter;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import com.medifitbe.user.application.port.in.CreateSubscriberUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriberController implements SubscriberApi {

    private final CreateSubscriberUseCase createSubscriberUseCase;

    @Override
    public ResponseEntity<Void> createSubscriber(@Valid CreateSubscriberRequest request) {
        createSubscriberUseCase.create(request);
        return ResponseEntity.ok().build();
    }
}
