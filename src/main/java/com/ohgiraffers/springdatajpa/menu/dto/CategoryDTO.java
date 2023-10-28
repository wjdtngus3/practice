package com.ohgiraffers.springdatajpa.menu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//jpa 를 조회하려면 레파지토리 필요함
public class CategoryDTO {
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
}
