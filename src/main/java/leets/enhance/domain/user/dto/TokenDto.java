package leets.enhance.domain.user.dto;

import leets.enhance.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    public Long id;
    public String accessToken;

    public static TokenDto build(User user, String accessToken){
        return TokenDto.builder()
                .id(user.getId())
                .accessToken(accessToken)
                .build();
    }
}
