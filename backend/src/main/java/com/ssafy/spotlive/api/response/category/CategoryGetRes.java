package com.ssafy.spotlive.api.response.category;

import com.ssafy.spotlive.db.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @FileName : CategoryGetRes
 * @작성자 : 강용수
 * @Class 설명 : 카테고리 조회 API 요청에 대한 CategoryGetResponseDto 정의
 */
@Getter
@Setter
@Builder
@ToString
public class CategoryGetRes {
    Long categoryId;
    String categoryName;

    public static CategoryGetRes of(Category category){
        /**
         * @Method Name : of
         * @작성자 : 강용수
         * @Method 설명 : Category Entity를 CategoryGetResponseDto로 변환하는 메소드
         */
        return CategoryGetRes.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}