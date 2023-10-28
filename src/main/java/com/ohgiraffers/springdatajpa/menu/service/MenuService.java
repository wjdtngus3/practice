package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    private CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;

    }

    /* 1. id로 entity조회 : findById */
    public MenuDTO findMenuByCode(int menuCode) {

        // Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        //IllegalArgumentException이란 디비에 없는 게 있으면 throw를 던져준다는 메소드
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);


        return modelMapper.map(menu, MenuDTO.class);
    }

    /* 2-1. 모든 entity 조회 : findAll(Sort) */
    //List를 위에 menu ->MenuDTO처럼 변환하기 어려움 그래서 스트림화 해야함
    public List<MenuDTO> findMenuList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending()); //정렬시 Sort.by 메소드 추가 단, 속성값에 필드명을 넣어야한다 , desecending은 내림차순

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList()); //menuList를 stream으로 바꿀때 stream()을사용
        //스트림을 사용하는이유 배열이 됐던, 어떤것이됐든 동일한방식으로 사용하기 위함임 , 스트림엔 다양한 메소드가 있는데 배열,가공 등등 메소드들이 있다
        //즉 메뉴라고 하는 것을 스트림화 한다는것은 즉 메뉴가 스트림이 된다는 것임  Stream의 map이란 메소드는 배열이란 메소드에 가공해서반환한다는 목적을 가진다. (menu-> modelMapper.map(menu, MenuDTO.class) 이부분이 메뉴 하나하나를 menuDTO로 변환한다는 뜻이다)
        // 즉 menu라는 엔티티 하나하나를 모델 매퍼를 통해 메뉴DTO로 변환하겠다는 뜻 즉 여기까지는 Stream<Menu> 를 -> Stream<MenuDTO>까지만 반환이고 다음으로
        // Stream<MenuDTO>는 최종으로 쓸 수 업식에 .collect(Collector.toList()로 List<MenuDTO>로 만들어준다
    }


    /* 2-2. 페이징 된 entity 조회 : findAll(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

//    쿼리 메소드 테스트
    public List<MenuDTO> findMenuPrice(Integer menuPrice) {


        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public List<MenuDTO> findorderableStatus(String orderableStatus) {
        List<Menu> menuList = menuRepository.findByorderableStatus(orderableStatus);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public List<MenuDTO> findByCategoryCategoryName(String categoryName) {
        List<Menu> menuList = menuRepository.findByCategoryCategoryName(categoryName);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }


    /*JPQL AND NATIVE QUETY*/
    public List<CategoryDTO> findAllCategory(){

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }


    /* Entity 저장*/
    @Transactional
    public void registNewMenu(MenuDTO menu) {

        menuRepository.save(modelMapper.map(menu, Menu.class)); // menu라는 값은 menuDTO값이어서 menu 엔티티형식으로 전환한 후 저장해야한다.


    }

    /*Entity 수정*/
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode()).orElseThrow(IllegalArgumentException::new);
        foundMenu.setMenuName(menu.getMenuName());

    }

    /* Entity 삭제*/
    @Transactional
    public void deleteMenu(Integer menuCode) {

        menuRepository.deleteById(menuCode);
    }



}
