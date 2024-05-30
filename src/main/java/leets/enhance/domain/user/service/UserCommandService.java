package leets.enhance.domain.user.service;

import leets.enhance.domain.user.DTO.UserDto;
import leets.enhance.global.utils.JwtToken;

public interface UserCommandService {
    UserDto signUp(UserDto.SignUpDto signUpDto);
    JwtToken signIn(String username, String password);
}
