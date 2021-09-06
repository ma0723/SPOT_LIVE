package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    String accountEmail;

    String profileNickname;
    String userName;
    String phoneNumber;
    String profileImageUrl;
    String profileDescription;
    String gender;
    String ageRange;
    String accessToken;
    String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ShowInfo> showInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "fan", cascade = CascadeType.ALL)
    List<Follow> fanList = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    List<Follow> artistList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<UserVideo> userVideoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Video> videoList = new ArrayList<>();

}
