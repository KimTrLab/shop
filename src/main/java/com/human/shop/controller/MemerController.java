package com.human.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.human.shop.service.MemberServiceImpl;
import com.human.shop.vo.MemberVO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemerController {

    private final MemberServiceImpl memberService;

    @PostMapping("/members")
    public String join(
            @Valid @ModelAttribute MemberVO memberVO,
            BindingResult bindingResult) {

        System.out.println("members controller in");

        // 검증 실패
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/"; // 회원가입 페이지로 이동
        }

        memberService.join(memberVO);

        return "redirect:/";
    }

}
