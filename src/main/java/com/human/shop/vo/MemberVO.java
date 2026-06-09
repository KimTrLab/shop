package com.human.shop.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberVO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    private String address;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    private String role;
}

// @Data
// public class MemberVO {

//     private String loginId;
//     private String name;
//     private String password;
//     private String address;
//     private String phone;
//     private String email;
//     private String role;
// }