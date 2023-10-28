package com.ohgiraffers.springdatajpa.menu.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tbl_menu")
@SequenceGenerator(
        name = "seq_menu_code_generator",
        sequenceName = "seq_menu_code",
        allocationSize = 1 //증가값
)
@Getter
@Setter
@ToString
public class Menu {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator"
    )
    private int menuCode;
    private String menuName;
    private int menuPrice;
    @ManyToOne
    @JoinColumn(name = "categoryCode") // "categoryCode"는 외래 키 열의 이름
    private Category category; // Category 엔티티와의 관계

    private String orderableStatus;
 // Category 엔티티와 연결하기 위한 외래 키



    public Menu(){}


}
