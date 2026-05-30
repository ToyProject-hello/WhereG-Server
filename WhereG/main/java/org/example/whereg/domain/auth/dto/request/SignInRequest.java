package org.example.whereg.domain.auth.dto.request;

public record SignInRequest(
        String password,
        String email
) {}
