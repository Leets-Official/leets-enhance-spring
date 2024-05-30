package leets.enhance.sword.application;

import jakarta.validation.constraints.Size;
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
public class CreateItemsService {

  private final MemberJPARepository memberJPARepository;

  private final SwordJPARepository swordJPARepository;

  private final JwtProvider jwtProvider;

  public CreateItemsResponse createItems(CreateItemsRequest request) {

    if (request == null || !request.isValid()) {
      return CreateItemsResponse.builder()
          .success(false)
          .errorCode(CreateItemsErrorCode.NOT_EXIST_ITEM_NAME)
          .build();
    }

    String emailFromToken = jwtProvider.getEmailFromToken(request.accessToken);
    if (emailFromToken == null) {
      return CreateItemsResponse.builder()
          .success(false)
          .errorCode(CreateItemsErrorCode.INVALID_ACCESS_TOKEN)
          .build();
    }

    Member member = memberJPARepository.findByEmail(emailFromToken).orElse(null);
    if (member == null) {
      return CreateItemsResponse.builder()
          .success(false)
          .errorCode(CreateItemsErrorCode.NOT_EXIST_MEMBER)
          .build();
    }

    if (!member.getIsFirstLogin()) {
      return CreateItemsResponse.builder()
          .success(false)
          .errorCode(CreateItemsErrorCode.ALREADY_EXIST_ITEM)
          .build();
    }

    member.setIsFirstLogin(false);
    Sword sword = Sword.builder()
        .name(request.getItemName())
        .member(member)
        .build();
    swordJPARepository.save(sword);

    return CreateItemsResponse.builder().build();
  }

  @Getter
  @RequiredArgsConstructor
  public enum CreateItemsErrorCode {

    NOT_EXIST_ITEM_NAME(HttpStatus.BAD_REQUEST, "아이템 생성에 팔요한 데이터가 존재하지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    NOT_EXIST_MEMBER(HttpStatus.INTERNAL_SERVER_ERROR, "사용자가 존재하지 않습니다."),
    ALREADY_EXIST_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 아이템이 있습니다.");

    private final HttpStatus httpStatus;
    private final String description;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateItemsResponse {

    @Builder.Default
    private boolean success = true;

    private CreateItemsErrorCode errorCode;
  }

  @ToString
  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateItemsRequest {

    @Size(min = 1, max = 5, message = "아이템 이름은 최대 5글자로 제한됩니다.")
    private String itemName;

    private String accessToken;

    public boolean isValid() {
      return itemName != null && !itemName.isEmpty() && accessToken != null && !accessToken.isEmpty();
    }
  }
}
