package leets.enhance.member.presentation;

import jakarta.validation.Valid;
import leets.enhance.member.application.LoginMemberService;
import leets.enhance.member.application.LoginMemberService.LoginMemberRequest;
import leets.enhance.member.application.LoginMemberService.LoginMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginMemberController {

  private final LoginMemberService loginMemberService;

  @PostMapping("/users/login")
  public ResponseEntity<LoginMemberResponse> login(@Valid @RequestBody LoginMemberRequest request) {
    return ResponseEntity.ok(loginMemberService.login(request));
  }
}
