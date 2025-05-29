package com.medifitbe.user.adapter;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import com.medifitbe.user.adapter.in.request.DeleteSubscriberRequest;
import com.medifitbe.user.application.port.in.CreateSubscriberUseCase;
import com.medifitbe.user.application.port.in.DeleteSubscriberUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscribers")
@RequiredArgsConstructor
public class SubscriberController implements SubscriberApi {

    private final CreateSubscriberUseCase createSubscriberUseCase;
    private final DeleteSubscriberUseCase deleteSubscriberUseCase;

    @PostMapping
    public ResponseEntity<Void> createSubscriber(@Valid CreateSubscriberRequest request) {
        createSubscriberUseCase.create(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteByEmail(@Valid DeleteSubscriberRequest request) {
        deleteSubscriberUseCase.deleteByEmail(request.email());
        return ResponseEntity.noContent().build();
    }

}
