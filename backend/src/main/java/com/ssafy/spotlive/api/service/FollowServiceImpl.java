package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.follow.FollowFindByArtistAccountEmailGetRes;
import com.ssafy.spotlive.api.response.follow.FollowFindByFanAccountEmailGetRes;
import com.ssafy.spotlive.db.entity.Follow;
import com.ssafy.spotlive.db.entity.FollowId;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.repository.FollowRepository;
import com.ssafy.spotlive.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @FileName : FollowServiceImpl
 * @작성자 : 권영린
 * @Class 설명 : 팔로우 관련 기능을 위한 ServiceImpl 정의.
 */
@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void insertFollowByAccountEmail(String artistEmail, String fanEmail){
        /**
         * @Method Name : insertFollowByAccountEmail
         * @작성자 : 권영린
         * @Method 설명 : 팔로우 버튼 클릭시 팔로잉
         */
        Follow follow = new Follow();
        User artist = new User();
        User fan = new User();
        artist.setAccountEmail(artistEmail);
        fan.setAccountEmail(fanEmail);
        follow.setArtist(artist);
        follow.setFan(fan);
        followRepository.save(follow);
    }

    @Override
    public void deleteFollowByAccountEmail(String accessToken, String artistEmail){
        /**
         * @Method Name : deleteFollowByAccountEmail
         * @작성자 : 권영린
         * @Method 설명 : 언팔로우 버튼 클릭시 언팔로잉
         */
        String fanEmail = userRepository.findUserByAccessToken(accessToken).get().getAccountEmail();
        System.out.println("artistEmail : "+artistEmail);
        System.out.println("fanEmail : "+fanEmail);
        FollowId followId = new FollowId();
        followId.setArtist(artistEmail);
        followId.setFan(fanEmail);
        followRepository.deleteById(followId);
    }

    @Override
    public List<FollowFindByFanAccountEmailGetRes> findArtistByFanAccountEmail(String fanEmail) {
        /**
         * @Method Name : findArtistByFanAccountEmail
         * @작성자 : 권영린
         * @Method 설명 : 팬의 이메일로 팬이 팔로우중인 유저들의 리스트를 담는 메소드
         */
        List<Follow> artistList = followRepository.findFollowsByFanAccountEmail(fanEmail).orElse(null);
        if(artistList == null || artistList.isEmpty()) {
            return null;
        }
        return artistList.stream()
                .map(follow -> FollowFindByFanAccountEmailGetRes.of(follow)).collect(Collectors.toList());
    }

    @Override
    public List<FollowFindByArtistAccountEmailGetRes> findFanByArtistAccountEmail(String artistEmail) {
        /**
         * @Method Name : findFanByArtistAccountEmail
         * @작성자 : 권영린
         * @Method 설명 : 아티스트의 이메일로 자신을 팔로우중인 유저들의 리스트를 담는 메소드
         */
        Optional<List<Follow>> fanList = followRepository.findFollowsByArtistAccountEmail(artistEmail);
        return fanList.get().stream()
                .map(follow -> FollowFindByArtistAccountEmailGetRes.of(follow)).collect(Collectors.toList());
    }
}
