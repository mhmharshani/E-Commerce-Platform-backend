package edu.icet.ecom.event;

public record UserRegisteredEvent(
        Long userId,
        String email,
        String username,
        String accessLink,
        String createdAt
) {}
