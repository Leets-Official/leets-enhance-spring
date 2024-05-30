package leets.enhance.domain.user.controller;

import jakarta.validation.Valid;
import leets.enhance.domain.user.dto.DuplicateUserIdRequest;
import leets.enhance.gloal.jwt.dto.Token;
import leets.enhance.domain.user.dto.UserLoginRequest;
import leets.enhance.domain.user.dto.UserRegisterRequest;
import leets.enhance.domain.user.dto.UserResponse;
import leets.enhance.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authService.signup(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.login(userLoginRequest));
    }

    @GetMapping("/check-duplicate-id")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestBody @Valid DuplicateUserIdRequest duplicateUserIdRequest) {
        return ResponseEntity.ok(authService.checkDuplicateId(duplicateUserIdRequest));
    }
}