package leets.enhance.domain.user.presentation;

import leets.enhance.domain.common.dto.ResponseDto;
import leets.enhance.domain.user.application.UserService;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.presentation.dto.Request.LoginRequestDto;
import leets.enhance.domain.user.presentation.dto.Request.RegisterRequestDto;
import leets.enhance.domain.user.presentation.dto.Response.DuplicationResponseDto;
import leets.enhance.domain.user.presentation.dto.Response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto){
        User register = userService.register(registerRequestDto);
        return ResponseDto.created(register);
    }

    @GetMapping("/duplication/{email}")
    public ResponseEntity<DuplicationResponseDto> checkDuplicateId(@PathVariable String email){
        return ResponseDto.ok(userService.checkDuplicateId(email));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseDto.ok(userService.login(loginRequestDto));
    }

}
