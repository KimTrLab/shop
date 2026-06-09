package com.human.shop.memberTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.human.shop.Repository_jpa.MemberRepository;
import com.human.shop.entity_jpa.Member;
import com.human.shop.mapper_mybatis.MemberMapper;
import com.human.shop.vo.MemberVO;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberRepository membmerRepor;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertAndFindTest() {

        // given
        Member member = Member.builder()
                .loginId("testuser")
                .name("홍길동")
                .password(passwordEncoder.encode("1234"))
                .address("서울시 강남구")
                .phone("010-1234-5678")
                .email("test@test.com")
                .role("ROLE_USER")
                .build();

        // when
        //memberMapper.save(member);
        membmerRepor.save(member);

        // then
        MemberVO findMember = memberMapper.findByLoginId("testuser");

        assertThat(findMember).isNotNull();
        assertThat(findMember.getLoginId()).isEqualTo("testuser");
        assertThat(findMember.getName()).isEqualTo("홍길동");

        // BCrypt는 매번 결과가 달라지므로 equals 비교 금지
        assertThat(passwordEncoder.matches(
                "1234",
                findMember.getPassword())).isTrue();
    }

    // @Test
    // void 로그인아이디조회테스트() {

    // // given
    // MemberVO member = new MemberVO();
    // member.setLoginId("user1");
    // member.setName("김철수");
    // member.setPassword("1111");
    // member.setAddress("경기도");
    // member.setPhone("010-1111-1111");
    // member.setEmail("user1@test.com");

    // memberMapper.insertMember(member);

    // // when
    // MemberVO result = memberMapper.findByLoginId("user1");

    // // then
    // assertThat(result).isNotNull();
    // assertThat(result.getLoginId()).isEqualTo("user1");
    // assertThat(result.getName()).isEqualTo("김철수");
    // }
}