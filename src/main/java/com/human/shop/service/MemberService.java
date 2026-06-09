package com.human.shop.service;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.human.shop.vo.MemberVO;

public interface MemberService {

    public void join(@ModelAttribute MemberVO memberVO);

}
