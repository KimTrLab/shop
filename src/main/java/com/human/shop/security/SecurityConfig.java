package com.human.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        // private final CustomUserDetailsService userDetailsService;

        // 🔐 비밀번호 암호화
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // 🔐 DB 인증 Provider
        // @Bean
        // public DaoAuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider provider = new
        // DaoAuthenticationProvider(userDetailsService);
        // provider.setPasswordEncoder(passwordEncoder());
        // return provider;
        // }

        // 🔐 Security Filter Chain
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())

                                // 🔐 인증 / 인가
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/logout",
                                                                "/members",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**",
                                                                "/access-denied")
                                                .permitAll()

                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/boards/**").hasRole("USER")

                                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login") // 인증이 필요한 페이지 접근 시 보여줄 로그인 페이지(GET)
                                                // 클라이언트 요청이 post방식에 login이라면 다음과 같이 처리
                                                // 시큐리티가 인증 받도록 돌려라 인증 처리로 이동
                                                // 인증은 데이터베이스로 부터 조회를 한 후에 비밀번호 일치 판단
                                                // 처리하는 흐름 <코드에서만 생략이 되고, 실제 시큐리티가알아서 >
                                                // UsernamePasswordAuthenticationFiler
                                                // 클라이언트가 입력한 파라미터에서 username password를 가져옴

                                                // AuthenticationManager
                                                // 인증총괄관리자.. 실제 인증을 하는 provider에게 작업 위임

                                                // DaoAuthentication Provider
                                                // 데이터베이스에 저장된 회원 정보를 이용하여 아이디와 비번 검증
                                                // userDetailService에게 정보 가져와.

                                                // UserDetailsService
                                                // 매퍼를 통해서 정보를 가져옴.

                                                // UserDetails
                                                // 인증된 사용자 정보를 스프링이 인식할 수 있도록 랩핑

                                                // 인증이 성공되면 Authentiation객체를 생성하고
                                                // Authentiation객체는 principal(사용자이름),
                                                // Credentials(인증정보-비번), Authorities(권한-role)
                                                // 정보를 저장합니다.
                                                // 이 객체를 SecurityConext에 저장
                                                // SecurityConext를 세션(JSESSIONID)에 저장을 합니다.

                                                // 한줄로 설명하면
                                                // post /login 요청이 오면 UserDetailsService 동작하고
                                                // 데이터베이스로 부터 사용자 정보 가져오고 비번확인
                                                // UserDetails 객체로 랩핑하여 로그인 인증
                                                .loginProcessingUrl("/login") // Spring Security가 가로채서 로그인 처리하는
                                                                              // URL(POST)
                                                .defaultSuccessUrl("/", true) // 로그인 성공 시 항상 "/"로 이동
                                                .failureUrl("/login?error") // 로그인 실패 시 이동
                                                .permitAll())

                                //  로그아웃 설정
                                .logout(logout -> logout
                                                .logoutUrl("/logout") // Spring Security가 가로채서 로그아웃 처리
                                                .invalidateHttpSession(true) // 서버에 저장된 HttpSession 제거
                                                .deleteCookies("JSESSIONID") // 클라이언트의 세션 식별 쿠키 삭제
                                                .logoutSuccessUrl("/login?logout")) // 로그아웃 완료 후 로그인 페이지로 리다이렉트

                                .exceptionHandling(exception -> exception
                                                // 로그인 안 된 경우
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        response.sendRedirect("/login?message=needLogin");
                                                })

                                                // 권한 없는 경우
                                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                                        response.sendRedirect("/access-denied?error=noPermission");
                                                }));

                // 🔥 핵심: 인증 Provider 연결
                // “UsernamePasswordAuthenticationFilter가 사용할 인증 로직 등록”
                // .authenticationProvider(authenticationProvider());

                return http.build();
        }
}