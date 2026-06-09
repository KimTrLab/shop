package com.human.shop.security;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.human.shop.Repository_jpa.MemberRepository;
import com.human.shop.entity_jpa.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //private final MemberMapper memberMapper;
    private final MemberRepository memberrepo;

    // @Override
    // public UserDetails loadUserByUsername(String loginId)
    // throws UsernameNotFoundException {

    // MemberVO member = memberMapper.findByLoginId(loginId);

    // if (member == null) {
    // throw new UsernameNotFoundException("사용자 없음");
    // }

    // return User.builder()
    // .username(member.getLoginId())
    // .password(member.getPassword()) // 이미 암호화된 값
    // .roles(member.getRole()) // USER, ADMIN
    // .build();

    // private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        System.out.println("kk" + loginId);

        Member member = memberrepo.findByLoginId(loginId);
        System.out.println("kk" + member);

        if (member == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        /*
         * 이 메서드는 사용자 정보를 조회하여 Spring Security에 전달하는 역할만 수행한다.
         *
         * 반환된 UserDetails 객체를 이용하여 Spring Security가 다음 과정을 자동으로 수행한다.
         *
         * 1. 사용자가 입력한 비밀번호와
         * DB에 저장된 암호화 비밀번호를 PasswordEncoder로 비교
         *
         * 2. 비밀번호가 일치하지 않으면
         * BadCredentialsException 발생
         *
         * 3. 비밀번호가 일치하면
         * Authentication 객체 생성
         *
         * 4. Authentication을 SecurityContext에 저장
         *
         * 5. SecurityContext를 Session(JSESSIONID)에 저장하여
         * 로그인 상태 유지
         */
        // Authentication은 Spring Security에서 "인증된 사용자 정보"를 담고 있는 객체
        // Principal (사용자 정보), Credentials (인증 정보) - 비밀번호, Authorities (권한)-Role, Name,
        // Authenticated 성공여부(true, false)



        /*
         * MemberVO는 Spring Security가 직접 사용할 수 없으므로
         * UserDetails 인터페이스를 구현한 CustomUserDetails로 변환한다.
         *
         * Spring Security는 UserDetails의
         * getUsername()
         * getPassword()
         * getAuthorities()
         * 등의 메서드를 이용하여 인증(Authentication)과
         * 인가(Authorization)를 처리한다.
         */
       
        return new CustomUserDetails(member);
    }

}