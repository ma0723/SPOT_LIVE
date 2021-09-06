package com.ssafy.spotlive.api.response.category;

import com.ssafy.spotlive.db.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @FileName : CategoryRes
 * @작성자 : 권영린
 * @Class 설명 : 카테고리 조회 API 요청에 대한 CategoryResponse 정의
 */
@Getter
@Setter
@Builder
public class CategoryRes {
    Long categoryId;
    String categoryName;

    public static CategoryRes of(Category category){
        /**
         * @Method Name : of
         * @작성자 : 권영린
         * @Method 설명 : Category Entity를 CategoryGetResponseDto로 변환하는 메소드
         */
        return CategoryRes.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
