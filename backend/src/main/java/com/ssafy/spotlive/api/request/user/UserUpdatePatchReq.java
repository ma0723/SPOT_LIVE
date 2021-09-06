package com.ssafy.spotlive.api.request.user;

import com.ssafy.spotlive.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : UserUpdatePatchReq
 * @작성자 : 김민권
 * @Class 설명 : User 정보 수정 시 사용될 Response Body를 위한 DTO
 */
@Getter
@Setter
@ToString
public class UserUpdatePatchReq {

    String accountEmail;
    String profileNickname;
    String userName;
    String phoneNumber;
    String profileImageUrl;
    String profileDescription;
    String gender;
    String ageRange;

    public User toUser(User updateUser) {
        /**
         * @Method Name : toUser
         * @작성자 : 김민권
         * @Method 설명 :  Update할 User를 인자로 받아 UserUpdatePatchReq DTO를 Save할 수 있도록 변환
         */
        updateUser.setProfileNickname(this.profileNickname);
        updateUser.setUserName(this.userName);
        updateUser.setPhoneNumber(this.phoneNumber);
        // UserUpdatePatchReq의 profileImageUrl이 S3 서버의 URL을 갖고있을 수 있도록 수정이 필요
        // updateUser.setProfileImageUrl(this.profileImageUrl);
        updateUser.setProfileDescription(this.profileDescription);
        updateUser.setGender(this.gender);
        updateUser.setAgeRange(this.ageRange);

        return updateUser;
    }
}
