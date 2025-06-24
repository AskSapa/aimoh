package com.inai.aimoh.dto;

public record AdminCreateOrEditUserDTO(
        String login,
        String password,
        String email,
        String firstName,
        String lastName,
        String role) {
}
