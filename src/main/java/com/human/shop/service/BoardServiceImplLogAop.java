package com.human.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.human.shop.mapper_mybatis.BoardFileMapper;
import com.human.shop.mapper_mybatis.BoardMapper;
import com.human.shop.utils.FileUploadUtil;
import com.human.shop.vo.BoardFileVO;
import com.human.shop.vo.BoardVO;
import com.human.shop.vo.PageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImplLogAop implements BoardService {

    @Autowired
    private BoardMapper boardmapper;

    @Autowired
    private BoardFileMapper boardFileMapper;

    @Override
    public void inserBoard(BoardVO boardvo, MultipartFile[] files) {

        boardmapper.insertBoard(boardvo);

        Long boardId = boardvo.getId();

        String uploadPath = "c:/upload/";

        List<BoardFileVO> fileList = FileUploadUtil.saveFiles(files, uploadPath);

        for (BoardFileVO fileVO : fileList) {
            fileVO.setBoardId(boardId);
            boardFileMapper.insertFile(fileVO);
        }

        // 비즈니스 로그 유지
        log.info("게시글 등록 완료 - id: {}, 첨부파일 개수: {}", boardId, fileList.size());
    }

    @Override
    public List<BoardVO> getBoardList(PageVO pageVO) {

        return boardmapper.selectBoardList(pageVO);
    }

    @Override
    @Transactional
    public void deleteBoard(Long id) {

        boardmapper.deleteById(id);
        boardFileMapper.deleteByBoardOneId(id);

        // 비즈니스 로그 유지
        log.info("게시글 삭제 완료 - id: {}", id);
    }

    @Override
    @Transactional
    public BoardVO detailBoard(Long id) {

        boardmapper.updateViewCount(id);

        BoardVO boardVO = boardmapper.getBoardById(id);
        List<BoardFileVO> fileList = boardFileMapper.findByBoardId(id);

        boardVO.setFileList(fileList);

        return boardVO;
    }

    @Override
    public void updateBoard(BoardVO boardvo) {

        boardmapper.updateById(boardvo);

        // 비즈니스 로그 유지
        log.info("게시글 수정 완료 - id: {}", boardvo.getId());
    }

    @Override
    public BoardFileVO displayFile(Long id) {

        return boardFileMapper.findByBoardOneId(id);
    }

    @Override
    public int getBoardCount() {

        int cnt = boardmapper.count();

        // 비즈니스 로그 유지
        log.info("게시글 총 개수 조회 결과 - count: {}", cnt);

        return cnt;
    }
}