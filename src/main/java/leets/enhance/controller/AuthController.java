package leets.enhance.controller;


import leets.enhance.dto.TokenResponseDto;
import leets.enhance.dto.UserRequestDto;
import leets.enhance.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserRequestDto.Login requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
