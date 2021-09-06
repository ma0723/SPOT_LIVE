package com.ssafy.spotlive.db.repository;

import com.ssafy.spotlive.db.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Transactional
    void findCategoryByCategoryName(){
        // given
        String categoryName = "테스트";
        Category insertCategory = new Category();
        insertCategory.setCategoryName(categoryName);

        // when
        categoryRepository.save(insertCategory);
        Category category = categoryRepository.findCategoryByCategoryName(categoryName).orElse(null);

        // then
        assertThat(category).isNotEqualTo(null);
        assertThat(category.getCategoryName()).isEqualTo(categoryName);
        assertThat(category.getCategoryId()).isEqualTo(insertCategory.getCategoryId());
    }
}