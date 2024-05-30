package leets.enhance.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinDto {
    private String email;
    private String password;
    private String name;
}
