package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.UserVideo;
import com.ssafy.spotlive.db.entity.UserVideoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @FileName : UserVideoRepository
 * @작성자 : 김민권
 * @Class 설명 : UserVideo 생성, 삭제, 조회 query문 생성을 위한 JPARepository 정의.
 */
@Repository
public interface UserVideoRepository extends JpaRepository<UserVideo, UserVideoId> {
    /**
     * @Method Name : findVideosByModeAndCategoryId
     * @작성자 : 김민권
     * @Method 설명 : UserVideo들을 accountEmail로 조회하는 메소드
     */
    List<UserVideo> findAllByUser_AccountEmail(String accountEmail);

    /**
     * @Method Name : findVideosByModeAndCategoryId
     * @작성자 : 김민권
     * @Method 설명 : UserVideo들을 VideoId로 조회하는 메소드
     */
    List<UserVideo> findAllByVideo_VideoId(Long videoId);
}