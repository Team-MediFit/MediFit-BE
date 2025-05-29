package com.medifitbe.user.application.service;


import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;
import com.medifitbe.user.application.port.in.CreateSubscriberUseCase;
import com.medifitbe.user.application.port.out.CreateSubscriberPort;
import com.medifitbe.user.domain.Subscriber;
import com.medifitbe.user.mapper.SubscriberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSubscriberService implements CreateSubscriberUseCase {

    private final CreateSubscriberPort createSubscriberPort;

    @Override
    @Transactional
    public void create(CreateSubscriberRequest request) {
        Subscriber subscriber = SubscriberMapper.toDomain(request);
        createSubscriberPort.save(subscriber);
    }
}