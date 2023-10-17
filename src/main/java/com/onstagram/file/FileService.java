package com.onstagram.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String FileUpload(MultipartFile file, String path); //파일 업로드
    public void DeleteFile(String fileName);//파일 삭제

}