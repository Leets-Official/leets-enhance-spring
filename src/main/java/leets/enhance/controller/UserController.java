package leets.enhance.controller;


import leets.enhance.dto.UserRequestDto;
import leets.enhance.dto.UserResponseDto;
import leets.enhance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto.Register requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(requestDto));
    }
}
