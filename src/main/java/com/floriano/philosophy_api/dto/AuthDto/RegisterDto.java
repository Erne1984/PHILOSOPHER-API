package com.floriano.philosophy_api.dto.AuthDto;

import com.floriano.philosophy_api.model.User.UserRole;

public record RegisterDto(String email, String password, UserRole role) {
}
