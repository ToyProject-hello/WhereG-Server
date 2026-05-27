package org.example.whereg.domain.auth.dto.request;

import org.example.whereg.domain.user.enums.Department;


public record SignUpRequest(
        String name,
        String password,
        Department department,
        Integer grade,
        String email

) {}
