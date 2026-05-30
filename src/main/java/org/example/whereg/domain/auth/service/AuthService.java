package org.example.whereg.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.auth.dto.request.ChangePasswordRequest;
import org.example.whereg.domain.auth.dto.request.SignInRequest;
import org.example.whereg.domain.auth.dto.request.SignUpRequest;
import org.example.whereg.domain.auth.dto.response.TokenResponse;
import org.example.whereg.domain.user.entity.User;
import org.example.whereg.domain.user.enums.Role;
import org.example.whereg.domain.user.repository.UserRepository;
import org.example.whereg.global.exception.ErrorCode;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.security.JwtProperties;
import org.example.whereg.global.security.JwtProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final StringRedisTemplate redisTemplate;

    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new GlobalException(ErrorCode.DUPLICATE_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(encodedPassword)
                .department(request.department())
                .grade(request.grade())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public TokenResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new GlobalException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        redisTemplate.opsForValue().set(
                "RT:" + user.getEmail(),
                refreshToken,
                jwtProperties.refreshExpiration(),
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtProperties.accessExpiration())
                .refreshTokenExpiresIn(jwtProperties.refreshExpiration())
                .build();
    }

    public TokenResponse reissue(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new GlobalException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String email = jwtProvider.getEmail(refreshToken);
        String savedRefreshToken = redisTemplate.opsForValue().get("RT:" + email);

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new GlobalException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtProvider.generateAccessToken(email);
        String newRefreshToken = jwtProvider.generateRefreshToken(email);

        redisTemplate.opsForValue().set(
                "RT:" + email,
                newRefreshToken,
                jwtProperties.refreshExpiration(),
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(jwtProperties.accessExpiration())
                .refreshTokenExpiresIn(jwtProperties.refreshExpiration())
                .build();
    }

    public void signOut(String accessToken) {
        String email = jwtProvider.getEmail(accessToken);
        redisTemplate.delete("RT:" + email);
    }

    public void changePassword(String accessToken, ChangePasswordRequest request) {
        String email = jwtProvider.getEmail(accessToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new GlobalException(ErrorCode.PASSWORD_MISMATCH);
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.changePassword(encodedPassword);
        userRepository.save(user);
    }
}