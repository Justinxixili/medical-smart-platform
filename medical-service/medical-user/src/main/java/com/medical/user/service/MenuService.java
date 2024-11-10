package com.medical.user.service;

import com.medical.model.common.dtos.Result;
import com.medical.model.user.pojos.Menu;

import java.util.List;

public interface MenuService {
    Result<List<Menu>> getAllMenus();


    Result<Menu> saveMenu(Menu menu);

    Result<Void> deleteMenu(Integer id);
}
