package com.onstagram.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String FileUpload(MultipartFile file, String path);

}