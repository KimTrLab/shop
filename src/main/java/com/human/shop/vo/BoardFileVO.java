package com.human.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileVO {

    private Long id;
    private Long boardId;
    private String originalName;
    private String savedName;
    private String filePath;
    private Long fileSize;

}