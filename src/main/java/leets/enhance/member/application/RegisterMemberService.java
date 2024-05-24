package leets.enhance.member.application;

import jakarta.validation.constraints.Email;
import leets.enhance.core.domain.entity.Member;
import leets.enhance.core.domain.infra.repository.MemberJPARepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMemberService {

  private final MemberJPARepository memberJPARepository;

  private final PasswordEncoder passwordEncoder;

  public RegisterMemberResponse register(RegisterMemberRequest request) {

    if (request == null || !request.isValid()) {
      return RegisterMemberResponse.builder()
          .success(false)
          .errorCode(RegisterMemberErrorCode.NOT_EXIST_REGISTER_CONDITION)
          .build();
    }

    if (!verifyPassword(request)) {
      return RegisterMemberResponse.builder()
          .success(false)
          .errorCode(RegisterMemberErrorCode.NOT_MATCH_CONFIRM_PASSWORD)
          .build();
    }

    Member member = Member.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

    memberJPARepository.save(member);

    return RegisterMemberResponse.builder().build();
  }

  private boolean verifyPassword(RegisterMemberRequest request) {
    if (request.getPassword() == null || request.getConfirmPassword() == null) {
      return false;
    }
    return request.getPassword().equals(request.getConfirmPassword());
  }

  @Getter
  @RequiredArgsConstructor
  public enum RegisterMemberErrorCode {

    NOT_EXIST_REGISTER_CONDITION(HttpStatus.BAD_REQUEST, "회원가입 필수 데이터가 존재하지 않습니다."),
    NOT_MATCH_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class RegisterMemberResponse {

    @Builder.Default
    private boolean success = true;

    private RegisterMemberErrorCode errorCode;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class RegisterMemberRequest {

    @Email
    private String email;
    private String name;
    private String password;
    private String confirmPassword;

    boolean isValid() {
      return email != null && name != null && password != null && confirmPassword != null;
    }
  }
}
