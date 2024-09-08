package com.medical.user.service.Impl;

import com.medical.model.common.dtos.Result;
import com.medical.model.user.pojos.Menu;
import com.medical.user.mapper.MenuMapper;
import com.medical.user.mapper.UserMapper;
import com.medical.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Result<List<Menu>> getAllMenus() {
                try {
            List<Menu> menus = menuMapper.getAllMenus();
            return Result.success(menus);
        } catch (Exception e) {
            return Result.error("获取菜单项失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Menu> saveMenu(Menu menu) {
        try {
            int affectedRows = menuMapper.saveMenu(menu);  // 执行插入操作，返回受影响的行数
            if (affectedRows > 0) {
                // 插入成功，menu 对象的 id 已经被自动生成和填充
                return Result.success(menu);
            } else {
                return Result.error("保存菜单项失败：未插入任何行");
            }
        } catch (Exception e) {
            return Result.error("保存菜单项失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Void> deleteMenu(Integer id) {
                try {
                    menuMapper.deleteMenu(id);
            return Result.success(null); // 删除成功，不返回数据
        } catch (Exception e) {
            return Result.error("删除菜单项失败：" + e.getMessage());
        }
    }


}
