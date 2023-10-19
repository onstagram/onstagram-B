package com.onstagram.file;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.onstagram.exception.OnstagramException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;//버킷이름
    @Value("${spring.servlet.multipart.location}")
    private String localpath; //업로드시 임시 저장 경로

    @Override //파일 업로드(저장)
    public String FileUpload(MultipartFile file, String path) {

        log.info("Service 파일업로드 시작");

        try {

            String fileName = file.getOriginalFilename(); // 선택한 파일명을 가져옴
            //String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            log.info("파일이름 : " + fileName + "        경로 : " + path);
            fileName = path + "/" + fileName;//파일명 앞에 저장 경로 설정(게시물, 회원)

            ObjectMetadata metadata = new ObjectMetadata(); // S3 객체 메타데이터 생성
            metadata.setContentType(file.getContentType()); // 파일의 컨텐츠 타입 설정
            metadata.setContentLength(file.getSize()); // 파일 크기 설정

            // Amazon S3에 파일 업로드. "post/" 폴더에 저장.
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata); //파일명 앞에 저장 경로 설정(게시물, 회원)

            return amazonS3Client.getUrl(bucket, fileName).toString(); //성공하면 URL 리턴

        } catch (Exception e) {
            e.printStackTrace();
            log.info("S3 업로드 실패");
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "파일 업로드 실패");
//            return null; //실패
        }
    }

//    @Override //파일 삭제
//    public void DeleteFile(String fileName) {
//        amazonS3Client.deleteObject(bucket, fileName);
//    }
}