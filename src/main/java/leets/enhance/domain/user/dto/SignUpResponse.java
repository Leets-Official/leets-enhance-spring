package leets.enhance.domain.user.dto;


import leets.enhance.domain.user.domain.User;

import java.util.UUID;


public record SignUpResponse (
        UUID userId,
        String name
){
    public static SignUpResponse fromUser(User user) {
        return new SignUpResponse(user.getUserId(), user.getNickname());
    }
}
