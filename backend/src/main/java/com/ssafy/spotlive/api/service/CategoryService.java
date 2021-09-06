package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.category.CategoryGetRes;

import java.util.List;

/**
 * @FileName : CategoryService
 * @작성자 : 강용수
 * @Class 설명 : Category 조회를 위한 Service 정의.
 */
public interface CategoryService {
    List<CategoryGetRes> findAllCategory();
    CategoryGetRes findCategoryByCategoryName(String categoryName);
}