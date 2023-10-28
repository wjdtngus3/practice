package com.ohgiraffers.springdatajpa.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping(value={"/", "/main"})
    public String main() {
        return "main/main";
    }


    //조회 1.JpaRepository 간단한거면 추천 -findById , findAll
    //    2.QueryMethod
    //    3.JPQL
    //    4.Native Query

    //DML 구문은 @Transactional 반드시 기억 
    //삽입 JpaRepository - save
    //수정 엔티티 조회 객체를 수정
    //삭제 JpaRepository - delete


}
