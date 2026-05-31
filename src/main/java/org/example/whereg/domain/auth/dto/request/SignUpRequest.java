package org.example.whereg.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.whereg.domain.user.enums.Department;


public record SignUpRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String name,

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        String password,

        @NotNull(message = "학과는 필수 입력 항목입니다.")
        Department department,

        @NotNull(message = "학년은 필수 입력 항목입니다.")
        Integer grade,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email

) {}
