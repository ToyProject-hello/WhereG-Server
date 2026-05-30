package org.example.whereg.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
   String accessToken,
   String refreshToken,
   Long accessTokenExpiresIn,
   Long refreshTokenExpiresIn
) {}
