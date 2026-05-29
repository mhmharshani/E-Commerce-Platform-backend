package edu.icet.ecom.service;

import edu.icet.ecom.event.UserRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;


public interface NotificationService {

    public void publishUserRegisteredEvent(UserRegisteredEvent event);
}
