package leets.enhance.domain.user.DTO;

import leets.enhance.domain.user.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String email;
    private String password;
    private String swordName;
//    private Item item;
    private List<String> roles;

    static public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .swordName(user.getSwordName())
                .roles(user.getRoles())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .swordName(swordName)
                .roles(roles)
                .build();
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class SignInDto {
        private String id;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpDto {

        private String id;
        private String email;
        private String password;
        private String swordName;
        //private Item item;
        private List<String> roles = new ArrayList<>();

        public User toEntity(String encodedPassword, List<String> roles) {

            return User.builder()
                    .id(id)
                    .password(encodedPassword)
                    .email(email)
                    .swordName(swordName)
                    .roles(roles)
                    .build();
        }
    }
}