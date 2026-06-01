package org.example.whereg.domain.auth.dto.request;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {}
