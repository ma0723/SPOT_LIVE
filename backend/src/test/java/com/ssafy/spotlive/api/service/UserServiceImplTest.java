package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.request.user.UserUpdatePatchReq;
import com.ssafy.spotlive.db.entity.User;
import com.ssafy.spotlive.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findUserByAccountEmailTest() {
        // given
        String accountEmail = "kmk130519@naver.com";

        // when
        userService.findUserByAccountEmail(accountEmail);

        // then
        verify(userRepository).findById(accountEmail);
    }

    @Test
    void insertUserTest() {
        // given
        User user = new User();

        // when
        userService.insertUser(user);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void findUserByAccessTokenTest() {
        // given
        String accessToken = "accessToken";

        // when
        userService.findUserByAccessToken(accessToken);

        // then
        verify(userRepository).findUserByAccessToken(accessToken);
    }


    @Test
    void userUpdateTest() {
        // given
        String accountEmail = "kmk130519@naver.com";
        User user = new User();
        user.setAccountEmail(accountEmail);

        UserUpdatePatchReq userUpdatePatchReq = new UserUpdatePatchReq();
        userUpdatePatchReq.setAccountEmail(user.getAccountEmail());

        // when
        when(userRepository.findById(accountEmail)).thenReturn(Optional.ofNullable(user));
        userService.updateUser(userUpdatePatchReq);

        // then
        verify(userRepository).save(userUpdatePatchReq.toUser(user));
        verify(userRepository).findById(accountEmail);
    }
}