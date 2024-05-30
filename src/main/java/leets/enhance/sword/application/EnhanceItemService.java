package leets.enhance.sword.application;

import leets.enhance.configuration.jwt.provider.JwtProvider;
import leets.enhance.core.domain.entity.Member;
import leets.enhance.core.domain.entity.Sword;
import leets.enhance.core.domain.infra.repository.MemberJPARepository;
import leets.enhance.core.domain.infra.repository.SwordJPARepository;
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
public class EnhanceItemService {

  private final SwordJPARepository swordJPARepository;

  private final MemberJPARepository memberJPARepository;

  private final JwtProvider jwtProvider;

  public EnhanceItemResponse enhanceItem(String accessToken) {
    if (accessToken == null || jwtProvider.getEmailFromToken(accessToken) == null) {
      return EnhanceItemResponse.builder()
          .success(false)
          .errorCode(EnhanceItemErrorCode.INVALID_ACCESS_TOKEN)
          .build();
    }
    String email = jwtProvider.getEmailFromToken(accessToken);
    Member member = memberJPARepository.findByEmail(email).orElse(null);
    if (member == null) {
      return EnhanceItemResponse.builder()
          .success(false)
          .errorCode(EnhanceItemErrorCode.NOT_EXIST_MEMBER)
          .build();
    }
    Sword sword = member.getSword();
    if (sword == null) {
      return EnhanceItemResponse.builder()
          .success(false)
          .errorCode(EnhanceItemErrorCode.NOT_EXIST_ITEM)
          .build();
    }



    return EnhanceItemResponse.builder().build();
  }

  @RequiredArgsConstructor
  @Getter
  public enum EnhanceItemErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    NOT_EXIST_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "사용자가 존재하지 않습니다."),
    NOT_EXIST_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "아이템이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String description;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class EnhanceItemResponse {

    @Builder.Default
    private boolean success = true;

    private EnhanceItemErrorCode errorCode;
  }
}
