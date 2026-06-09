package com.human.shop.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.human.shop.vo.BoardFileVO;

public class FileUploadUtil {

        // 파일 저장 메서드
        public static List<BoardFileVO> saveFiles(
                        MultipartFile[] files,
                        String uploadPath) {

                List<BoardFileVO> fileList = new ArrayList<>();

                // 업로드 폴더 생성
                File folder = new File(uploadPath);

                if (!folder.exists()) {
                        folder.mkdirs();
                }

                // 파일 저장
                for (MultipartFile file : files) {
                        // 빈 파일 제외
                        if (file.isEmpty()) {
                                continue;
                        }

                        try {
                                // 원본 파일명
                                String originalName = file.getOriginalFilename();
                                // UUID 생성
                                String uuid = UUID.randomUUID().toString();
                                // 저장 파일명
                                String savedName = uuid + "_" + originalName;
                                // 저장 파일 객체
                                File saveFile = new File(uploadPath
                                                + File.separator
                                                + savedName);
                                // 실제 파일 저장
                                file.transferTo(saveFile);
                               // 파일 VO 생성
                                BoardFileVO fileVO = new BoardFileVO();
                                fileVO.setOriginalName(originalName);
                                fileVO.setSavedName(savedName);
                                fileVO.setFilePath(uploadPath);
                                fileVO.setFileSize(file.getSize());

                                // BoardFileVO fileVO = BoardFileVO.builder()
                                //                 .originalName(originalName)
                                //                 .savedName(savedName)
                                //                 .filePath(uploadPath)
                                //                 .fileSize(file.getSize())
                                //                 .build();

                                
                                // 리스트 추가
                                fileList.add(fileVO);

                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return fileList;

        }

}
