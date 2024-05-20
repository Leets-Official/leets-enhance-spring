package leets.enhance.domain.user.controller;

import leets.enhance.domain.user.dto.request.LoginRequest;
import leets.enhance.domain.user.dto.request.RegisterRequest;
import leets.enhance.domain.user.dto.response.RegisterResponse;
import leets.enhance.domain.user.usecase.UserLogin;
import leets.enhance.domain.user.usecase.UserRegister;
import leets.enhance.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserLogin userLogin;
    private final UserRegister userRegister;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = userRegister.execute(request.username(), request.password(), request.bladeName());
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse tokens = userLogin.execute(request.username(), request.password());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/check-duplicate-id")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestParam String username) {
        Boolean isDuplicate = userRegister.isUsernameDuplicated(username);
        return ResponseEntity.ok(isDuplicate);
    }
}
