package com.ssafy.spotlive.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ShowInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long showInfoId = null;
    String showInfoTitle;
    String showInfoDescription;
    String posterUrl;
    int runningTime;
    Long price;

    @OneToMany(mappedBy = "showInfo", cascade = CascadeType.ALL)
    List<Timetable> timetableList = new ArrayList<>();

    @OneToMany(mappedBy = "showInfo", cascade = CascadeType.ALL)
    List<Video> videoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountEmail")
    User user;
}
