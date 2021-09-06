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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId = null;

    String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Video> videoList = new ArrayList<>();
}
