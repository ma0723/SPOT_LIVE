package com.ssafy.spotlive.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@Transactional
@ExtendWith(MockitoExtension.class)
class FileUploadServiceTest {

    @Mock
    AmazonS3Client amazonS3Client;

    @InjectMocks
    FileUploadService fileUploadService;

    @Test
    void uploadTest() throws Exception {
        //given
        String fileName = "test.jpg";
        final byte[] content = "hello world".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("content", fileName, "multipart/mixed", content);
        //when
        String url = fileUploadService.upload(multipartFile);
        //then
        assertThat(url).contains("jpg");
    }

    @Test
    void getUuidTest() throws Exception{
        //given
        Method getUuid = fileUploadService.getClass().getDeclaredMethod("getUuid");
        getUuid.setAccessible(true);
        //when
        String uuid = (String) getUuid.invoke(fileUploadService);
        //then
        assertTrue(uuid.matches("([a-f0-9]{32})"));
    }

    @Test
    void deleteTest() throws Exception{
        //given
        String imageUrl = "imageUrl";
        //when
        fileUploadService.delete(imageUrl);
        //then
        verify(amazonS3Client).deleteObject(fileUploadService.bucket, "imageUrl");
    }
}