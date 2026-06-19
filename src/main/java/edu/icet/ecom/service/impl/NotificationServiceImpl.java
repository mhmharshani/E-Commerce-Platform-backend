package edu.icet.ecom.service.impl;

import edu.icet.ecom.event.UserRegisteredEvent;
import edu.icet.ecom.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        eventPublisher.publishEvent(event);
    }
}
