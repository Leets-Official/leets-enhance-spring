package leets.enhance.domain.user.dto;

import leets.enhance.domain.user.domain.User;

import java.util.UUID;

public record LoginResponse(
        UUID userId,
        String name,
        String token
) {
    public static LoginResponse from(User user,String token) {
        return new LoginResponse(user.getUserId(), user.getNickname(), token);
    }
}
