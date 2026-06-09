package com.human.shop.mapper_mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.human.shop.vo.BoardFileVO;

@Mapper
public interface BoardFileMapper {
        // 파일 저장
    void insertFile(BoardFileVO fileVO);
    // 게시글 번호로 파일 조회
    List<BoardFileVO> findByBoardId(Long id);

    //삭제 예정
    BoardFileVO findByBoardOneId(Long id);

    void deleteByBoardOneId(Long id);

}
