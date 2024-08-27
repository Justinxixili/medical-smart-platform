package com.medical.model.user.pojos;

import lombok.Data;

@Data
public class Menu {

    private Integer id;


    private Integer parentId;


    private String title;


    private String icon;

    private String route;

    private Integer order;


    private Boolean isActive;

}
