package org.example.whereg.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.user.entity.User;
import org.example.whereg.domain.user.repository.UserRepository;
import org.example.whereg.global.exception.ErrorCode;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.security.JwtProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public void withdraw(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        redisTemplate.delete("RT:" + email);
    }
}
