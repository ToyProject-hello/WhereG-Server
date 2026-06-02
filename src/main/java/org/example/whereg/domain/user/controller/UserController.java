package org.example.whereg.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.user.service.UserService;
import org.example.whereg.global.exception.ErrorCode;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.security.JwtProvider;
import org.example.whereg.global.security.TokenParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenParser tokenParser;
    private final JwtProvider jwtProvider;

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(HttpServletRequest request) {
        String accessToken = tokenParser.resolveToken(request);
        if (accessToken == null) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }
        String email = jwtProvider.getEmail(accessToken);
        userService.withdraw(email);
        return ResponseEntity.ok().build();
    }
}
