package com.medifitbe.user.adapter.out.persistence;

import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import com.medifitbe.user.application.port.in.DeleteSubscriberUseCase;
import com.medifitbe.user.application.port.out.CreateSubscriberPort;
import com.medifitbe.user.application.port.out.DeleteSubscriberPort;
import com.medifitbe.user.domain.Subscriber;
import com.medifitbe.user.mapper.SubscriberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriberPersistenceAdapter implements CreateSubscriberPort, DeleteSubscriberPort {

    private final SubscriberRepository repository;

    @Override
    public void save(Subscriber subscriber) {
        SubscriberEntity entity = SubscriberMapper.toEntity(subscriber);
        repository.save(entity);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}