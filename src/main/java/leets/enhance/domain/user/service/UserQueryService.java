package leets.enhance.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserQueryService {
    UserDetails loadUserByUserId(Long id);
}
