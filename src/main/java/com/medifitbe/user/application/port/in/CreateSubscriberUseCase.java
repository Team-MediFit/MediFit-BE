package com.medifitbe.user.application.port.in;

import com.medifitbe.user.adapter.in.request.CreateSubscriberRequest;

public interface CreateSubscriberUseCase {
    void create(CreateSubscriberRequest request);
}
