package leets.enhance.domain.user.service;

import leets.enhance.global.utils.JwtToken;

public interface UserCommandService {
    JwtToken signIn(String username, String password);
}
