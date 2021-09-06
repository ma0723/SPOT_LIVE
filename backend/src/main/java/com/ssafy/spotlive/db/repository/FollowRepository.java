package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.Follow;
import com.ssafy.spotlive.db.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @FileName : FollowRepository
 * @작성자 : 권영린
 * @Class 설명 : 팔로우 관련 기능을 위한 Repository 정의.
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    /**
     * @Method Name : findFollowsByArtistAccountEmail
     * @작성자 : 권영린
     * @Method 설명 : 아티스트 이메일로 팬리스트 반환
     */
    Optional<List<Follow>> findFollowsByArtistAccountEmail(String artistEmail);
    /**
     * @Method Name : findFollowsByFanAccountEmail
     * @작성자 : 권영린
     * @Method 설명 : 팬 이메일로 아티스트 리스트 반환
     */
    Optional<List<Follow>> findFollowsByFanAccountEmail(String fanEmail);
}
