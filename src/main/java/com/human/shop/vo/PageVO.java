package com.human.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVO {

    // 현재 페이지
    private int page = 1;
    // 한 페이지 게시글 수
    private int size = 10;
    // 전체 게시글 수
    private int totalCount;
    // 화면에 보여줄 페이지 개수
    private int block = 10;
    // 시작 페이지
    private int startPage;
    // 끝 페이지
    private int endPage;

    // 이전 다음 여부
    private boolean prev;
    private boolean next;

    // 전체 페이지 수
    private int totalPage;

    //Limit의 시작 위치
    private int offset;

    // LIMIT 시작 위치
    public int getOffset() {
        offset =(page - 1) * size;
        return offset;
    }

    // 페이징 계산
    public void pageInfo(int totalCount) {

        this.totalCount = totalCount;
        // 전체 페이지 수
        this.totalPage =(int)Math.ceil((double) totalCount / size);
        // 끝 페이지
        this.endPage = (int)Math.ceil((double) page / block) * block;
        // 시작 페이지
        this.startPage =  endPage - block + 1;
        // 실제 마지막 페이지보다 크면 수정
        if(endPage > totalPage) {
            endPage = totalPage;
        }
        // 이전
        this.prev = startPage > 1;
        // 다음
        this.next = endPage < totalPage;
    }

}