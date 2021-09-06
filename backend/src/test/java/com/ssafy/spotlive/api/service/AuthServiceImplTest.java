//package com.ssafy.spotlive.api.service;
//
//import com.ssafy.spotlive.api.response.user.KakaoUserRes;
//import com.ssafy.spotlive.api.response.user.UserRes;
//import com.ssafy.spotlive.db.entity.User;
//import com.ssafy.spotlive.db.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//class AuthServiceImplTest { // Test 작업은 kmk130519@naver.com 유저의 AccessToken이 현재 Vaild해야만 한다.
//
//    @Autowired
//    AuthServiceImpl authService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void getKakaoLoginUrlTest() {
//        // given
//        String kakaoLoginUrl = new StringBuilder()
//                .append("https://kauth.kakao.com").append("/oauth/authorize?client_id=").append("68c5cb3f22fe800fc5606851a8bed982")
//                .append("&redirect_uri=").append("http://localhost:8080/api/auth/kakao/login")
//                .append("&response_type=code")
//                .toString();
//
//        // when
//        String result = authService.getKakaoLoginUrl();
//
//        // then
//        assertThat(result).isEqualTo(kakaoLoginUrl);
//    }
//
//    @Test
//    void getKakaoUserInfoTest() {
//        // given
//        String accountEmail = "kmk130519@naver.com";
//        Optional<User> optionalMyUser = userRepository.findById(accountEmail);
//        String accessToken = optionalMyUser.map(User::getAccessToken).orElse(null);
//
//        // when
//        KakaoUserRes kakaoUserInfo = authService.getKakaoUserInfo(accessToken);
//
//        // then
//        assertThat(kakaoUserInfo.getKakao_account().getEmail()).isEqualTo(accountEmail);
//    }
//
//    @Test
//    @Transactional // 자동 Rollback
//    void refreshTokensForExistUserTest() {
//        // given
//        String accountEmail = "kmk130519@naver.com";
//        String newAccessToken = "newAccessToken";
//        String newRefreshToken = "newRefreshToken";
//
//        // when
//        UserRes userRes = authService.refreshTokensForExistUser(accountEmail, newAccessToken, newRefreshToken);
//
//        // then
//        Optional<User> optionalUser = userRepository.findById(accountEmail);
//        assertThat(optionalUser.get().getAccountEmail()).isEqualTo(accountEmail);
//        assertThat(optionalUser.get().getAccessToken()).isEqualTo(newAccessToken);
//        assertThat(optionalUser.get().getRefreshToken()).isEqualTo(newRefreshToken);
//    }
//
//    @Test
//    @Transactional
//    void accessTokenUpdateTest() {
//        // given
//        String accountEmail = "kmk130519@naver.com";
//        Optional<User> optionalUser = userRepository.findById(accountEmail);
//        String beforeAccessToken = optionalUser.get().getAccessToken();
//        String refreshToken = optionalUser.get().getRefreshToken();
//
//        // when
//        String afterAccessToken = authService.accessTokenUpdate(accountEmail);
//
//        // then
//        Optional<User> optionalNewUser = userRepository.findById(accountEmail);
//        assertThat(optionalNewUser.get().getAccountEmail()).isEqualTo(accountEmail);
//        assertThat(optionalNewUser.get().getAccessToken()).isEqualTo(afterAccessToken);
//        assertThat(optionalNewUser.get().getAccessToken()).isNotEqualTo(beforeAccessToken);
//    }
//
//    @Test
//    void isValidTokenTest() {
//        // given
//        String accountEmail = "kmk130519@naver.com";
//        Optional<User> optionalUser = userRepository.findById(accountEmail);
//        String accessToken = optionalUser.get().getAccessToken();
//
//        // when
//        int validTokenCode = authService.isValidToken("bearer " + accessToken);
//
//        // then
//        assertThat(validTokenCode).isIn(200, 401);
//    }
//
//    @Test
//    void logoutTest() {
//        // given
//        String accountEmail = "kmk130519@naver.com";
//        Optional<User> optionalLoginUser = userRepository.findById(accountEmail);
//        String accessToken = optionalLoginUser.get().getAccessToken();
//
//        // then
//        authService.logout(UserRes.of(optionalLoginUser.get()));
//
//        // when 1
//        Optional<User> optionalLogoutUser = userRepository.findById(accountEmail);
//        assertThat(optionalLogoutUser.get().getAccessToken()).isEqualTo("");
//        assertThat(optionalLogoutUser.get().getRefreshToken()).isEqualTo("");
//
//        // when 2
//        int validTokenCode = authService.isValidToken("bearer " + accessToken);
//        assertThat(validTokenCode).isEqualTo(401);
//    }
//
//}