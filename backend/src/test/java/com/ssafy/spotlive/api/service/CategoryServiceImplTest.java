package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.category.CategoryGetRes;
import com.ssafy.spotlive.db.entity.Category;
import com.ssafy.spotlive.db.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    void findAllCategory(){
        // given x
        Long categoryId = 2147483648L;
        String categoryName = "kmk 그는 신인가?";
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        categoryList.add(category);

        // when
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryGetRes> categoryGetResList = categoryServiceImpl.findAllCategory();

        // then
        verify(categoryRepository).findAll();
        assertThat(categoryGetResList.get(0).getCategoryId()).isEqualTo(categoryId);
        assertThat(categoryGetResList.get(0).getCategoryName()).isEqualTo(categoryName);
    }

    @Test
    void findCategoryByCategoryName(){
        // given
        Long categoryId = 2147483648L;
        String categoryName = "kmk 그는 신인가?";
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);

        // when
        when(categoryRepository.findCategoryByCategoryName(categoryName)).thenReturn(Optional.ofNullable(category));
        CategoryGetRes categoryGetRes = categoryServiceImpl.findCategoryByCategoryName(categoryName);

        // then
        verify(categoryRepository).findCategoryByCategoryName(categoryName);
        assertThat(categoryGetRes.getCategoryId()).isEqualTo(categoryId);
        assertThat(categoryGetRes.getCategoryName()).isEqualTo(categoryName);
    }
}