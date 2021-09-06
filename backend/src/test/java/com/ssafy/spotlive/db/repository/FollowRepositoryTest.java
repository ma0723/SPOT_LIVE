package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FollowRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    FollowRepository followRepository;

    static String testAccountEmail1 = "test_account_1@gmail.com";
    static String testAccountEmail2 = "test_account_2@gmail.com";
    static String testAccountEmail3 = "test_account_3@gmail.com";
    static String testAccountEmail4 = "test_account_4@gmail.com";
    static User user0 = new User();
    static User user1 = new User();
    static User user2 = new User();
    static User user3 = new User();
    //follow0_3 -> user0 이 user3을 팔로우 한다는 의미
    static Follow follow0_3 = new Follow();
    static Follow follow1_2 = new Follow();
    static Follow follow1_3 = new Follow();
    static Follow follow1_0 = new Follow();
    static Follow follow2_0 = new Follow();
    static Follow follow2_3 = new Follow();
    static Follow follow3_0 = new Follow();
    static Follow follow3_1 = new Follow();

    @BeforeAll
    void initTestData() {
        //첫번 째 유저 추가
        user0.setAccountEmail(testAccountEmail1);
        user0.setGender("테스트MAN");
        user0.setAgeRange("테스트10~19");
        user0.setProfileDescription("테스트프로필");
        user0.setUserName("테스트이름");
        user0.setPhoneNumber("테스트전화");
        user0.setProfileImageUrl("테스트URL");
        //두번 째 유저 추가
        user1.setAccountEmail(testAccountEmail2);
        user1.setGender("테스트MAN");
        user1.setAgeRange("테스트10~19");
        user1.setProfileDescription("테스트프로필");
        user1.setUserName("테스트이름");
        user1.setPhoneNumber("테스트전화");
        user1.setProfileImageUrl("테스트URL");
        //세번 째 유저 추가
        user2.setAccountEmail(testAccountEmail3);
        user2.setGender("테스트WOMAN");
        user2.setAgeRange("테스트10~19");
        user2.setProfileDescription("테스트프로필");
        user2.setUserName("테스트이름");
        user2.setPhoneNumber("테스트전화");
        user2.setProfileImageUrl("테스트URL");
        //네번 째 유저 추가
        user3.setAccountEmail(testAccountEmail4);
        user3.setGender("테스트WOMAN");
        user3.setAgeRange("테스트10~19");
        user3.setProfileDescription("테스트프로필");
        user3.setUserName("테스트이름");
        user3.setPhoneNumber("테스트전화");
        user3.setProfileImageUrl("테스트URL");

        follow0_3.setFan(user0);
        follow0_3.setArtist(user3);
        follow1_2.setFan(user1);
        follow1_2.setArtist(user2);
        follow1_3.setFan(user1);
        follow1_3.setArtist(user3);
        follow1_0.setFan(user1);
        follow1_0.setArtist(user0);
        follow2_0.setFan(user2);
        follow2_0.setArtist(user0);
        follow2_3.setFan(user2);
        follow2_3.setArtist(user3);
        follow3_0.setFan(user3);
        follow3_0.setArtist(user0);
        follow3_1.setFan(user3);
        follow3_1.setArtist(user0);
    }

    @Test
    @Transactional
    void findFollowsByArtistAccountEmail() {
        //given
        userRepository.save(user0);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        followRepository.save(follow0_3);
        followRepository.save(follow1_0);
        followRepository.save(follow1_2);
        followRepository.save(follow1_3);
        followRepository.save(follow2_0);
        followRepository.save(follow3_0);
        followRepository.save(follow3_1);

        //when
        Optional<List<Follow>> fanListByArtist = followRepository.findFollowsByArtistAccountEmail(user3.getAccountEmail());

        //then
        assertThat(fanListByArtist).isNotEqualTo(Optional.empty()); // 비어있지 않으면 통과
        assertThat(fanListByArtist.get().stream() // user3의 팔로워중 user0이 포함되어 있다면 통과
                .anyMatch(follow -> follow.getFan().getAccountEmail().equals(user0.getAccountEmail())));
        assertThat(fanListByArtist.get().stream() // user3의 팔로워중 user2랑 매칭되는게 없다면 통과
                .noneMatch(follow -> follow.getFan().getAccountEmail().equals(user2.getAccountEmail())));
    }

    @Test
    @Transactional
    void findFollowsByFanAccountEmail() {
        //given
        userRepository.save(user0);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        followRepository.save(follow0_3);
        followRepository.save(follow1_0);
        followRepository.save(follow1_2);
        followRepository.save(follow1_3);
        followRepository.save(follow3_0);
        followRepository.save(follow3_1);

        //when
        Optional<List<Follow>> artistListByFan0 = followRepository.findFollowsByFanAccountEmail(user0.getAccountEmail());
        Optional<List<Follow>> artistListByFan2 = followRepository.findFollowsByFanAccountEmail(user2.getAccountEmail());

        //then
        assertThat(artistListByFan0).isNotEqualTo(Optional.empty()); //user0의 팔로잉 리스트는 비어있지 않으면 통과
        assertThat(artistListByFan2).isNotEqualTo(Optional.empty()); //user2의 팔로잉 리스트는 비어있어야 통과
        assertThat(artistListByFan0.get().stream() //user0의 팔로잉은 user3밖에 없어야 통과
                .allMatch(follow -> follow.getArtist().getAccountEmail().equals(user3.getAccountEmail())));
        assertThat(artistListByFan0.get().stream() //user0의 팔로잉중 user2이 없으면 통과(위에와 같은 의미)
                .noneMatch(follow -> follow.getArtist().getAccountEmail().equals(user2.getAccountEmail())));

    }
}