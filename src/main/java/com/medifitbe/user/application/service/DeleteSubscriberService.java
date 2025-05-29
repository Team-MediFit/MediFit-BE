package com.medifitbe.user.application.service;

import com.medifitbe.user.application.port.in.DeleteSubscriberUseCase;
import com.medifitbe.user.application.port.out.DeleteSubscriberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteSubscriberService implements DeleteSubscriberUseCase {

    private final DeleteSubscriberPort deleteSubscriberPort;

    @Override
    public void deleteByEmail(String email) {
        deleteSubscriberPort.deleteByEmail(email);
    }
}