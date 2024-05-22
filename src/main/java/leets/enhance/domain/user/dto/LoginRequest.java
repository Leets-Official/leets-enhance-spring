package leets.enhance.domain.user.dto;

public record LoginRequest(
        String email,
        String password
) {}
