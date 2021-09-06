package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByAccountEmailTest() {
        // given
        String accountEmail = "kmk130519@naver.com";
        String userName = "김민권";

        // then
        Optional<User> userByAccountEmail = userRepository.findById(accountEmail);

        // when
        assertThat(userByAccountEmail.get().getAccountEmail()).isEqualTo(accountEmail);
        assertThat(userByAccountEmail.get().getUserName()).isEqualTo(userName);
    }

    @Test
    void findUserByAccountEmailTestIfNull() {
        // given
        String accountEmail = "expectNullId@younsubabo.com";

        // then
        Optional<User> userByAccountEmail = userRepository.findById(accountEmail);

        // when
        assertThat(userByAccountEmail).isEqualTo(Optional.empty());
        assertThat(userByAccountEmail.isPresent()).isFalse();
    }

    @Test
    void findUserByAccessTokenTest() {
        // given
        String accountEmail = "kmk130519@naver.com";
        Optional<User> userByAccountEmail = userRepository.findById(accountEmail);
        String accessToken = userByAccountEmail.get().getAccessToken();

        // when
        Optional<User> userByAccessToken = userRepository.findUserByAccessToken(accessToken);

        // then
        assertThat(userByAccessToken.get().getAccountEmail()).isEqualTo(userByAccountEmail.get().getAccountEmail());
        assertThat(userByAccessToken.get().getAccessToken()).isEqualTo(userByAccountEmail.get().getAccessToken());
    }
}