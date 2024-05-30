package leets.enhance.domain.user.controller;

import leets.enhance.domain.user.DTO.UserDto;
import leets.enhance.domain.user.service.UserCommandService;
import leets.enhance.global.response.ApiResponse;
import leets.enhance.global.utils.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody UserDto.SignInDto signInDto) {
        String id = signInDto.getId();
        String password = signInDto.getPassword();
        log.info("request id = {}, password = {}", id, password);
        JwtToken jwtToken = userCommandService.signIn(id, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/sign-up")
    public ApiResponse<UserDto> signUp(@RequestBody UserDto.SignUpDto signUpDto) {
        UserDto savedUserDto = userCommandService.signUp(signUpDto);
        return ApiResponse.onSuccess(savedUserDto);
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}

