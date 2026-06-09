package com.human.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.human.shop.mapper_mybatis.BoardFileMapper;
import com.human.shop.mapper_mybatis.BoardMapper;
import com.human.shop.utils.FileUploadUtil;
import com.human.shop.vo.BoardFileVO;
import com.human.shop.vo.BoardVO;
import com.human.shop.vo.PageVO;

import lombok.extern.slf4j.Slf4j;

//고객의 요청을 처리해 주는 게 역할
//메서드 네이밍시.. 고객의 요청의 냄새가 나게.. 
@Slf4j
// @Service
public class BoardServiceImpl implements BoardService {

    // 컨트롤러 부터 요청을 받으면 mapper에게 디비작업 콜
    // boardmapper를 의존합니다.
    // 그래서 boardmapper객체의 주소가 필요
    // BoardMapper 타입의 객체의 주소를
    @Autowired // 컨테이너로 부터 주입해 주는 기능
    private BoardMapper boardmapper;

    @Autowired
    private BoardFileMapper boardFileMapper;

    @Override   
    // @Transactional 
    public void inserBoard(BoardVO boardvo, MultipartFile[] files) {

        log.info("게시글 등록 요청 시작 - title: {}", boardvo.getTitle());

        // 글 저장기능
        // 하루에 3개만 작성하게 하자..서비스 추가
        // 글에 욕설이 있는지 인공지능 필터링

        boardmapper.insertBoard(boardvo);

        // insert 후 생성된 게시글 번호
        Long boardId = boardvo.getId();

        String uploadPath = "c:/upload/";

        log.debug("생성된 게시글 ID: {}", boardId);

        // 저장된 파일 목록 반환
        List<BoardFileVO> fileList = FileUploadUtil.saveFiles(files, uploadPath);

        for (BoardFileVO fileVO : fileList) {
            // 게시글 번호 세팅
            fileVO.setBoardId(boardId);
            // DB 저장
            boardFileMapper.insertFile(fileVO);
        }

        log.info("게시글 등록 완료 - id: {}, 첨부파일 개수: {}", boardId, fileList.size());
    }

    @Override
    public List<BoardVO> getBoardList(PageVO pageVO) {

        log.info("게시글 목록 조회 요청 - page: {}", pageVO.getPage());

        List<BoardVO> boardlist = boardmapper.selectBoardList(pageVO);

        return boardlist;
    }

    @Override
    @Transactional
    public void deleteBoard(Long id) {

        //System.out.println("서비스단에서 삭제요청 처리");
        log.warn("게시글 삭제 요청 - id: {}", id);

        boardmapper.deleteById(id);
        boardFileMapper.deleteByBoardOneId(id);

        log.info("게시글 삭제 완료 - id: {}", id);
    }

    @Override
    @Transactional
    public BoardVO detailBoard(Long id) {

        log.info("게시글 상세 조회 요청 - id: {}", id);

        boardmapper.updateViewCount(id);

        BoardVO boardVO = boardmapper.getBoardById(id);        
        List<BoardFileVO> fileList = boardFileMapper.findByBoardId(id);

        boardVO.setFileList(fileList);

        return boardVO;
    }

    @Override
    public void updateBoard(BoardVO boardvo) {

        log.info("게시글 수정 요청 - id: {}", boardvo.getId());

        boardmapper.updateById(boardvo);

        log.info("게시글 수정 완료 - id: {}", boardvo.getId());
    }

    @Override
    public BoardFileVO displayFile(Long id) {

        log.debug("파일 조회 요청 - fileId: {}", id);

        BoardFileVO fileVO = boardFileMapper.findByBoardOneId(id);

        return fileVO;
    }

    @Override
    public int getBoardCount() {

        int cnt = boardmapper.count();

        log.info("게시글 총 개수 조회 - count: {}", cnt);

        return cnt;
    }
}