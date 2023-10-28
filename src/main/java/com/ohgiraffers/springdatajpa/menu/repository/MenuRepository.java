package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository<Menu, Id>


    public interface MenuRepository extends JpaRepository<Menu, Integer>  {


//        // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록 조회
//        List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);
//
//        // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
//        List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

        // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 내림차순으로 조회
        List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

        List<Menu> findByorderableStatus(String orderableStatus);

        List<Menu> findByCategoryCategoryName(String categoryName);
    }
