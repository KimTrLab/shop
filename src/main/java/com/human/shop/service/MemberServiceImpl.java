package com.human.shop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.human.shop.Repository_jpa.MemberRepository;
import com.human.shop.entity_jpa.Member;
import com.human.shop.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    //private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void join(MemberVO memberVO) {
        String encodedPassword = passwordEncoder.encode(memberVO.getPassword());

        //mybatis 방식
        // memberVO.setPassword(encodedPassword);
        // memberVO.setRole("USER");
        // memberMapper.insertMember(memberVO);

       
        // jpa방식
        Member member = Member.builder()
                .loginId(memberVO.getLoginId())
                .name(memberVO.getName())
                .password(encodedPassword)
                .address(memberVO.getAddress())
                .phone(memberVO.getPhone())
                .email(memberVO.getEmail())
                .role("USER")
                .build();

        memberRepository.save(member);
    }
}
