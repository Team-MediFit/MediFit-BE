package com.medifitbe.mail.application.service;

import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import com.medifitbe.user.adapter.out.persistence.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailBroadcastService {
    private final SubscriberRepository subscriberRepository;
    private final MailService mailService;

    public void sendToAll(String subject, String content) {
        List<SubscriberEntity> subscribers = subscriberRepository.findAll();

        for (SubscriberEntity subscriber : subscribers) {
            mailService.sendTo(subscriber.getEmail(), subject, content);
        }
    }
}
