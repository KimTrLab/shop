package com.human.shop.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.human.shop.mapper_mybatis.BoardFileMapper;
import com.human.shop.vo.BoardFileVO;

@SpringBootTest
class BoardFileMapperTests {

    @Autowired
    private BoardFileMapper boardFileMapper;    

    @Test
    void insertTest() {

        BoardFileVO fileVO = BoardFileVO.builder()
                .boardId(4L)
                .originalName("aaa")
                .savedName("aaa")
                .filePath("aaa")
                .fileSize(15L)
                .build();

        boardFileMapper.insertFile(fileVO);

        System.out.println("저장 완료");
    }
}
