package org.example.whereg.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.auth.dto.request.ChangePasswordRequest;
import org.example.whereg.domain.auth.dto.request.SignInRequest;
import org.example.whereg.domain.auth.dto.request.SignUpRequest;
import org.example.whereg.domain.auth.dto.response.TokenResponse;
import org.example.whereg.domain.auth.service.AuthService;
import org.example.whereg.domain.auth.service.EmailService;
import org.example.whereg.global.security.TokenParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final TokenParser tokenParser;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            HttpServletRequest request,
            @RequestBody ChangePasswordRequest body) {
        String accessToken = tokenParser.resolveToken(request);
        authService.changePassword(accessToken, body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
            HttpServletRequest request) {
        String accessToken = tokenParser.resolveToken(request);
        return ResponseEntity.ok(authService.reissue(accessToken));
    }
    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@RequestParam String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/email/verify")
    public ResponseEntity<Void> verifyCode(
            @RequestParam String email,
            @RequestParam String code) {
        emailService.verifyCode(email, code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/signout")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        String accessToken = tokenParser.resolveToken(request);
        authService.signOut(accessToken);
        return ResponseEntity.ok().build();
    }
}
