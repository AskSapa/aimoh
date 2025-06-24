package com.inai.aimoh.dto;

public record AuthenticationRequest(
        String login,
        String password
) {
}
