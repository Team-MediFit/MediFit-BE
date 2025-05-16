package com.medifitbe.user.application.port.out;

import com.medifitbe.user.domain.Subscriber;

public interface CreateSubscriberPort {
    void save(Subscriber subscriber);
}
