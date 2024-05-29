package leets.attendance.domain.user.presentation;

import leets.attendance.domain.common.dto.ResponseDto;
import leets.attendance.domain.user.application.UserService;
import leets.attendance.domain.user.domain.User;
import leets.attendance.domain.user.presentation.dto.Request.LoginRequestDto;
import leets.attendance.domain.user.presentation.dto.Request.RegisterRequestDto;
import leets.attendance.domain.user.presentation.dto.Response.DuplicationResponseDto;
import leets.attendance.domain.user.presentation.dto.Response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto){
//        User register = userService.register(registerRequestDto);
//        return ResponseDto.created(URI.create("/users/" + register.getId()));
//    }

    @GetMapping("/duplication/{joinId}")
    public ResponseEntity<DuplicationResponseDto> checkDuplicateId(@PathVariable String joinId){
        return ResponseDto.ok(userService.checkDuplicateId(joinId));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseDto.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto requestDto){
        userService.register(requestDto);
        return ResponseEntity.ok().build();
    }

}
