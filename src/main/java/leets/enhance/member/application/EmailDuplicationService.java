package leets.enhance.member.application;

import jakarta.validation.constraints.Email;
import leets.enhance.core.domain.infra.repository.MemberJPARepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDuplicationService {

  private final MemberJPARepository memberJPARepository;

  public EmailDuplicationResponse isEmailDuplicated(EmailDuplicationRequest request) {

    if (request == null || !request.isValid()) {
      return EmailDuplicationResponse.builder()
          .success(false)
          .errorCode(EmailDuplicationErrorCode.NOT_EXIST_EMAIL)
          .build();
    }

    boolean isDuplicatedId = memberJPARepository.existsByEmail(request.getEmail());
    if (isDuplicatedId) {
      return EmailDuplicationResponse.builder()
          .success(false)
          .errorCode(EmailDuplicationErrorCode.ALREADY_EXIST_EMAIL)
          .build();
    }
    return EmailDuplicationResponse.builder().build();
  }

  @Getter
  @RequiredArgsConstructor
  public enum EmailDuplicationErrorCode {

    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이메일이 입력되지 않았습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class EmailDuplicationResponse {

    @Builder.Default
    private boolean success = true;

    private EmailDuplicationErrorCode errorCode;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class EmailDuplicationRequest {

    @Email
    private String email;

    boolean isValid() {
      return email != null;
    }
  }
}
