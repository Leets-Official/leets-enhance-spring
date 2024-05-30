package leets.enhance.domain.user.dto;

import leets.enhance.domain.user.domain.User;

public record UserResponse(
        String email,
        String name

) {
    public static UserResponse of(User user) {
        return new UserResponse(user.getUsername(), user.getName());
    }
}