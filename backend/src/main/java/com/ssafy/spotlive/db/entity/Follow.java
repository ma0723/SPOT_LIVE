package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(FollowId.class)
public class Follow{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fan")
    User fan;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist")
    User artist;
}
