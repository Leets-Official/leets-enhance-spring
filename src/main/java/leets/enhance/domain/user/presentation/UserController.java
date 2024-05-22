package leets.enhance.domain.user.presentation;

import leets.enhance.domain.user.application.UserService;
import leets.enhance.domain.user.dto.LoginRequest;
import leets.enhance.domain.user.dto.LoginResponse;
import leets.enhance.domain.user.dto.SignUpRequest;
import leets.enhance.domain.user.dto.SignUpResponse;
import leets.enhance.global.dto.ResponseDto;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static leets.enhance.domain.user.presentation.UserResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@EnableJpaAuditing
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/register")
    public ResponseDto<SignUpResponse> register(@RequestBody SignUpRequest userRequest) throws Exception {
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), userService.register(userRequest));
    }

    @GetMapping(value = "/check-duplicate-email")
    public ResponseDto<String> checkDuplicateId(@RequestBody Map<String, String> email) throws Exception {
        return ResponseDto.of(OK.value(), USABLE_ID.getMessage(), userService.checkDuplicateId(email.get("email")));
    }

    @PostMapping(value = "/login")
    public ResponseDto<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception{
        return ResponseDto.of(OK.value(),SUCCESS_LOGIN.getMessage(),userService.login(loginRequest));
    }
}
