package org.example.whereg.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.global.exception.ErrorCode;
import org.example.whereg.global.exception.GlobalException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private String createCode() {
        int code = SECURE_RANDOM.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    private void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[어딨G] 이메일 인증번호");
        message.setText("인증 번호 :" + code);
        mailSender.send(message);
    }

    public void sendVerificationEmail(String email) {
        String code = createCode();
        redisTemplate.opsForValue().set("EMAIL:" + email, code, 5, TimeUnit.MINUTES);
        sendEmail(email, code);
    }

    public void verifyCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get("EMAIL:" + email);
        if (savedCode == null) {
            throw new GlobalException(ErrorCode.EMAIL_CODE_EXPIRED);
        }
        if (!savedCode.equals(code)) {
            throw new GlobalException(ErrorCode.EMAIL_CODE_MISMATCH);
        }
        redisTemplate.delete("EMAIL:" + email);
        redisTemplate.opsForValue().set("EMAIL_VERIFIED:" + email, "true", 10, TimeUnit.MINUTES);
    }

    public void sendPasswordResetEmail(String email) {
        String code = createCode();
        redisTemplate.opsForValue().set("PW_EMAIL:" + email, code, 5, TimeUnit.MINUTES);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[어딨G] 비밀번호 재설정 인증번호");
        message.setText("인증 번호 :" + code);
        mailSender.send(message);
    }

    public void verifyPasswordResetCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get("PW_EMAIL:" + email);
        if (savedCode == null) {
            throw new GlobalException(ErrorCode.EMAIL_CODE_EXPIRED);
        }
        if (!savedCode.equals(code)) {
            throw new GlobalException(ErrorCode.EMAIL_CODE_MISMATCH);
        }
        redisTemplate.delete("PW_EMAIL:" + email);
        redisTemplate.opsForValue().set("PW_VERIFIED:" + email, "true", 10, TimeUnit.MINUTES);
    }

}
