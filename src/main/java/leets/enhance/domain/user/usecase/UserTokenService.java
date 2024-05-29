package leets.enhance.domain.user.usecase;

import leets.enhance.domain.user.domain.User;

public interface UserTokenService {
    String generateRefreshToken(User user);
    Boolean isValidToken(String token);
}
