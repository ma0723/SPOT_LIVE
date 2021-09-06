package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(UserVideoId.class)
public class UserVideo {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountEmail")
    User user;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoId")
    Video video;
}
