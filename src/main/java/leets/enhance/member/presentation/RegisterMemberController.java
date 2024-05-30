package leets.enhance.member.presentation;

import jakarta.validation.Valid;
import leets.enhance.member.application.RegisterMemberService;
import leets.enhance.member.application.RegisterMemberService.RegisterMemberRequest;
import leets.enhance.member.application.RegisterMemberService.RegisterMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterMemberController {

  private final RegisterMemberService registerMemberService;

  @PostMapping("/users/register")
  public ResponseEntity<RegisterMemberResponse> register(@Valid @RequestBody RegisterMemberRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(registerMemberService.register(request));
  }
}
