package leets.enhance.domain.user.dto;

import leets.enhance.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {

    public Long id;
    public String email;
    public String name;

    public static ResponseDto build(User user){
        return ResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
