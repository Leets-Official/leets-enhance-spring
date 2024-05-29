package leets.enhance.domain.user.service;

import leets.enhance.domain.user.User;
import leets.enhance.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserQueryServiceImpl implements UserQueryService{
    private UserRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(User user) {
        return User.builder()
                .id(user.getId())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
    }
}
