package com.human.shop.Repository_jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.human.shop.entity_jpa.Member;

public interface MemberRepository
        extends JpaRepository<Member, String> {

    Member findByLoginId(String loginId);
    /**
     * Spring Data JPA가 메서드 이름을 분석해서 자동으로 SQL을 생성합니다.
     * select *
        from member
        where login_id = ?
     */
}