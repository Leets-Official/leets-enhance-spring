package leets.enhance.gloal.config;

import leets.enhance.gloal.jwt.TokenProvider;
import leets.enhance.gloal.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        http
                .csrf(AbstractHttpConfigurer::disable)

        //CORS 설정 Disable
                .cors(AbstractHttpConfigurer::disable)

        //From 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)

        //http basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)

        // 시큐리티는 기본적으로 세션을 사용
        // JWT는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
        //경로별 인가 작업

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/**","items/top10").permitAll()
                        .requestMatchers("/items/**", "/enhance").permitAll()
                        .anyRequest().permitAll()
                )


        // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
