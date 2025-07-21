package com.floriano.login_system_backend_maven.dto.AuthDto;

import com.floriano.login_system_backend_maven.model.User.UserRole;

public record RegisterDto(String email, String password, UserRole role) {
}
