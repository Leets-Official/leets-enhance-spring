package leets.enhance.service;

import leets.enhance.domain.Member;
import leets.enhance.repository.MemberRepository;
import leets.enhance.security.JwtTokenProvider;
import leets.enhance.service.dto.AuthRequest;
import leets.enhance.service.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(AuthRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        Member member = Member.create(request.email(), passwordEncoder.encode(request.password()), request.name());
        memberRepository.save(member);

        String token = jwtTokenProvider.createToken(member.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Optional<Member> memberOptional = memberRepository.findByEmail(request.email());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (passwordEncoder.matches(request.password(), member.getPassword())) {
                String token = jwtTokenProvider.createToken(member.getEmail());
                return new AuthResponse(token);
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
}
