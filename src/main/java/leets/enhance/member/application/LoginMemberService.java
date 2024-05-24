package leets.enhance.member.application;

import jakarta.validation.constraints.Email;
import leets.enhance.configuration.jwt.provider.JwtProvider;
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
public class LoginMemberService {

  private final MemberJPARepository memberJPARepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  public LoginMemberResponse login(LoginMemberRequest request) {

    if (request == null || !request.isValid()) {
      return LoginMemberResponse.builder()
          .success(false)
          .errorCode(LoginMemberErrorCode.NOT_EXIST_LOGIN_CONDITION)
          .build();
    }

    Member member = memberJPARepository.findByEmail(request.getEmail()).orElse(null);
    if (member == null) {
      return LoginMemberResponse.builder()
          .success(false)
          .errorCode(LoginMemberErrorCode.NOT_EXIST_MEMBER_INFORMATION)
          .build();
    }

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      return LoginMemberResponse.builder()
          .success(false)
          .errorCode(LoginMemberErrorCode.INCORRECT_PASSWORD)
          .build();
    }

    String token = jwtProvider.createToken(member.getEmail(), member.getPassword());
    if (token == null || token.isEmpty()) {
      return LoginMemberResponse.builder()
          .success(false)
          .errorCode(LoginMemberErrorCode.TOKEN_NOT_GENERATED)
          .build();
    }

    return LoginMemberResponse.builder().accessToken(token).build();
  }

  @Getter
  @RequiredArgsConstructor
  public enum LoginMemberErrorCode {

    NOT_EXIST_LOGIN_CONDITION(HttpStatus.BAD_REQUEST, "로그인 필수 데이터가 존재하지 않습니다."),
    NOT_EXIST_MEMBER_INFORMATION(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다."),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    TOKEN_NOT_GENERATED(HttpStatus.INTERNAL_SERVER_ERROR, "토큰이 생성되지않았습니다.");

    private final HttpStatus httpStatus;
    private final String message;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class LoginMemberResponse {

    @Builder.Default
    private boolean success = true;

    private LoginMemberErrorCode errorCode;

    private String accessToken;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class LoginMemberRequest {

    @Email
    private String email;
    private String password;

    boolean isValid() {
      return email != null && password != null;
    }
  }
}
