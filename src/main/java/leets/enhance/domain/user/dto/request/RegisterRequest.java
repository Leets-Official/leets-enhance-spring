package leets.enhance.domain.user.dto.request;

public record RegisterRequest(String username,
                              String password,
                              String bladeName) {
}
