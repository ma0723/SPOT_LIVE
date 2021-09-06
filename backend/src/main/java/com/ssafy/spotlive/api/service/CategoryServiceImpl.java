package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.category.CategoryGetRes;
import com.ssafy.spotlive.db.entity.Category;
import com.ssafy.spotlive.db.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @FileName : CategoryServiceImpl
 * @작성자 : 강용수
 * @Class 설명 : Category 조회를 위한 Service 정의.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryGetRes> findAllCategory(){
        /**
         * @Method Name : findAllCategory
         * @작성자 : 강용수
         * @Method 설명 : 모든 카테고리를 조회하는 메소드
         */
        return categoryRepository.findAll().stream()
                .map(category -> CategoryGetRes.of(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryGetRes findCategoryByCategoryName(String categoryName){
        /**
         * @Method Name : findCategoryByCategoryName
         * @작성자 : 강용수
         * @Method 설명 : 카테고리를 카테고리 이름으로 조회하는 메소드
         */
        Optional<Category> optionalCategory = categoryRepository.findCategoryByCategoryName(categoryName);

        if (optionalCategory.isPresent())
            return CategoryGetRes.of(optionalCategory.get());
        else
            return null;
    }
}