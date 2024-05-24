package leets.enhance.member.presentation;

import jakarta.validation.Valid;
import leets.enhance.member.application.EmailDuplicationService;
import leets.enhance.member.application.EmailDuplicationService.EmailDuplicationRequest;
import leets.enhance.member.application.EmailDuplicationService.EmailDuplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailDuplicationController {

  private final EmailDuplicationService emailDuplicationService;

  @PostMapping("/users/check-duplicate-id")
  public ResponseEntity<EmailDuplicationResponse> register(@Valid @RequestBody EmailDuplicationRequest request) {
    return ResponseEntity.ok(emailDuplicationService.isEmailDuplicated(request));
  }
}
